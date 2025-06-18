package io.gt.mintrianglepath

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.IO
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.{ EitherValues, OptionValues }
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class MainSpec
    extends AsyncWordSpec
      with AsyncIOSpec
      with AsyncMockFactory
      with EitherValues
      with OptionValues
      with Matchers {
  "just checking that pipeline" should {
    "run all the tests" in {
      IO.pure(42).asserting(_ shouldBe 13)
    }
  }
}
