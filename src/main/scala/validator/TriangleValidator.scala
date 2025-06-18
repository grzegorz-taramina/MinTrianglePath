package io.gt.mintrianglepath.validator

import cats.data.{ NonEmptyList, Validated, ValidatedNel }
import cats.data.Validated.Valid
import cats.implicits.{ catsSyntaxEitherId, catsSyntaxEq, catsSyntaxFoldOps }

class TriangleValidator {
  def validateTriangle(triangle: Seq[Seq[Int]]): Either[String, Seq[Seq[Int]]] =
    if (triangle.isEmpty) {
      "Triangle is empty".asLeft
    } else {
      triangle.zipWithIndex.map {
        case (row, index) => validateRowSize(row, index)
      }.fold(Valid(0))(_ combine _)
        .map(_ => triangle)
        .leftMap(errors => errors.mkString_(s"Triangle is invalid. Found issues: [", ", ", "]"))
        .toEither
    }

  private def validateRowSize(row: Seq[Int], index: Int): Validated[NonEmptyList[String], Int] =
    if (row.size === index + 1) {
      Validated.valid(1)
    } else {
      Validated.invalidNel(s"Row ${index + 1} has ${row.size} elements instead of ${index + 1}")
    }
}

object TriangleValidator {
  def apply(): TriangleValidator = new TriangleValidator
}
