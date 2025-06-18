package io.gt.mintrianglepath
package validator

import cats.implicits.catsSyntaxEitherId
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.EitherValues

class TriangleValidatorSpec extends AnyWordSpec with Matchers with EitherValues {
  "triangle validator" should {
    "return an error when the triangle is empty" in {
      TriangleValidator().validateTriangle(Seq.empty) shouldBe "Triangle is empty".asLeft
    }

    "return error when a row has too few elements" in {
      val triangle = Seq(
        Seq(1),
        Seq(2, 3),
        Seq(4, 5),
        Seq(7, 8, 9, 0)
      )
      TriangleValidator().validateTriangle(
        triangle
      ) shouldBe "Triangle is invalid. Found issues: [Row 3 has 2 elements instead of 3]".asLeft
    }

    "return all found errors" in {
      val triangle = Seq(
        Seq(1),
        Seq(2, 3),
        Seq(4, 5),
        Seq(7, 8, 9)
      )
      TriangleValidator().validateTriangle(
        triangle
      ) shouldBe "Triangle is invalid. Found issues: [Row 3 has 2 elements instead of 3, Row 4 has 3 elements instead of 4]".asLeft
    }

    "validate a valid triangle" in {
      val triangle = Seq(
        Seq(1),
        Seq(2, 3),
        Seq(4, 5, 6),
        Seq(7, 8, 9, 10)
      )

      TriangleValidator().validateTriangle(triangle).value shouldBe triangle
    }
  }
}
