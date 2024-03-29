package home

import home.ShiftList.Companion.toShiftList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ShiftListTest {

    /**
     * Test for [ShiftList.shiftRight].
     */
    @ParameterizedTest
    @MethodSource("shiftTestData")
    fun shiftTest(expected: List<Long>, shiftList: ShiftList<Long>, shifts: Int) {
        repeat(shifts) { shiftList.shiftRight() }
        Assertions.assertEquals(shifts % shiftList.size, shiftList.shifts)
        Assertions.assertEquals(expected, shiftList)
    }

    /**
     * Test for [ShiftList.toShiftList].
     */
    @ParameterizedTest
    @MethodSource("toShiftTestData")
    fun toShiftTest(expected: List<Long>, list: List<Long>, shifts: Int) {
        val shiftList = list.toShiftList()
        repeat(shifts) { shiftList.shiftRight() }
        Assertions.assertEquals(shifts % shiftList.size, shiftList.shifts)
        Assertions.assertEquals(expected, shiftList)
    }

    companion object {
        @JvmStatic
        fun shiftTestData(): Stream<Arguments> {
            val e1 = 0L
            val e2 = 1L
            val e3 = 2L
            val list = listOf(e1, e2, e3)

            return Stream.of(
                Arguments.of(list,               ShiftList(list), 0),
                Arguments.of(listOf(e3, e1, e2), ShiftList(list), 1),
                Arguments.of(listOf(e3, e1, e2), ShiftList(list), 4),

                Arguments.of(listOf(e2, e3, e1), ShiftList(list), 2),
                Arguments.of(listOf(e2, e3, e1), ShiftList(list), 5),

                Arguments.of(listOf(e1, e2, e3), ShiftList(list), 3),
                Arguments.of(listOf(e1, e2, e3), ShiftList(list), 6),
            )
        }

        @JvmStatic
        fun toShiftTestData(): Stream<Arguments> {
            val e1 = 0L
            val e2 = 1L
            val e3 = 2L
            val list = listOf(e1, e2, e3)

            return Stream.of(
                Arguments.of(list,               list, 0),
                Arguments.of(listOf(e3, e1, e2), list, 1),
                Arguments.of(listOf(e3, e1, e2), list, 4),
                Arguments.of(listOf(e2, e3, e1), list, 2),
                Arguments.of(listOf(e2, e3, e1), list, 5),
                Arguments.of(listOf(e1, e2, e3), list, 3),
                Arguments.of(listOf(e1, e2, e3), list, 6),
            )
        }
    }
}