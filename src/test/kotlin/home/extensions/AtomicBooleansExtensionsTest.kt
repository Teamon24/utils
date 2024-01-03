package home.extensions

import home.extensions.AtomicBooleansExtensions.atomic
import home.extensions.AtomicBooleansExtensions.toggleAfter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * Test for class [AtomicBooleansExtensions].
 */
internal class AtomicBooleansExtensionsTest {

    @Test
    fun getAtomic() {
        assertEquals(true, true.atomic.get())
        assertEquals(false, false.atomic.get())
    }

    @Test
    fun toggleAfter() {
        val atomic = true.atomic
        atomic.toggleAfter(1000)

    }

    @Test
    fun invoke() {
    }

    @Test
    fun testInvoke() {
    }
}