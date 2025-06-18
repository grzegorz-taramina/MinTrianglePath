package io.gt.mintrianglepath
package reader

import cats.effect.IO
import cats.implicits.catsSyntaxEq
import fs2.{ io, text, Stream }

object TriangleReader {
  def readLinesUntilStopped: Stream[IO, Seq[String]] =
    readLines.takeWhile(line => line.trim.toLowerCase =!= "stop").fold(Seq.empty[String])(_ ++ Seq(_))

  private def readLines: Stream[IO, String] =
    io.stdin[IO](1024).through(text.utf8.decode).through(text.lines).filter(_.nonEmpty)
}
