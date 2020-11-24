package net.exoego.scalajs.munit

import scala.scalajs.js

class Empty1 extends js.Object
class Empty2 extends js.Object
class HasStringFoo(val foo: String) extends js.Object
class HasStringBar(val bar: String) extends js.Object
class HasIntFoo(val foo: Int) extends js.Object

class StructuralEqualsTest extends munit.FunSuite with ScalaJSAssertions {

  test("equal if both are empty") {
    assertStructuralEquals(new js.Object(), new js.Object())
    assertStructuralEquals(new Empty1, new HasStringBar("a"))
  }

  test("equal if exact same key and value") {
    assertStructuralEquals(new HasStringFoo("foo"), new HasStringFoo("foo"))

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
    assertStructuralEquals(a, b)
    assertStructuralEquals(b, a)
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
    assertStructuralEquals(a, b)
    assertStructuralEquals(b, a)
    assertStructuralEquals(new HasStringFoo("foo"), new HasStringFoo("foo"))
  }

  test("not equal if different key") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val b = 2
    }
    assertNotStructuralEquals(a, b)
    assertNotStructuralEquals(b, a)
    assertNotStructuralEquals(new HasStringFoo(foo = "foo"), new HasStringBar(bar = "foo"))
  }

  test("not equal if different value") {
    val a = new js.Object() {
      val a = 1
    }
    val b = new js.Object() {
      val a = 2
    }
    assertNotStructuralEquals(a, b)
    assertNotStructuralEquals(b, a)

    val c = new js.Object() {
      val a = null
    }
    val d = new js.Object() {
      val a = js.undefined
    }
    assertNotStructuralEquals(c, d)
    assertNotStructuralEquals(d, c)

    assertNotStructuralEquals(new HasStringFoo("foo"), new HasStringFoo("bar"))
  }

  test("not equal if having extra key") {
    val a = new js.Object() {
      val a = 1
      val extra = 42
    }
    val b = new js.Object() {
      val a = 1
    }
    assertNotStructuralEquals(a, b)
    assertNotStructuralEquals(b, a)

    val c = new js.Object() {
      val a = 1
      val extra = js.undefined
    }
    val d = new js.Object() {
      val a = 1
    }
    assertNotStructuralEquals(c, d)
    assertNotStructuralEquals(d, c)
  }

  test("equal if both is null") {
    val a: js.Object = null
    val b: js.Object = null
    assertStructuralEquals(a, b)
    assertStructuralEquals(b, a)
  }

  test("not equal if either is null") {
    val o: js.Object = null
    assertNotStructuralEquals(new js.Object(), o)
    assertNotStructuralEquals(o, new js.Object())
  }

  test("not equal with js.Array") {
    assertNotStructuralEquals(
      new js.Object() {
        val length = 0
      },
      js.Array()
    )
  }

  test("equal if both array have deep-equal elements in same order") {
    assertStructuralEquals(js.Array(), js.Array())
    assertStructuralEquals(js.Array(1, "a"), js.Array(1, "a"))
    assertStructuralEquals(js.Array(new js.Object()), js.Array(new js.Object()))
  }

  test("not equal if both array have not-deep-equal elements") {
    assertNotStructuralEquals(js.Array(1), js.Array(2))
    assertNotStructuralEquals(js.Array("a", 1), js.Array(1, "a"))
  }

  test("equal if no properties but JSON string representation equals") {
    assertStructuralEquals(new js.Date(1), new js.Date(1))
    assertNotStructuralEquals(new js.Date(1), new js.Date(2))
  }
}
