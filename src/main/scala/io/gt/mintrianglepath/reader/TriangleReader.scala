package io.gt.mintrianglepath.reader

import cats.effect.IO
import cats.implicits.catsSyntaxEq
import fs2.{ io, text, Stream }

class TriangleReader {
  def readLinesUntilStopped: Stream[IO, Seq[String]] =
    readLines.takeWhile(line => line.trim.toLowerCase =!= "stop").fold(Seq.empty[String])(_ ++ Seq(_))

  private def readLines: Stream[IO, String] =
    io.stdin[IO](1024).through(text.utf8.decode).through(text.lines).filter(_.nonEmpty)
}

object TriangleReader {
  def apply(): TriangleReader = new TriangleReader
}
