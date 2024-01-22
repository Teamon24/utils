package home

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.stream
import home.dsl.MutableListCreationDsl.list
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class CartesianProductTest {

    /**
     * Test for [CartesianProduct.pair].
     */
    @Test
    fun pairTest() {
        val firstList = listOf("1", "2", null)
        val secondList = listOf("3", "4", null)
        assertEquals(
            list {
                firstList.forEach { i1 ->  secondList.forEach { i2 -> + (i1 to i2) } }
            },
        CartesianProduct.pair(
            list {
                +firstList
                +secondList
            }
        ))
    }

    /**
     * Test for [CartesianProduct.elements].
     */
    @ParameterizedTest
    @MethodSource("pairTestData")
    fun pairTest(args: List<List<*>>, expected: List<Pair<*, *>>) {
        val actual = CartesianProduct.pair(args)
        assertIterableEquals(expected, actual)
    }

    /**
     * Test for [CartesianProduct.elements].
     */
    @ParameterizedTest
    @MethodSource("productTestData")
    fun productTest(args: List<List<*>>, expected: List<List<*>>) {
        val actual = CartesianProduct.elements(args)
        assertIterableEquals(expected, actual)
    }

    /**
     * Test for [CartesianProduct.indices].
     */
    @ParameterizedTest
    @MethodSource("indicesProductTestData")
    fun indicesProductTest(args: List<List<*>>, expected: List<IntArray>) {
        val actual = CartesianProduct.indices(args)
        actual.forEachIndexed { index, indices ->
            assertArrayEquals(expected[index], indices)
        }
    }

    companion object {

        @JvmStatic
        fun productTestData(): Stream<Arguments> {
            val list1 = listOf("a", "b", "c", "d")
            val list2 = listOf(null, "BIG")
            val list3 = listOf(1, 2, 3)

            return stream {
                args {
                    +listOf(list1, list2, list3)
                    +list {
                        list1.forEach { el1 ->
                            list2.forEach { el2 ->
                                list3.forEach { el3 -> +listOf(el1, el2, el3) }
                            }
                        }
                    }
                }
            }
        }

        @JvmStatic
        fun indicesProductTestData(): Stream<Arguments> {
            val maxSize = 3

            val listOfLists = list {
                (1..maxSize).forEach { i1 ->
                    (1..maxSize).forEach { i2 ->
                        (1..maxSize).forEach { i3 ->
                            + list {
                                + listWithSize(i1)
                                + listWithSize(i2)
                                + listWithSize(i3)
                            }
                        }
                    }
                }
            }

            return stream {
                listOfLists.forEach { lists ->
                    lists as List<List<String?>>
                    args {
                        + lists
                        + list {
                            lists[0].indices.forEach { i1 ->
                                lists[1].indices.forEach { i2 ->
                                    lists[2].indices.forEach { i3 -> + intArrayOf(i1, i2, i3) }
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun listWithSize(i1: Int) = list { repeat(i1) { +null } }

        @JvmStatic
        fun pairTestData(): Stream<Arguments> {
            val firstList = listOf("a", "b", "c", "d")
            val secondList = listOf(null, "e", "f")

            return stream {
                args {
                    +listOf(firstList, secondList)
                    +list {
                        firstList.forEach { i1 ->
                            secondList.forEach { i2 -> +(i1 to i2) }
                        }
                    }
                }
            }
        }
    }
}
