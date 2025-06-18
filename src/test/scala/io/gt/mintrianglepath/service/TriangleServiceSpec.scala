package io.gt.mintrianglepath.service

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.IO
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.validator.TriangleValidator
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.{ EitherValues, OptionValues }
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec

class TriangleServiceSpec
    extends AsyncWordSpec
      with AsyncIOSpec
      with AsyncMockFactory
      with EitherValues
      with OptionValues
      with Matchers {
  "triangle service" should {
    "read a triangle" in {
      newServiceWithTriangle(Seq(Seq("7", "6 3", "3 8 5", "11 2 10 9"))).readTriangle().value.asserting { result =>
        result.value.rows shouldBe List(
          List(7),
          List(6, 3),
          List(3, 8, 5),
          List(11, 2, 10, 9)
        )
      }
    }
  }

  private def newServiceWithTriangle(triangle: Seq[Seq[String]]): TriangleService = {
    val triangleReader = mock[TriangleReader]
    (triangleReader.readLinesUntilStopped _)
      .expects()
      .returning(fs2.Stream.emits[IO, Seq[String]](triangle))
    TriangleService(triangleReader, TriangleLineParser(), TriangleValidator())
  }
}
