package home.extensions

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.stream
import home.extensions.AnysExtensions.invoke
import home.extensions.AnysExtensions.isNotUnit
import home.extensions.AnysExtensions.plus
import home.extensions.AnysExtensions.and
import home.extensions.AnysExtensions.isNull
import home.extensions.AnysExtensions.notIn
import home.extensions.AnysExtensions.removeFrom
import home.extensions.CollectionsExtensions.and
import home.extensions.CollectionsExtensions.plus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AnysExtensionsTest {

    /**
     * Test for [AnysExtensions.removeFrom] for [MutableMap]
     */
    @ParameterizedTest
    @MethodSource("removeFromTestData")
    internal fun removeFromMutableMapTest(map: MutableMap<Int,String>, removables: Collection<Int>) {
        val initialSize = map.size

        removables.forEach { removable ->
            removable.removeFrom(map)
        }

        Assertions.assertEquals(initialSize - removables.size, map.size)
        removables.forEach { removable ->
            Assertions.assertTrue(removable !in map.keys)
        }
    }

    /**
     * Test for [AnysExtensions.removeFrom] for [MutableCollection]
     */
    @ParameterizedTest
    @MethodSource("removeFromCollectionTestData")
    internal fun removeFromMutableCollectionTest(
        mutableCollection: MutableCollection<Int>,
        removables: Collection<Int>
    ) {
        val initialSize = mutableCollection.size

        removables.forEach { removable ->
            removable.removeFrom(mutableCollection)
        }

        Assertions.assertEquals(initialSize - removables.size, mutableCollection.size)
        removables.forEach { removable ->
            Assertions.assertTrue(removable !in mutableCollection)
        }
    }

    /**
     * Test for [AnysExtensions.notIn]
     */
    @Test
    internal fun notInTest() {
        Assertions.assertTrue (1 notIn listOf(2,3))
        Assertions.assertTrue (1.notIn(2,3))
        Assertions.assertFalse(1.notIn(1,2))
        Assertions.assertFalse(1 notIn listOf(1,2))
    }

    /**
     * Test for
     *
     * [AnysExtensions.plus]
     *
     * [AnysExtensions.and]
     *
     * [CollectionsExtensions.plus]
     *
     * [CollectionsExtensions.and]
     */
    @Test
    internal fun andPlusTest() {
        Assertions.assertEquals(1  +    "F"   +   2 , listOf(1 , "F", 2))
        Assertions.assertEquals(1  +    "F"   and 2 , listOf(1 , "F", 2))
        Assertions.assertEquals(1  and  "F"   and 2 , listOf(1 , "F", 2))
        Assertions.assertEquals((1 and  "F" ) +   2 , listOf(1 , "F", 2))
    }

    /**
     * Test for [AnysExtensions.invoke].
     */
    @ParameterizedTest
    @MethodSource("invokeTestData")
    fun invokeTest(any: Any?, expected: Int) {
        var result = 0
        any.invoke {
            result = 1
        }.also {
            Assertions.assertEquals(expected, result)
            Assertions.assertEquals(Unit, it)
        }
    }

    /**
     * Test for [AnysExtensions.isNotUnit].
     */
    @ParameterizedTest
    @MethodSource("isNotUnitTestData")
    fun isNotUnitTest(any: Any, expected: Int) {
        var result = 0
        any.isNotUnit {
            result = 1
        }.also {
            Assertions.assertEquals(expected, result)
            Assertions.assertEquals(Unit, it)
        }
    }

    companion object {
        const val resultOnTrue = 1
        const val resultOnFalse = 1


        @JvmStatic
        fun invokeTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(Any(), 1),
                Arguments.of(null, 0),
            )
        }

        @JvmStatic
        fun isNotUnitTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(Any(), 1),
                Arguments.of(Unit, 0),
            )
        }

        @JvmStatic
        fun removeFromTestData(): Stream<Arguments> {
            val size = 10
            return stream {
                val removables = arrayListOf<Int>()
                (1..size).forEach { removable ->
                    removables + removable
                    args {
                        + hashMapOf<Int, String>().apply {
                            (1..size).forEach {
                                put(it, "$it")
                            }
                        }
                        + removables.toList()
                    }
                }
            }
        }

        @JvmStatic
        fun removeFromCollectionTestData(): Stream<Arguments> {
            val size = 10
            return stream {
                val removables = arrayListOf<Int>()
                (1..size).forEach { removable ->
                    removables + removable
                    args {
                        + mutableListOf<Int>().apply {
                            (1..size).forEach(this::add)
                        }
                        + removables.toList()
                    }
                }
            }
        }
    }
}