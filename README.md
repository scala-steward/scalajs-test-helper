Scala.js Test Helper
----

This project provides custom assertions and other helpers, which are convenient for writing test in Scala.js.


## Usage

### With [ScalaTest](http://www.scalatest.org)

The `scalajs-test-helper-scalatest` library is available for Scala.js 0.6.x and 1.0.0.

```scala
libraryDependencies += "net.exoego" %%% "scalajs-test-helper-scalatest" % "0.2.0" % Test
```

Import `net.exoego.scalajs.scalatest.structural._`.
This imports some [custom `Equality`](http://www.scalactic.org/user_guide/CustomEquality) implementations for JavaScript objects (e.g. `js.Object` and `js.Array`).
Those allows comparing two JS objects based on its contents, as similar as comparing Scala objects.

```scala

import org.scalatest.funsuite.AnyFunSuite
import scala.scalajs.js

class JsObjectStructuralEqualityTest extends AnyFunSuite {
  import net.exoego.scalajs.scalatest.structural._

  test("content equal of js.Array") {
    assert(js.Array() != js.Array())

    assert(js.Array() === js.Array())
    assert(js.Array(1, "a") === js.Array(1, "a"))
    assert(js.Array(new js.Object()) === js.Array(new js.Object()))
  }
}
```


### With [MUnit](https://scalameta.org/munit/)

The `scalajs-test-helper-munit` library is available for Scala.js 0.6.x and 1.0.0.

```scala
libraryDependencies += "net.exoego" %%% "scalajs-test-helper-munit" % "0.2.0" % Test
```

Import `net.exoego.scalajs.munit.ScalaJSAssertions` and extend it by your Test class.
Those allows comparing two JS objects based on its contents, as similar as comparing Scala objects.

```scala

import scala.scalajs.js
import net.exoego.scalajs.munit.ScalaJSAssertions

class JsObjectStructuralEqualityTest extends munit.FunSuite with ScalaJSAssertions {

  test("content equal of js.Array") {
    assertStructuralEquals(js.Array(), js.Array())
    assertStructuralEquals(js.Array(1, "a"), js.Array(1, "a"))
    assertStructuralEquals(js.Array(new js.Object()), js.Array(new js.Object()))

    assertNotStructuralEquals(js.Array(0, "b"), js.Array(1, "a"))
  }
}
```

### Other test library

Not supported but welcome contributions.


