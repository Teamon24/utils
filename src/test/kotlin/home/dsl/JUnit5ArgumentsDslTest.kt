package home.dsl

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.list
import home.dsl.JUnit5ArgumentsDsl.stream
import home.extensions.IterablesExtensions.toRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import kotlin.test.assertEquals

/**
 * Test for class [JUnit5ArgumentsDsl].
 */
internal class JUnit5ArgumentsDslTest {

    private val int = Int.MAX_VALUE
    private val long = Long.MAX_VALUE
    private val string = "4"

    private val repeats = 15
    private val range = 1..repeats
    private val array = arrayOf(1, "1", 1.0)

    private val list = listOf(2, "2", 2.0)
    private val mutableList = mutableListOf(3, "3", 3.0)
    private val stringsList = MutableList(10) { string }

    @Test
    fun dslTest() {
        list() {
            args()
        }.let { argsList ->
            assert(argsList)
        }


        stream {
            args()
        }.let { stream ->
            val argsList = stream.toList()
            assert(argsList)
        }
    }

    private fun MutableList<Arguments>.args() {
        args {
            add(int)
            add(long)
            +string
            +range
            +array
            +list
            +mutableList
            +stringsList
        }
    }

    private fun assert(
        argsList: MutableList<Arguments>
    ) {
        val arguments = argsList[0].get()

        //check that same link was added to dsl-container
        Assertions.assertEquals(int        , arguments[0])
        Assertions.assertEquals(long       , arguments[1])
        Assertions.assertEquals(string     , arguments[2])
        Assertions.assertEquals(range      , arguments[3])
        Assertions.assertEquals(array      , arguments[4])
        Assertions.assertEquals(list       , arguments[5])
        Assertions.assertEquals(mutableList, arguments[6])
        Assertions.assertEquals(stringsList, arguments[7])

        //check that object was not edited during adding to dsl-container
        val actualRange = arguments[3] as IntRange
        val expectedRange = range.toList().toRange()

        assertEquals(expectedRange.start, actualRange.first)
        assertEquals(expectedRange.endInclusive, actualRange.last)

        actualRange.forEach {
            Assertions.assertTrue(it in expectedRange)
        }

        Assertions.assertArrayEquals(array.toList().toTypedArray()        , arguments[4] as Array<out Any?>)
        Assertions.assertIterableEquals(list.toList()                     , arguments[5] as List<Any?>)
        Assertions.assertIterableEquals(mutableList.toList()              , arguments[6] as MutableList<out Any?>)
        Assertions.assertIterableEquals(stringsList.toList()              , arguments[7] as MutableList<String>)
    }
}


