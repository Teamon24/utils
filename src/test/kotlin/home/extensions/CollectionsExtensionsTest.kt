package home.extensions

import home.extensions.CollectionsExtensions.ifAbsent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Tests for [CollectionsExtensions].
 */
class CollectionsExtensionsTest {

    /**
     * Test for [CollectionsExtensions.ifAbsent].
     */
    @ParameterizedTest
    @MethodSource("ifAbsentTestData")
    fun <E, T: Collection<E>> ifAbsentFalseTest(
        collection: MutableCollection<T>, absent: T, onAbsent: E, expected: E?
    ) {
        var actual: E? = onPresent
        collection.ifAbsent(absent) { actual = onAbsent }
        Assertions.assertEquals(expected, actual)
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

        private fun <E, T: List<E>> onAbsentCase(
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
    }
}