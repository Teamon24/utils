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

    private val repeats = 3
    private val string = "string"
    private val range = 1..repeats

    private val firstArgs = arrayOf(1, "1", 1.0)
    private val secondArgs = arrayOf(10, "10", 10.0)
    private inline val DslContainer<Any?>.addThirdArgs: () -> Unit get() = { repeat(repeats) { +string } }

    @Test
    fun dslTest() {
        list {
            args { +firstArgs }
            args { +secondArgs }
            args { addThirdArgs() }
        }.let { argsList ->
            assert(argsList)
        }

        stream {
            args { +firstArgs }
            args { +secondArgs }
            args { addThirdArgs() }
        }.let { stream ->
            val argsList = stream.toList()
            assert(argsList)
        }
    }

    private fun assert(
        argsList: MutableList<Arguments>
    ) {
        Assertions.assertArrayEquals(firstArgs, argsList[0].get())
        Assertions.assertArrayEquals(secondArgs, argsList[1].get())
        Assertions.assertArrayEquals(range.map { string }.toTypedArray(), argsList[2].get())
    }
}