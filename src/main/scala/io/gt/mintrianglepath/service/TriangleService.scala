package io.gt.mintrianglepath.service

import cats.data.EitherT
import cats.effect.IO
import cats.implicits.toTraverseOps
import io.gt.mintrianglepath.model.Triangle
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.validator.TriangleValidator

class TriangleService(triangleReader: TriangleReader,
                      triangleLineParser: TriangleLineParser,
                      triangleValidator: TriangleValidator
) {
  def readTriangle(): EitherT[IO, String, Triangle] =
    for {
      rawInput          <- readTriangleFromStdin
      parsedInput       <- toParsedTriangle(rawInput)
      validatedTriangle <- toValidatedTriangle(parsedInput)
    } yield Triangle(validatedTriangle)

  private def readTriangleFromStdin: EitherT[IO, String, Seq[String]] =
    EitherT.fromOptionF[IO, String, Seq[String]](
      triangleReader.readLinesUntilStopped.compile.last,
      "No lines read from input"
    )

  private def toParsedTriangle(triangle: Seq[String]): EitherT[IO, String, Seq[Seq[Int]]] =
    EitherT.fromEither[IO](triangle.map(triangleLineParser.parseLine).sequence)

  private def toValidatedTriangle(triangle: Seq[Seq[Int]]): EitherT[IO, String, Seq[Seq[Int]]] =
    EitherT.fromEither[IO](triangleValidator.validateTriangle(triangle))
}

object TriangleService {
  def apply(triangleReader: TriangleReader,
            triangleLineParser: TriangleLineParser,
            triangleValidator: TriangleValidator
  ): TriangleService = new TriangleService(triangleReader, triangleLineParser, triangleValidator)
}
