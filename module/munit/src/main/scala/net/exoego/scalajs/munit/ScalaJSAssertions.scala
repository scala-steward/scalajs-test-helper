package net.exoego.scalajs.munit

import munit.internal.console.{AnsiColors, Lines, Printers, StackTraces}
import munit.internal.difflib.{ComparisonFailExceptionHandler, Diffs}
import munit.{Clues, FailException, Location}
import net.exoego.scalajs.StructuralEqual

import scala.scalajs.js

trait ScalaJSAssertions {
  private def munitAnsiColors: Boolean = true
  private val munitLines = new Lines
  private val comparisonFailHandler: ComparisonFailExceptionHandler =
    (message: String, obtained: String, expected: String, location: Location) => fail(message)

  def assertStructuralEquals[A <: js.Object, B <: js.Object](
      a: A,
      b: B,
      clue: => Any = "values equals structurally"
  )(implicit loc: Location): Unit =
    StackTraces.dropInside {
      if (!StructuralEqual(a, b)) {
        Diffs.assertNoDiff(
          munitPrint(a),
          munitPrint(b),
          comparisonFailHandler,
          munitPrint(clue),
          printObtainedAsStripMargin = false
        )
      }
    }

  def assertNotStructuralEquals[A <: js.Object, B <: js.Object](
      a: A,
      b: B,
      clue: => Any = "values not equals structurally"
  )(implicit loc: Location): Unit =
    StackTraces.dropInside {
      if (StructuralEqual(a, b)) {
        Diffs.assertNoDiff(
          munitPrint(a),
          munitPrint(b),
          comparisonFailHandler,
          munitPrint(clue),
          printObtainedAsStripMargin = false
        )
      }
    }

  private def munitFilterAnsi(message: String): String =
    if (munitAnsiColors) message
    else AnsiColors.filterAnsi(message)

  private def fail(
      message: String,
      clues: Clues = new Clues(Nil)
  )(implicit loc: Location): Nothing =
    throw new FailException(
      munitFilterAnsi(munitLines.formatLine(loc, message, clues)),
      loc
    )

  private def munitPrint(clue: => Any): String =
    clue match {
      case message: String => message
      case value           => Printers.print(value)
    }
}
