package home.extensions

import home.extensions.IterablesExtensions.toRange
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class IterablesExtensionsTest {

    @Test
    fun toRange() {
        val size = 100
        val intRange = 1..size
        val mutableList = intRange.toMutableList().apply { shuffle() }
        val closedRange = mutableList.toRange()

        mutableList.forEach {
            Assertions.assertTrue(it in closedRange)
        }

        (size + 1.. Int.MAX_VALUE).forEach {
            Assertions.assertFalse(it in closedRange)
        }
    }
}