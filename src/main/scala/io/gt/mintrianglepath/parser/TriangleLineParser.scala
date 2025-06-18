package io.gt.mintrianglepath.parser

import scala.util.Try

import cats.data.{ Validated, ValidatedNel }
import cats.data.Validated.Valid
import cats.implicits.catsSyntaxFoldOps

class TriangleLineParser {
  def parseLine(line: String): Either[String, Seq[Int]] = {
    line.trim
      .split("\\s+")
      .map(parseSingleToken)
      .fold(Valid(Seq.empty))(_ combine _)
      .leftMap(errors => errors.mkString_(s"Issues for line ${line} [", ", ", "]"))
      .toEither
  }

  private def parseSingleToken(token: String): ValidatedNel[String, Seq[Int]] =
    Try(token.toInt).toOption match {
      case Some(value) => Validated.valid(Seq(value))
      case None        => Validated.invalidNel(s"Token $token is not a number")
    }
}

object TriangleLineParser {
  def apply(): TriangleLineParser = new TriangleLineParser
}
