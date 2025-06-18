package io.gt.mintrianglepath.parser

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxValidatedId
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class TriangleLineParserSpec extends AsyncWordSpec with AsyncIOSpec with Matchers {
  "triangle line parser" must {
    "return an error when one of the tokens is not a number" in {
      TriangleLineParser().parseLine("1 2s 3") shouldBe "Token [2s] in line [1 2s 3] is not a number".invalidNel
    }

    "return multiple errors for different tokens" in {
      TriangleLineParser().parseLine(
        "1 2s 3 4e"
      ) shouldBe "Token [2s] in line [1 2s 3 4e] is not a number"
        .invalidNel[Seq[Int]]
        .combine(
          "Token [4e] in line [1 2s 3 4e] is not a number".invalidNel
        )
    }

    "parse a line" in {
      TriangleLineParser().parseLine("1 2 3") shouldBe List(1, 2, 3).valid
    }

    "trim and parse a line" in {
      TriangleLineParser().parseLine(" 1 2 3  ") shouldBe List(1, 2, 3).valid
    }
  }
}
