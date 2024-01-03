package home.extensions

import home.extensions.AnysExtensions.lowercaseFirstSimpleName
import home.extensions.StringsExtensions.lowercaseFirst
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Test for [StringsExtensions].
 */
class StringsExtensionsTest {

    /**
     * Test for [StringsExtensions.lowercaseFirst].
     */
    @ParameterizedTest
    @MethodSource("lowercaseFirstTestData")
    fun lowercaseFirstTest(string: String, expected: String, expectedSimpleName: String) {
        val actual = string.lowercaseFirst
        val actualSimpleName = string.lowercaseFirstSimpleName
        Assertions.assertEquals(expected, actual)
        Assertions.assertEquals(expectedSimpleName, actualSimpleName)
    }

    companion object {
        @JvmStatic
        fun lowercaseFirstTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("Args", "args", "string"),
                Arguments.of("ARGS", "aRGS", "string"),
                Arguments.of("ARgs", "aRgs", "string"),
                Arguments.of("ARgS", "aRgS", "string"),
                Arguments.of("ArgS", "argS", "string"),
            )
        }
    }
}