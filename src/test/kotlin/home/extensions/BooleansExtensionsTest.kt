package home.extensions

import home.extensions.BooleansExtensions.invoke
import home.extensions.BooleansExtensions.no
import home.extensions.BooleansExtensions.or
import home.extensions.BooleansExtensions.otherwise
import home.extensions.BooleansExtensions.so
import home.extensions.BooleansExtensions.then
import home.extensions.BooleansExtensions.thus
import home.extensions.BooleansExtensions.yes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Tests for [BooleansExtensions].
 */
class BooleansExtensionsTest {
    var result = initialValue

    @BeforeEach
    fun init() {
        result = initialValue
    }

    @ParameterizedTest
    @MethodSource("thenOrTestData")
    fun thenOrTest(clause: Boolean, thenReturn: Int, orReturn: Int, expectedReturn: Any) {
        clause
            .then { thenReturn }
            .or { orReturn }
            .also {
                Assertions.assertEquals(expectedReturn, it)
            }

        Assertions.assertEquals(expectedReturn, clause then thenReturn or orReturn)
    }


    @ParameterizedTest
    @MethodSource("noReturnTestData")
    fun invokeTest(clause: Boolean, expectedResult: Int?, expectedReturn: Any) {
        clause
            .invoke { result = resultOnTrue }
            .also {
                Assertions.assertEquals(expectedResult, result)
                Assertions.assertEquals(expectedReturn, it)
            }
    }


    @ParameterizedTest
    @MethodSource("withReturnTestData")
    fun yesNoTest(clause: Boolean, expectedResult: Int?, expectedReturn: Any) {
        clause
            .yes { result = resultOnTrue }
            .no { result = resultOnFalse }
            .also {
                Assertions.assertEquals(expectedResult, result)
                Assertions.assertEquals(expectedReturn, it)
            }
    }

    /**
     * Test for [BooleansExtensions.so].
     */
    @ParameterizedTest
    @MethodSource("noReturnTestData")
    fun soTest(clause: Boolean, expectedResult: Int?, expectedReturn: Any) {
        clause
            .so { result = resultOnTrue }
            .also {
                Assertions.assertEquals(expectedResult, result)
                Assertions.assertEquals(expectedReturn, it)
            }
    }


    @ParameterizedTest
    @MethodSource("noReturnTestData")
    fun thusOtherwiseTest(clause: Boolean, expectedResult: Int?, expectedReturn: Any) {
        clause
            .thus { result = resultOnTrue }
            .otherwise { result = resultOnFalse }
            .also {
                Assertions.assertEquals(expectedResult, result)
                Assertions.assertEquals(expectedReturn, it)
            }
    }

    companion object {
        const val initialValue = 0

        const val resultOnFalse = initialValue
        const val resultOnTrue = 1

        @JvmStatic
        fun thenOrTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(true, resultOnTrue, resultOnFalse, resultOnTrue),
                Arguments.of(false, resultOnTrue, resultOnFalse, resultOnFalse)
            )
        }

        @JvmStatic
        fun noReturnTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(true, resultOnTrue, Unit),
                Arguments.of(false, resultOnFalse, Unit)
            )
        }

        @JvmStatic
        fun withReturnTestData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(true, resultOnTrue, true),
                Arguments.of(false, resultOnFalse, false)
            )
        }
    }
}