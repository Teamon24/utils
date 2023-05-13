package home.extensions

import home.extensions.AnysExtensions.invoke
import home.extensions.AnysExtensions.isNotUnit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AnysExtensionsTest {

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
    }
}