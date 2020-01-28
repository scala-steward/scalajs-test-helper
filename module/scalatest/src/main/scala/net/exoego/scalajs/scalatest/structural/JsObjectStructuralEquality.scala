package net.exoego.scalajs.scalatest.structural

import net.exoego.scalajs.StructuralEqual
import org.scalactic.Equality

import scala.scalajs.js

private object JsObjectStructuralEquality extends Equality[js.Object] {
  override def areEqual(a: js.Object, b: Any): Boolean =
    if (a == b) {
      true
    } else {
      b match {
        case bObj: js.Object => StructuralEqual(a, bObj)
        case _               => false
      }
    }
}
