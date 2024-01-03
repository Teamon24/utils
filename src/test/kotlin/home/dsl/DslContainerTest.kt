package home.dsl

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DslContainerTest {

    private var container: DslContainer<Any>? = null;

    private fun dslPrintln(add: DslContainer<Any>.() -> Unit) {
        dslContainer(add) {
            container = this
            println(container!!.elements)
        }
    }

    private val actualElements = { container!!.elements }

    @Test
    fun dslTest() {
        val typedArray = (1..4).toList().toTypedArray()
        val list = listOf("A", "B", "C")

        case {
            dslPrintln { list.forEach { + it } }
            expected = list
        }

        case {
            dslPrintln { val temp = +list }
            expected = list
        }

        case {
            dslPrintln { val temp = + typedArray }
            expected = typedArray.toList()
        }

        case {
            dslPrintln {
                val s1 = + list[0]
                val s2 = + list[1]
                val s3 = + list[2]
            }
            expected = list
        }

        case {
            val intRange = 1..3
            dslPrintln { println(+ intRange) }
            expected = intRange.toList()
        }

        case {
            val forRange = 1..5
            val string = "string"
            dslPrintln { for (i in forRange) +string }
            expected = forRange.map { string }
        }

        case {
            val addable = 1 to 2
            val ints = addable.toList() + 3
            dslPrintln {
                this + ints[0]
                this + ints[1]
                +ints[2]
            }
            expected = addable.toList()
        }
    }

    @Test
    fun nestedDslTest() {
        val first = "1"
        val second = "2"
        val third = 3

        a {
            + A(third)
            + b {
                + B(first)
                + B(second)
            }
        }.apply {
            assertEquals(double(first.toInt()) + double(second.toInt()) + third, this.a)
        }
    }

    private fun case(function: CaseBox.() -> Unit) {
        CaseBox().apply {
            function()
            assertIterableEquals(expected, actualElements())
        }
    }

    class CaseBox { var expected: Iterable<*>? = null }


    data class A(val a: Int)
    data class B(var b: String)

    private fun a(add: DslContainer<A>.() -> Unit): A {
        val reduced = dslElements(add)
                .map { Integer.valueOf(it.a) }
                .reduce { acc: Int, i: Int -> acc + i }

        return A(reduced)
    }

    private fun b(add: DslContainer<B>.() -> Unit): A {
        val reduced = dslElements(add) {
                forEach { it.b = double(it.b.toInt()).toString() }
            }
                .map { it.b.toInt() }
                .reduce { acc: Int, i: Int -> acc + i }

        return A(reduced)
    }

    private fun double(i: Int) = i * 2
}
