package io.gt.mintrianglepath.model

import cats.implicits.toShow
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TriangleSpec extends AnyWordSpec with Matchers {
  "triangle show" should {
    "print small triangle" in {
      val triangle = Triangle(List(List(1), List(2, 3), List(4, 5, 6)))
      triangle.show shouldBe """[    1]	1
                               |[    2]	2 3
                               |[    3]	4 5 6""".stripMargin
    }

    "print big (> 10 rows) triangle" in {

      val triangle = Triangle((0 until 12).map(row => (0 until row + 1).map(item => item)).toList)

      triangle.show shouldBe """[    1]	0
                               |[    2]	0 1
                               |[    3]	0 1 2
                               |[    4]	0 1 2 3
                               |[    5]	0 1 2 3 4
                               |[    6]	0 1 2 3 4 5
                               |[    7]	0 1 2 3 4 5 6
                               |[    8]	0 1 2 3 4 5 6 7
                               |[    9]	0 1 2 3 4 5 6 7 8
                               |[   10]	0 1 2 3 4 5 6 7 8 9
                               | ...
                               |[   12] ...""".stripMargin
    }
  }
}
