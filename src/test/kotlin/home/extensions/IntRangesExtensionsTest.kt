package home.extensions

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.stream
import home.extensions.IntRangesExtensions.range
import home.extensions.IntRangesExtensions.size
import home.extensions.IntRangesExtensions.until
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Test for [IntRangesExtensions].
 */
internal class IntRangesExtensionsTest {

    /**
     * Test for [IntRangesExtensions.size].
     */
    @Test
    fun getSize() {
        val intRange = 0..10
        var sizeCounter = 0
        intRange.forEach { _ -> sizeCounter++ }

        assertEquals(sizeCounter, intRange.size)
    }

    /**
     * Test for [IntRangesExtensions.range] and [IntRangesExtensions.until].
     */
    @ParameterizedTest
    @MethodSource("rangeAndUtilTestData")
    fun until(start: Int,
              end: Int,
              method: (start: Int, end: Int,onEach: (Int) -> Unit) -> Unit,
              expectedCreator: (start: Int, end: Int) -> IntRange
    ) {
        val mutableList = mutableListOf<Int>()
        method(start, end) {
            mutableList.add(it)
        }

        assertIterableEquals(expectedCreator(start, end).toList(), mutableList)
    }

    companion object {
        @JvmStatic
        fun rangeAndUtilTestData(): Stream<Arguments> {
            return stream {
                args {
                    val start = add(1)
                    val end = add(6)
                    val testMethod = + {start: Int, end: Int,onEach: (Int) -> Unit -> start.range(end, onEach) }
                    val expectedCreator = + {start: Int, end: Int -> start..end }
                }

                args {
                    this + 1
                    this + 6
                    + {start: Int, end: Int,onEach: (Int) -> Unit -> start.until(end, onEach) }
                    + {start: Int, end: Int -> start until end }
                }

            }
        }
    }


}