package io.gt.mintrianglepath.service

import cats.data.EitherT
import cats.effect.{ Async, IO }
import cats.implicits.{ catsSyntaxEitherId, catsSyntaxFoldOps, catsSyntaxSemigroup, toTraverseOps }
import io.gt.mintrianglepath.model.{ PathResult, Triangle }
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.validator.TriangleValidator

class TriangleService[F[_]: Async](triangleReader: TriangleReader[F],
                                   triangleLineParser: TriangleLineParser,
                                   triangleValidator: TriangleValidator
) {
  def findMinimalPath(triangle: Triangle): EitherT[F, String, PathResult] = {
    val initial = triangle.rows.last.map(v => PathResult(v, List(v)))

    val result = triangle.rows
      .dropRight(1)
      .reverse
      .foldLeft(initial) { (lowerRow, upperRow) =>
        upperRow.zipWithIndex.map {
          case (value, index) =>
            val left  = lowerRow(index)
            val right = lowerRow(index + 1)
            combineLower(value, left, right)
        }
      }
    EitherT.fromEither[F](result.headOption.map(_.asRight).getOrElse("Triangle is empty".asLeft))
  }

  private def combineLower(currentValue: Int, left: PathResult, right: PathResult): PathResult = {
    val newPathElement = PathResult(currentValue, List(currentValue))
    if (left.min <= right.min) {
      newPathElement.combine(left)
    } else {
      newPathElement.combine(right)
    }
  }

  def readTriangle(): EitherT[F, String, Triangle] =
    for {
      rawInput          <- readTriangleFromStdin
      parsedInput       <- toParsedTriangle(rawInput)
      validatedTriangle <- toValidatedTriangle(parsedInput)
    } yield Triangle(validatedTriangle)

  private def readTriangleFromStdin: EitherT[F, String, Seq[String]] =
    EitherT.fromOptionF[F, String, Seq[String]](
      triangleReader.readLinesUntilStopped.compile.last,
      "No lines read from input"
    )

  private def toParsedTriangle(triangle: Seq[String]): EitherT[F, String, Seq[Seq[Int]]] = {
    val errorOrTriangle = triangle
      .map(triangleLineParser.parseLine)
      .sequence
      .leftMap(errors => errors.mkString_(s"Found issues for triangle items [", ", ", "]"))
      .toEither
    EitherT.fromEither[F](errorOrTriangle)
  }

  private def toValidatedTriangle(triangle: Seq[Seq[Int]]): EitherT[F, String, Seq[Seq[Int]]] =
    EitherT.fromEither[F](triangleValidator.validateTriangle(triangle))
}

object TriangleService {
  def apply[F[_]: Async](triangleReader: TriangleReader[F],
                         triangleLineParser: TriangleLineParser,
                         triangleValidator: TriangleValidator
  ): TriangleService[F] = new TriangleService(triangleReader, triangleLineParser, triangleValidator)
}
