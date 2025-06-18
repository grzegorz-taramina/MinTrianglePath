package io.gt.mintrianglepath.model

import cats.Show

case class Triangle(rows: Seq[Seq[Int]])

object Triangle {
  implicit val triangleShow: Show[Triangle] = Show.show { triangle =>
    def asIndex(index: Int) = f"${index}%5d"

    val numberOfRows = triangle.rows.size
    val suffix = if (numberOfRows > 10) {
      s"""\n ...
         |[${asIndex(numberOfRows)}] ...""".stripMargin
    } else ""
    triangle.rows
      .take(10)
      .zipWithIndex
      .map {
        case (row, index) =>
          s"[${asIndex(index + 1)}]\t${row.mkString(" ")}"
      }
      .mkString("\n") + suffix
  }
}
