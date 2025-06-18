package io.gt.mintrianglepath.model

import cats.{ Monoid, Show }

case class PathResult(min: Int, steps: List[Int])

object PathResult {
  implicit val pathResultMonoid: Monoid[PathResult] = new Monoid[PathResult] {
    override def empty: PathResult = PathResult(0, List.empty)

    override def combine(x: PathResult, y: PathResult): PathResult =
      PathResult(x.min + y.min, x.steps ++ y.steps)
  }

  implicit val pathResultShow: Show[PathResult] = Show.show { pathResult =>
    s"Minimal path is: ${pathResult.steps.mkString(" + ")} = ${pathResult.min}"
  }
}
