package io.gt.mintrianglepath

import cats.effect.{ IO, IOApp }
import reader.TriangleReader

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    TriangleReader.readLinesUntilStopped.compile.last.flatMap {
      case Some(triangle) => IO(println(s"Triangle: $triangle"))
      case None           => IO(println("No triangle"))
    }
  }
}
