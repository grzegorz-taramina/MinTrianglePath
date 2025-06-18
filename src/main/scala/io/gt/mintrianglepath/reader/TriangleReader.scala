package io.gt.mintrianglepath.reader

import cats.effect.Async
import cats.implicits.catsSyntaxEq
import fs2.{ io, text, Stream }

class TriangleReader[F[_]: Async] {
  def readLinesUntilStopped: Stream[F, Seq[String]] =
    readLines.takeWhile(line => line.trim.toLowerCase =!= "stop").fold(Seq.empty[String])(_ ++ Seq(_))

  private def readLines: Stream[F, String] =
    io.stdin[F](1024).through(text.utf8.decode).through(text.lines).filter(_.nonEmpty)
}

object TriangleReader {
  def apply[F[_]: Async](): TriangleReader[F] = new TriangleReader[F]
}
