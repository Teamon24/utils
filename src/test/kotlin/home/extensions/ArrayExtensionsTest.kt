package home.extensions

import home.extensions.ArrayExtensions.isNotEmpty
import home.extensions.ArrayExtensions.isEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Tests for [AnysExtensions]
 */
internal class ArrayExtensionsTest {

    /**
     * Test for
     *
     * [ArrayExtensions.isEmpty]
     *
     * [ArrayExtensions.isNotEmpty].
     */
    @Test
    fun isEmptyAndNotTest() {
        val initial = 0
        val expected = 1
        var a = initial;
        arrayOf(1,2,3).isNotEmpty { a = expected }
        assertEquals(expected, a)
        arrayOf<Int>().isEmpty { a = initial }
        assertEquals(initial, a)
    }
}