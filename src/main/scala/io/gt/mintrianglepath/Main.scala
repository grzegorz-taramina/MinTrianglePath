package io.gt.mintrianglepath

import cats.effect.{ IO, IOApp }
import io.gt.mintrianglepath.parser.TriangleLineParser
import io.gt.mintrianglepath.reader.TriangleReader
import io.gt.mintrianglepath.service.TriangleService
import io.gt.mintrianglepath.validator.TriangleValidator

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    val triangle = TriangleService(TriangleReader(), TriangleLineParser(), TriangleValidator())

    val last: IO[Option[Seq[String]]] = TriangleReader().readLinesUntilStopped.compile.last
    last.flatMap {
      case Some(triangle) => IO(println(s"Triangle: $triangle"))
      case None           => IO(println("No triangle"))
    }
  }
}
