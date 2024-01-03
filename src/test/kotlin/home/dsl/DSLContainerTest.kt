package home.dsl

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DSLContainerTest {

    private var container: DSLContainer<String>? = null;

    private fun dslPrintln(add: DSLContainer<String>.() -> Unit) {
        dslContainer(add) {
            container = this
            println(container!!.elements)
        }
    }

    private var strings = listOf("A", "B", "C")

    @Test
    fun dslTest() {
        dslPrintln { strings.forEach { + it } }
        assertEquals(strings, container!!.elements)

        dslPrintln {
            val temp = +strings
        }
        assertEquals(strings, container!!.elements)

        dslPrintln {
            + strings[0]
            + strings[1]
            + strings[2]
        }
        assertEquals(strings, container!!.elements)
    }

    data class A(val a: Int)
    data class B(var b: String)
    private fun a(add: DSLContainer<A>.() -> Unit): A {
        val reduced =
            dslElements(add)
            .map { Integer.valueOf(it.a) }
            .reduce { acc: Int, i: Int -> acc + i }

        return A(reduced)
    }

    private fun b(add: DSLContainer<B>.() -> Unit): A {
        val reduced =
            dslElements(add) {
                forEach {
                    it.b = double(it.b.toInt()).toString()
                }
            }
                .map { it.b.toInt() }
                .reduce { acc: Int, i: Int -> acc + i }

        return A(reduced)
    }

    private fun double(i: Int) = i * 2

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
}