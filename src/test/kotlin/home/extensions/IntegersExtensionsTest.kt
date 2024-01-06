package home.extensions

import home.extensions.IntegersExtensions.repeat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * Tests for [IntegersExtensions].
 */
internal class IntegersExtensionsTest {

    /**
     * Test for [IntegersExtensions.repeat].
     */
    @Test
    fun repeatTest() {
        var counter = 0;
        10.let { it.repeat { counter++ }; assertEquals(it, counter); counter = 0; }
        20.let { it.repeat { counter++ }; assertEquals(it, counter); counter = 0; }
        30.let { it.repeat { counter++ }; assertEquals(it, counter); counter = 0; }
        40.let { it.repeat { counter++ }; assertEquals(it, counter); counter = 0; }
    }
}