package home.extensions

import home.CartesianProduct
import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.stream
import home.extensions.CollectionsExtensions.and
import home.extensions.CollectionsExtensions.containsOnly
import home.extensions.CollectionsExtensions.doIf
import home.extensions.CollectionsExtensions.exclude
import home.extensions.CollectionsExtensions.ifAbsent
import home.extensions.CollectionsExtensions.isEmpty
import home.extensions.CollectionsExtensions.isNotEmpty
import home.extensions.CollectionsExtensions.plus
import home.extensions.IntegersExtensions.isEven
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

/**
 * Tests for [CollectionsExtensions].
 */
class CollectionsExtensionsTest {

    /**
     * Test for [CollectionsExtensions.isEmpty].
     */
    @Test
    fun isEmptyTest() {
        val initial = 0
        var value = initial
        mutableListOf(1, 2, 3, 4).isEmpty {
            value = Int.MAX_VALUE
        }
        Assertions.assertEquals(value, initial)
    }

    /**
     * Test for [CollectionsExtensions.isNotEmpty].
     */
    @Test
    fun isNotEmptyTest() {
        val initial = 0
        var value = initial
        val maxValue = Int.MAX_VALUE
        mutableListOf(1, 2, 3, 4).isNotEmpty {
            value = maxValue
        }
        Assertions.assertEquals(value, maxValue)
    }

    /**
     * Test for [CollectionsExtensions.and].
     */
    @Test
    fun andPlusTest() {

        Assertions.assertEquals(listOf(1, 2), 1 + listOf(2))
        Assertions.assertEquals(listOf(1, 2), listOf(1) + 2)

        Assertions.assertNotEquals(listOf(1, 2), 1 + listOf(3))
        Assertions.assertNotEquals(listOf(1, 2), 1 + listOf(2, 3))

        Assertions.assertNotEquals(listOf(1, 2), listOf(1, 2) + 3)
        Assertions.assertNotEquals(listOf(1, 2), listOf(1) + 3)

        Assertions.assertEquals(listOf(1, 2), 1 and listOf(2))
        Assertions.assertEquals(listOf(1, 2), listOf(1) and 2)

        Assertions.assertNotEquals(listOf(1, 2), 1 and listOf(3))
        Assertions.assertNotEquals(listOf(1, 2), 1 and listOf(2, 3))

        Assertions.assertNotEquals(listOf(1, 2), listOf(1, 2) and 3)
        Assertions.assertNotEquals(listOf(1, 2), listOf(1) and 3)
    }

    /**
     * Test for [CollectionsExtensions.containsOnly].
     */
    @Test
    fun containsOnlyTest() {
        Assertions.assertTrue(arrayListOf(1).containsOnly(1))
        Assertions.assertTrue(arrayListOf("1").containsOnly("1"))

        Assertions.assertFalse(arrayListOf("1").containsOnly(1))
        Assertions.assertFalse(arrayListOf(1).containsOnly("1"))
        Assertions.assertFalse(arrayListOf<String>().containsOnly("1"))
        Assertions.assertFalse(arrayListOf<Int>().containsOnly(1))
        Assertions.assertFalse(arrayListOf(1, "1").containsOnly("1"))
        Assertions.assertFalse(arrayListOf(1, "1").containsOnly(1))
    }

    /**
     * Test for [CollectionsExtensions.ifAbsent].
     */
    @ParameterizedTest
    @MethodSource("ifAbsentTestData")
    fun <E, T : Collection<E>> ifAbsentFalseTest(
        collection: MutableCollection<T>, absent: T, onAbsent: E, expected: E?
    ) {
        var actual: E? = onPresent
        collection.ifAbsent(absent) { actual = onAbsent }
        Assertions.assertEquals(expected, actual)
    }

    /**
     * Test for [CollectionsExtensions.exclude].
     */
    @ParameterizedTest
    @MethodSource("excludeByPredicateTestData")
    fun <E> excludeByPredicateTest(
        collection: MutableCollection<Collection<E>>,
        expected: MutableCollection<Collection<E>>,
        predicate: (Collection<E>) -> Boolean
    ) {
        val size = collection.size
        val exclude = collection.exclude(predicate)
        Assertions.assertEquals(expected, exclude)
        assertEquals(size, collection.size)
    }

