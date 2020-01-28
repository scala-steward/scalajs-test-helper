package net.exoego.scalajs

import org.scalatest.funsuite.AnyFunSuite

import scala.scalajs.js

class Empty1 extends js.Object
class Empty2 extends js.Object
class HasStringFoo(val foo: String) extends js.Object
class HasStringBar(val bar: String) extends js.Object
class HasIntFoo(val foo: Int) extends js.Object

class DeepEqualTest extends AnyFunSuite {
  test("equal if both are empty") {
    assert(StructuralEqual(new js.Object(), new js.Object()))
    assert(StructuralEqual(new Empty1, new Empty2))
  }

  test("equal if exact same key and value") {
    assert(StructuralEqual(new HasStringFoo("foo"), new HasStringFoo("foo")))
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
    assert(StructuralEqual(a, b))
    assert(StructuralEqual(b, a))
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
    assert(StructuralEqual(a, b))
    assert(StructuralEqual(b, a))
    assert(StructuralEqual(new HasStringFoo("foo"), new HasStringFoo("foo")))
  }

  test("not equal if different key") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val b = 2
    }
    assert(!StructuralEqual(a, b))
    assert(!StructuralEqual(b, a))

    assert(!StructuralEqual(new HasStringFoo(foo = "foo"), new HasStringBar(bar = "foo")))
  }

  test("not equal if different value") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val a = 2
    }
    assert(!StructuralEqual(a, b))
    assert(!StructuralEqual(b, a))

    val c = new js.Object() {
      val a = null
    }
    val d = new js.Object() {
      val a = js.undefined
    }
    assert(!StructuralEqual(c, d))
    assert(!StructuralEqual(d, c))

    assert(!StructuralEqual(new HasStringFoo("foo"), new HasStringFoo("bar")))
  }

  test("not equal if having extra key") {
    val a = new js.Object() {
      val a = 1
      val extra = 42
    }
    val b = new js.Object() {
      val a = 1
    }
    assert(!StructuralEqual(a, b))
    assert(!StructuralEqual(b, a))

    val c = new js.Object() {
      val a = 1
      val extra = js.undefined
    }
    val d = new js.Object() {
      val a = 1
    }
    assert(!StructuralEqual(c, d))
    assert(!StructuralEqual(d, c))
  }

  test("equal if both is null") {
    assert(StructuralEqual(null, null))
  }

  test("not equal if either is null") {
    assert(!StructuralEqual(new js.Object(), null))
    assert(!StructuralEqual(null, new js.Object()))
  }

  test("not equal with js.Array") {
    assert(!StructuralEqual(new js.Object() {
      val length = 0
    }, js.Array()))
  }

  test("equal if both array have deep-equal elements in same order") {
    assert(StructuralEqual(js.Array(), js.Array()))
    assert(StructuralEqual(js.Array(1, "a"), js.Array(1, "a")))
    assert(StructuralEqual(js.Array(new js.Object()), js.Array(new js.Object())))
  }

  test("not equal if both array have not-deep-equal elements") {
    assert(!StructuralEqual(js.Array(1), js.Array(2)))
    assert(!StructuralEqual(js.Array("a", 1), js.Array(1, "a")))
  }

  test("equal if no properties but JSON string representation equals") {
    assert(StructuralEqual(new js.Date(1), new js.Date(1)))
    assert(!StructuralEqual(new js.Date(1), new js.Date(2)))
  }
}
