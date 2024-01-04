package home.extensions

import home.extensions.NumbersExtensions.times
import home.tuple.TuplesExtensions.quadruple
import org.jetbrains.annotations.ApiStatus
import kotlin.system.measureNanoTime

object NumbersExtensions {

    /**
     * benchmark:
     *
     *          int    *    int: as Number - 3120200
     *          int    *    int: as is     - 200
     *          float  *  float: as Number - 2500
     *          float  *  float: as is     - 200
     *          double * double: as Number - 4500
     *          double * double: as is     - 200
     *          long   *   long: as Number - 1300
     *          long   *   long: as is     - 200
     */
    @ApiStatus.Experimental
    inline operator fun <reified T : Number, reified A : Number> T.times(n: A): Number {
        var result: Number? = null

        if (this is Int) {
            if (n is Int) result = this * n
            if (n is Long) result = this * n
            if (n is Double) result = this * n
            if (n is Float) result = this * n
            result?.let { return result as Number }
            throw throw unsupportedEx(n)
        }

        if (this is Double) {
            if (n is Int) result = this * n
            if (n is Long) result = this * n
            if (n is Double) result = this * n
            if (n is Float) result = this * n
            result?.let { return result as Number }
            throw throw unsupportedEx(n)
        }

        if (this is Float) {
            if (n is Int) result = this * n
            if (n is Long) result = this * n
            if (n is Double) result = this * n
            if (n is Float) result = this * n
            result?.let { return result as Number }
            throw unsupportedEx(n)
        }

        if (this is Long) {
            if (n is Int) result = this * n
            if (n is Long) result = this * n
            if (n is Double) result = this * n
            if (n is Float) result = this * n
            result?.let { return result }
            throw throw unsupportedEx(n)
        }

        result
            ?.let { return result }
            ?: throw UnsupportedOperationException(
                "There is no cases for 1st number - '${this.javaClass}'"
            )
    }

    inline fun <reified T : Number> unsupportedEx(n: T) =
        UnsupportedOperationException(absentCaseFor2ndNumberMessage(n))

    inline fun <reified T : Number> absentCaseFor2ndNumberMessage(n: T) =
        "There is no case for 2nd number class: '${n.javaClass}'"


}

private fun main() {

    val (in1, in2, i1, i2) = 1.quadruple()
    val (dn1, dn2, d1, d2) = 1.0.quadruple()
    val (fn1, fn2, f1, f2) = 1.08020910f.quadruple()
    val (ln1, ln2, l1, l2) = 1L.quadruple()

    println("int    *    int: as Number - " + measureNanoTime { in1 as Number  * in2 })
    println("int    *    int: as is     - " + measureNanoTime { i1 * i2 })
    println("float  *  float: as Number - " + measureNanoTime { dn1 as Number * dn2 })
    println("float  *  float: as is     - " + measureNanoTime { d1 * d2 })
    println("double * double: as Number - " + measureNanoTime { fn1 as Number * fn2 })
    println("double * double: as is     - " + measureNanoTime { f1 * f2 })
    println("long   *   long: as Number - " + measureNanoTime { ln1 as Number * ln2 })
    println("long   *   long: as is     - " + measureNanoTime { l1 * l2 })

}