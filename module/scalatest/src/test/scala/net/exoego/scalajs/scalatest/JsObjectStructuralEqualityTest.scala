package net.exoego.scalajs.scalatest

import org.scalatest.funsuite.AnyFunSuite

import scala.scalajs.js

class Empty1 extends js.Object
class Empty2 extends js.Object
class HasStringFoo(val foo: String) extends js.Object
class HasStringBar(val bar: String) extends js.Object
class HasIntFoo(val foo: Int) extends js.Object

class JsObjectStructuralEqualityTest extends AnyFunSuite {
  import structural._

  test("equal if both are empty") {
    assert(new js.Object() != new js.Object())
    assert(new Empty1 != new Empty2)
    assert(new js.Object() === new js.Object())
    assert(new Empty1 === new Empty2)
  }

  test("equal if exact same key and value") {
    assert(new HasStringFoo("foo") != new HasStringFoo("foo"))
    assert(new HasStringFoo("foo") === new HasStringFoo("foo"))

    val a = new js.Object() {
      val a = 1
      val b = true
      val c = ""
      val d = null
      val e = js.undefined
    }
    val b = new js.Object() {
      val a = 1
      val b = true
      val c = ""
      val d = null
      val e = js.undefined
    }
    assert(a === b)
    assert(b === a)
  }

  test("equal if exact same key and value with nested object") {
    val a = new js.Object() {
      val a = 1
      val b = true
      val c = ""
      val d = null
      val e = new HasIntFoo(foo = 1)
      val f = js.Array(1, "a")
    }
    val b = new js.Object() {
      // order different
      val f = js.Array(1, "a")
      val c = ""
      val b = true
      val a = 1
      val e = new js.Object() {
        val foo = 1
      }
      val d = null
    }
    assert(a === b)
    assert(b === a)
    assert(new HasStringFoo("foo") === new HasStringFoo("foo"))
  }

  test("not equal if different key") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val b = 2
    }
    assert(a !== b)
    assert(b !== a)

    assert(new HasStringFoo(foo = "foo") !== new HasStringBar(bar = "foo"))
  }

  test("not equal if different value") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val a = 2
    }
    assert(a !== b)
    assert(b !== a)

    val c = new js.Object() {
      val a = null
    }
    val d = new js.Object() {
      val a = js.undefined
    }
    assert(c !== d)
    assert(d !== c)

    assert(new HasStringFoo("foo") !== new HasStringFoo("bar"))
  }

  test("not equal if having extra key") {
    val a = new js.Object() {
      val a = 1
      val extra = 42
    }
    val b = new js.Object() {
      val a = 1
    }
    assert(a !== b)
    assert(b !== a)

    val c = new js.Object() {
      val a = 1
      val extra = js.undefined
    }
    val d = new js.Object() {
      val a = 1
    }
    assert(c !== d)
    assert(d !== c)
  }

  test("equal if both is null") {
    val a: js.Object = null
    val b: js.Object = null
    assert(a === b)
    assert(b === a)
  }

  test("not equal if either is null") {
    val o: js.Object = null
    assert(new js.Object() !== o)
    assert(o !== new js.Object())
  }

  test("not equal with js.Array") {
    assert(new js.Object() {
      val length = 0
    } !== js.Array())
  }

  test("equal if both array have deep-equal elements in same order") {
    assert(js.Array() === js.Array())
    assert(js.Array(1, "a") === js.Array(1, "a"))
    assert(js.Array(new js.Object()) === js.Array(new js.Object()))
  }

  test("not equal if both array have not-deep-equal elements") {
    assert(js.Array(1) !== js.Array(2))
    assert(js.Array("a", 1) !== js.Array(1, "a"))
  }

  test("equal if no properties but JSON string representation equals") {
    assert(new js.Date(1) === new js.Date(1))
    assert(new js.Date(1) !== new js.Date(2))
  }
}
