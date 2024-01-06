package home.extensions

import home.extensions.MapExtensions.exclude
import home.extensions.MapExtensions.excludeAll
import home.extensions.MapExtensions.isEmpty
import home.extensions.MapExtensions.isNotEmpty
import home.tuple.TuplesExtensions.pair
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

/**
 * Tests for [MapExtensions].
 */
internal class MapExtensionsTest {

    /**
     * Test for
     *
     * [MapExtensions.exclude]
     *
     * [MapExtensions.excludeAll].
     */
    @Test
    fun exclude() {
        val notExcluded = 2 to 2
        assertIterableEquals(
            hashMapOf(1 to 1, notExcluded).exclude(1).entries,
            hashMapOf(notExcluded).entries
        )

        val exclusions = 1..5
        val range = 1..10
        val rest = exclusions.last + 1..range.last

        assertIterableEquals(
            range.associate { it.pair() }.excludeAll(exclusions.toList()).entries,
            rest.associate { it.pair() }.entries
        )

        assertIterableEquals(
            range.associate { it.pair() }.excludeAll(*exclusions.toList().toTypedArray()).entries,
            rest.associate { it.pair() }.entries
        )

    }

    /**
     * Test for
     *
     * [MapExtensions.isEmpty]
     *
     * [MapExtensions.isNotEmpty].
     */
    @Test
    fun isEmptyAndNotTest() {
        val initial = 0
        val expected = 1
        var a = initial;
        hashMapOf(1 to 1, 2 to 2).isNotEmpty { a = expected }
        assertEquals(expected, a)
        hashMapOf<Int, Int>().isEmpty { a = initial }
        assertEquals(initial, a)
    }
}