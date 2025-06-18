package io.gt.mintrianglepath.service

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.IO
import cats.syntax.option._
import io.gt.mintrianglepath.model.{ PathResult, Triangle }
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.validator.TriangleValidator
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.EitherValues

class TriangleServiceSpec extends AsyncWordSpec with AsyncIOSpec with AsyncMockFactory with EitherValues with Matchers {
  "triangle service" should {
    "readTriangle" should {
      "read a triangle" in {
        newServiceWithTriangle(Seq(Seq("7", "6 3", "3 8 5", "11 2 10 9")).some).readTriangle().value.asserting {
          result =>
            result.value.rows shouldBe List(
              List(7),
              List(6, 3),
              List(3, 8, 5),
              List(11, 2, 10, 9)
            )
        }
      }
    }

    "findMinimalPath" should {
      "find the path in a triangle with just one row" in {
        val triangle = Triangle(List(List(7)))
        newServiceWithTriangle(None)
          .findMinimalPath(triangle)
          .value
          .asserting { result =>
            result.value shouldBe PathResult(7, List(7))
          }
      }

      "find the path in a triangle with two rows" in {
        val triangle = Triangle(List(List(7), List(6, 3)))
        newServiceWithTriangle(None)
          .findMinimalPath(triangle)
          .value
          .asserting { result =>
            result.value shouldBe PathResult(10, List(7, 3))
          }
      }

      "find the minimal path" in {
        val triangle = Triangle(List(List(7), List(6, 3), List(3, 8, 5), List(11, 2, 10, 9)))
        newServiceWithTriangle(none[Seq[Seq[String]]])
          .findMinimalPath(triangle)
          .value
          .asserting { result =>
            result.value shouldBe PathResult(18, List(7, 6, 3, 2))
          }
      }

      "handle negative values" in {
        val triangle = Triangle(List(List(1), List(1, 9), List(1, 8, 9), List(1, 8, 8, -20)))
        newServiceWithTriangle(none[Seq[Seq[String]]])
          .findMinimalPath(triangle)
          .value
          .asserting { result =>
            result.value shouldBe PathResult(-1, List(1, 9, 9, -20))
          }
      }
    }
  }

  private def newServiceWithTriangle(triangle: Option[Seq[Seq[String]]]): TriangleService[IO] = {
    class MockTriangleReader extends TriangleReader[IO]
    val triangleReader = mock[MockTriangleReader]
    triangle.foreach { t =>
      (triangleReader.readLinesUntilStopped _)
        .expects()
        .returning(fs2.Stream.emits[IO, Seq[String]](t))
    }

    TriangleService(triangleReader, TriangleLineParser(), TriangleValidator())
  }
}
