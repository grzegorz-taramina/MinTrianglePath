package io.gt.mintrianglepath.parser

import scala.util.Try

import cats.data.{ Validated, ValidatedNel }
import cats.data.Validated.Valid

class TriangleLineParser {
  def parseLine(line: String): ValidatedNel[String, Seq[Int]] =
    line.trim
      .split("\\s+")
      .map(parseSingleToken(_, line))
      .fold(Valid(Seq.empty))(_ combine _)

  private def parseSingleToken(token: String, line: String): ValidatedNel[String, Seq[Int]] =
    Try(token.toInt).toOption match {
      case Some(value) => Validated.valid(Seq(value))
      case None        => Validated.invalidNel(s"Token [$token] in line [$line] is not a number")
    }
}

object TriangleLineParser {
  def apply(): TriangleLineParser = new TriangleLineParser
}
