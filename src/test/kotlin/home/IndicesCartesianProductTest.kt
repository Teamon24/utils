package home

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 *
 */
internal class IndicesCartesianProductTest {
    /**
     * Test for [IndicesCartesianProduct.product].
     */
    @ParameterizedTest
    @MethodSource("productTestData")
    fun productTest(args: List<List<*>>, expected: List<List<*>>) {
        val actual = IndicesCartesianProduct.product(args)
        assertIterableEquals(expected, actual)
    }

    /**
     * Test for [IndicesCartesianProduct.indicesProduct].
     */
    @ParameterizedTest
    @MethodSource("indicesProductTestData")
    fun indicesProductTest(args: List<List<*>>, expecteds: ArrayList<IntArray>) {
        val actual = IndicesCartesianProduct.indicesProduct(args)
        val iterator = actual.iterator()
        var i = 0
        while (iterator.hasNext()) {
            val actual = iterator.next()
            val expected = expecteds[i]
            assertArrayEquals(expected, actual)
            i++
        }

        assertEquals(expecteds.size, i)

    }

    companion object {
        @JvmStatic
        fun productTestData(): Stream<Arguments> {
            val list1 = arrayListOf("a", "b", "c", "d")
            val list2: List<*> = ArrayList<Any?>().apply {
                add(null)
                add("BIG")
            }
            val list3 = arrayListOf(1, 2, 3)

            return Stream.of(
                Arguments.of(
                    arrayListOf(list1, list2, list3),
                    arrayListOf(
                        arrayListOf("a", null, 1),
                        arrayListOf("a", null, 2),
                        arrayListOf("a", null, 3),
                        arrayListOf("a", "BIG", 1),
                        arrayListOf("a", "BIG", 2),
                        arrayListOf("a", "BIG", 3),
                        arrayListOf("b", null, 1),
                        arrayListOf("b", null, 2),
                        arrayListOf("b", null, 3),
                        arrayListOf("b", "BIG", 1),
                        arrayListOf("b", "BIG", 2),
                        arrayListOf("b", "BIG", 3),
                        arrayListOf("c", null, 1),
                        arrayListOf("c", null, 2),
                        arrayListOf("c", null, 3),
                        arrayListOf("c", "BIG", 1),
                        arrayListOf("c", "BIG", 2),
                        arrayListOf("c", "BIG", 3),
                        arrayListOf("d", null, 1),
                        arrayListOf("d", null, 2),
                        arrayListOf("d", null, 3),
                        arrayListOf("d", "BIG", 1),
                        arrayListOf("d", "BIG", 2),
                        arrayListOf("d", "BIG", 3),
                    )
                )
            )
        }

        @JvmStatic
        fun indicesProductTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    arrayListOf(
                        arrayListOf("a"),
                        arrayListOf("a", "b"),
                        arrayListOf("a", "b", "c"),
                    ),
                    arrayListOf(
                        intArrayOf(0, 0, 0),
                        intArrayOf(0, 0, 1),
                        intArrayOf(0, 0, 2),
                        intArrayOf(0, 1, 0),
                        intArrayOf(0, 1, 1),
                        intArrayOf(0, 1, 2)
                    )
                ),
                Arguments.of(
                    arrayListOf(
                        arrayListOf("a"),
                        arrayListOf("d"),
                        arrayListOf("a", "b"),
                        arrayListOf("a", "b", "c"),
                    ),
                    arrayListOf(
                        intArrayOf(0, 0, 0, 0),
                        intArrayOf(0, 0, 0, 1),
                        intArrayOf(0, 0, 0, 2),
                        intArrayOf(0, 0, 1, 0),
                        intArrayOf(0, 0, 1, 1),
                        intArrayOf(0, 0, 1, 2)
                    )
                )
            )
        }
    }
}
