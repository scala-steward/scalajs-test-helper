package net.exoego.scalajs.scalatest

import org.scalactic.Equality

import scala.scalajs.js

package object structural {
  implicit def jsObjectStructuralEquality[T <: js.Object]: Equality[T] =
    JsObjectStructuralEquality.asInstanceOf[Equality[T]]
  implicit def jsArrayStructuralEquality[T <: js.Object]: Equality[js.Array[T]] =
    JsObjectStructuralEquality.asInstanceOf[Equality[js.Array[T]]]

}
