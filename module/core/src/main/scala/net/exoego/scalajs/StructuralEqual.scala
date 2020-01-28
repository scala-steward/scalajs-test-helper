package net.exoego.scalajs

import scala.scalajs.js

object StructuralEqual extends ((js.Object, js.Object) => Boolean) {
  override def apply(v1: js.Object, v2: js.Object): Boolean =
    if (v1 == v2) {
      true
    } else if (v1 == null || v2 == null) {
      false
    } else {
      deepEqual(v1, v2)
    }

  @inline
  private def deepEqual(v1: js.Object, v2: js.Object): Boolean = {
    val entries1 = js.Object.entries(v1)
    val entries2 = js.Object.entries(v2)
    if (entries1.length != entries2.length) {
      false
    } else if (entries1.length == 0) {
      js.JSON.stringify(v1) == js.JSON.stringify(v2)
    } else {
      val values1 = entries1.sortBy(_._1).map(p => (p._1, p._2.asInstanceOf[js.Any]))
      val values2 = entries2.sortBy(_._1).map(p => (p._1, p._2.asInstanceOf[js.Any]))
      values1.zip(values2).forall {
        case ((k1, _), (k2, _)) if k1 != k2 => false
        case ((_, v1), (_, v2)) =>
          if (js.typeOf(v1) == "object" && js.typeOf(v2) == "object")
            this.apply(v1.asInstanceOf[js.Object], v2.asInstanceOf[js.Object])
          else
            v1 == v2
      }
    }
  }
}
