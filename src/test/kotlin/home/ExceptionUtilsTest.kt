package home

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ExceptionUtilsTest {

    @Test
    fun throwsOn() {
        ExceptionUtils.throwsOn { throw RuntimeException("") }.also { assertTrue(it) }
        ExceptionUtils.throwsOn { 1 + 1 }.also { assertFalse(it) }
    }
}