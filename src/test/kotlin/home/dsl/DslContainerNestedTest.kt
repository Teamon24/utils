package home.dsl

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test for [DslContainer].
 */
internal class DslContainerNestedTest {

    /**
     * Test for [DslContainer.unaryPlus], but when dsl can be nested in another dsl-block.
     */
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
