package home.dsl

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.list
import home.dsl.JUnit5ArgumentsDsl.stream
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments

/**
 * Test for class [JUnit5ArgumentsDsl].
 */
internal class JUnit5ArgumentsDslTest {

    private val first = arrayOf(1, "1", 1.0)
    private val second = arrayOf(10, "10", 10.0)

    @Test
    fun dslTest() {
        val string = "string"
        val range = 1..3
        val third = range.map { string }.toTypedArray()

        list {
            args { + first }
            args { + second }
            args { range.forEach { _ -> +string } }
        }.let { argsList ->
            assert(third, argsList)
        }

        stream {
            args { + first }
            args { + second }
            args { range.forEach { _ -> +string } }
        }.let { stream ->
            val argsList = stream.toList()
            assert(third, argsList)
        }
    }

    private fun assert(
        third: Array<String>,
        argsList: MutableList<Arguments>
    ) {
        Assertions.assertArrayEquals(first, argsList[0].get())
        Assertions.assertArrayEquals(second, argsList[1].get())
        Assertions.assertArrayEquals(third, argsList[2].get())
    }
}