    /**
     * Test for [CollectionsExtensions.doIf].
     */
    @ParameterizedTest
    @MethodSource("doIfTestData")
    fun doIfTest(
        collection: Collection<Int>,
        predicate: (Int) -> Boolean,
        expected: Collection<Int>
    ) {
        val actual = mutableListOf<Int>()
        collection.doIf(predicate) { actual.add(it) }
        Assertions.assertIterableEquals(expected, actual)
    }

    companion object {
        private val list1 = listOf(0 to 0, 0 to 1)
        private val list2 = listOf(0 to 1, 1 to 2)
        private const val onAbsent = Int.MAX_VALUE
        private val lists = mutableListOf(list1, list2, listOf(1 to 10), listOf(2 to 4))
        val onPresent = null

        @JvmStatic
        fun ifAbsentTestData(): Stream<Arguments> {
            val absent1 = listOf(2 to 1)
            val absent2 = listOf(0 to 0, 1 to 1)
            val empties = listOf<List<Pair<Int, Int>>>()
            val empty = listOf<Pair<Int, Int>>()

            val withEmpty = empties.toMutableList().apply { add(empty) }

            return flatToStream(
                onAbsentCase(
                    listOf(lists, empties, withEmpty),
                    listOf(absent1, absent2)
                ),
                onAbsentCase(
                    listOf(empties, withEmpty),
                    listOf(list1, list2)
                ),
                onAbsentCase(listOf(lists, empties), listOf(empty)),
                listOf(
                    //cases when present
                    Arguments.of(withEmpty, empty, onAbsent, onPresent),
                    Arguments.of(lists, list1, onAbsent, onPresent),
                    Arguments.of(lists, list2, onAbsent, onPresent)
                )
            )
        }

        private fun <T> flatToStream(vararg lists: List<T>) = lists.flatMap { it }.stream()

        private fun <E, T : List<E>> onAbsentCase(
            lists: List<List<T>>,
            absents: List<T>
        ): List<Arguments> {
            val arguments = mutableListOf<Arguments>()
            lists.forEach { list ->
                absents.forEach { absent ->
                    arguments.add(Arguments.of(list, absent, onAbsent, onAbsent))
                }
            }
            return arguments
        }

        @JvmStatic
        fun excludeByPredicateTestData(): Stream<Arguments> {
            val blank = " "
            val empty = ""
            val string = "string"
            val elements = arrayListOf(null, blank, string, empty)
            val collections = CartesianProduct.elements(elements, elements)

            val expectedCollection = arrayListOf(
                arrayListOf(null, string),
                arrayListOf(blank, string),
                arrayListOf(string, null),
                arrayListOf(string, blank),
                arrayListOf(string, string),
                arrayListOf(string, empty),
                arrayListOf(empty, string),
            )

            val expectedCollection2 = arrayListOf(
                arrayListOf(string, string),
            )

            val expectedCollection3 = arrayListOf(
                arrayListOf(null, null),
                arrayListOf(null, blank),
                arrayListOf(null, string),
                arrayListOf(null, empty),
                arrayListOf(blank, null),
                arrayListOf(blank, blank),
                arrayListOf(blank, string),
                arrayListOf(blank, empty),
                arrayListOf(string, null),
                arrayListOf(string, blank),
                arrayListOf(string, empty),
                arrayListOf(empty, null),
                arrayListOf(empty, blank),
                arrayListOf(empty, string),
                arrayListOf(empty, empty),
            )

            val allAreBlankOrNull = { c: Collection<String?> -> c.all { it == null || it.isBlank() } }
            val oneIsBlankOrNull = { c: Collection<String?> -> c.any { it == null || it.isBlank() } }
            val noneIsBlankOrNull = { c: Collection<String?> -> c.none { it == null || it.isBlank() } }

            return Stream.of(
                Arguments.of(
                    collections,
                    expectedCollection,
                    allAreBlankOrNull
                ),
                Arguments.of(
                    collections,
                    expectedCollection2,
                    oneIsBlankOrNull
                ),
                Arguments.of(
                    collections,
                    expectedCollection3,
                    noneIsBlankOrNull
                ),
            )
        }

        @JvmStatic
        fun doIfTestData(): Stream<Arguments> {

            return stream {
                args {
                    val collection = MutableList(100) { i -> i } as Collection<Int>
                    val predicate: (Int) -> Boolean = { it.isEven }
                    + collection
                    + predicate
                    val expected = + collection.filter(predicate)
                }
            }
        }
    }
}