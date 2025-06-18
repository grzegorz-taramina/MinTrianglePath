package io.gt.mintrianglepath.parser

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxEitherId
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.EitherValues

class TriangleLineParserSpec extends AsyncWordSpec with AsyncIOSpec with EitherValues with Matchers {
  "triangle line parser" must {
    "return an error when one of the tokens is not a number" in {
      TriangleLineParser().parseLine("1 2s 3") shouldBe "Issues for line 1 2s 3 [Token 2s is not a number]".asLeft
    }

    "return comma separated errors" in {
      TriangleLineParser().parseLine(
        "1 2s 3 4e"
      ) shouldBe "Issues for line 1 2s 3 4e [Token 2s is not a number, Token 4e is not a number]".asLeft
    }

    "parse a line" in {
      TriangleLineParser().parseLine("1 2 3").value shouldBe List(1, 2, 3)
    }

    "trim and parse a line" in {
      TriangleLineParser().parseLine(" 1 2 3  ").value shouldBe List(1, 2, 3)
    }
  }
}
