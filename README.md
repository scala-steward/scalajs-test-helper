Scala.js Test Helper
----

This project provides custom assertions and other helpers, which are convenient for writing test in Scala.js.


## Usage

### With ScalaTest

The `scalajs-test-helper-scalatest` library is available for Scala.js 0.6.x.

```scala
libraryDependencies += "net.exoego" %%% "scalajs-test-helper-scalatest" % "0.1.3" % Test
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

### Other test library

Not supported but welcome contributions.


