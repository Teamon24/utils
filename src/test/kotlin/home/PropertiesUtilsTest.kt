package home

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class PropertiesUtilsTest {

    /**
     * Test for [PropertiesUtils.prettyToString].
     */
    @ParameterizedTest
    @MethodSource("prettyToStringTestData")
    fun prettyToStringTest(props: Map<String, *>, expected: String) {
        assertEquals(expected, PropertiesUtils.prettyToString(props))
    }

    companion object {
        @JvmStatic
        fun prettyToStringTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    HashMap<String, Any>()
                        .apply {
                            put("c", "c")
                            put("bb", "b")
                            put("aaaa", "a")
                        }
                    ,
                    """
                        aaaa = a
                        bb   = b
                        c    = c
                    """.trimIndent()
                ),
            )
        }
    }
}