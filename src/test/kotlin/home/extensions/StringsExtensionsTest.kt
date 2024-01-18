package home.extensions

import home.extensions.AnysExtensions.decapitalizedSimpleName
import home.extensions.AnysExtensions.lowercaseFirstSimpleName
import home.extensions.StringsExtensions.decapitalized
import home.extensions.StringsExtensions.isNotEmpty
import home.extensions.StringsExtensions.lowercaseFirst
import org.apache.commons.lang3.RandomStringUtils
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
     * Test for [StringsExtensions.isNotEmpty].
     */
    @ParameterizedTest
    @MethodSource("isNotEmptyTestData")
    fun isNotEmptyTest(string: String) {
        val emptiness = 0
        val fullness = 1
        var isNotEmpty = emptiness

        string.isNotEmpty {
            isNotEmpty = fullness
        }

        Assertions.assertTrue(isNotEmpty == if (string.isNotEmpty()) fullness else emptiness)
    }

    /**
     * Test for [StringsExtensions.decapitalized].
     */
    @ParameterizedTest
    @MethodSource("decapitalizedTestData")
    fun decapitalizedTest(string: String, expected: String, expectedSimpleName: String) {
        val actual = string.decapitalized
        val actualSimpleName = string.decapitalizedSimpleName
        Assertions.assertEquals(expected, actual)
        Assertions.assertEquals(expectedSimpleName, actualSimpleName)
    }

    /**
     * Test for [StringsExtensions.lowercaseFirst].
     */
    @ParameterizedTest
    @MethodSource("decapitalizedTestData")
    fun lowercaseFirstTest(string: String, expected: String, expectedSimpleName: String) {
        val actual = string.lowercaseFirst
        val actualSimpleName = string.lowercaseFirstSimpleName
        Assertions.assertEquals(expected, actual)
        Assertions.assertEquals(expectedSimpleName, actualSimpleName)
    }

    companion object {

        @JvmStatic
        fun decapitalizedTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("Args", "args", "string"),
                Arguments.of("ARGS", "aRGS", "string"),
                Arguments.of("ARgs", "aRgs", "string"),
                Arguments.of("ARgS", "aRgS", "string"),
                Arguments.of("ArgS", "argS", "string"),
            )
        }

        @JvmStatic
        fun isNotEmptyTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(" "),
                Arguments.of(""),
                Arguments.of("   "),
                Arguments.of(RandomStringUtils.randomAlphabetic(10)),
            )
        }
    }
}