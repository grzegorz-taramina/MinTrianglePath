package io.gt.mintrianglepath

import cats.effect.{ IO, IOApp }
import cats.effect.std.Console
import cats.implicits.toShow
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.service.TriangleService
import io.gt.mintrianglepath.validator.TriangleValidator

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    val triangleService = TriangleService[IO](TriangleReader[IO](), TriangleLineParser(), TriangleValidator())

    (for {
      triangle   <- triangleService.readTriangle()
      pathResult <- triangleService.findMinimalPath(triangle)
    } yield pathResult).value.flatMap {
      case Left(error)   => Console[IO].println(error)
      case Right(result) => Console[IO].println(result.show)
    }
  }
}
