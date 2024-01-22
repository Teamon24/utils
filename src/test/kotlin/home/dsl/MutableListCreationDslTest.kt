package home.dsl

import home.dsl.JUnit5ArgumentsDsl.args
import home.dsl.JUnit5ArgumentsDsl.stream
import home.dsl.MutableListCreationDsl.flat
import home.dsl.MutableListCreationDsl.list
import home.dsl.MutableListCreationDsl.mutableList
import home.extensions.CollectionsExtensions.and
import home.extensions.IntRangesExtensions.size
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random


class MutableListCreationDslTest {

    /**
     * Test for [MutableListCreationDsl.flat].
     */
    @Test
    fun listFlatAddTest() {
        val list1 = list { repeat(5) { add(1) } }
        val list2 = list { repeat(5) { add(2) } }
        val list3 = list { repeat(5) { add(3) } }
        val list4 = list { repeat(5) { add(4) } }
        val array = list { repeat(5) { add(5) } }.toTypedArray()

        val nothing = null
        val unit = 1
        list {
            //0
            +flat { -list1; -list2; -array; +nothing; }
            //1
            +flat { -list3; -list4; add(unit); +nothing; }
            //2
            +list {
                +flat { -list1; -list2; -array; +nothing }
                +flat { -list3; -list4; add(unit); +nothing }
            }
            //3
            +flat {
                -flat { -list1; -list2; -array; +nothing; }
                -list {
                    +flat { -list1; -list2; -array; +nothing; }
                    +flat {
                        -list3; -list4; add(unit); +nothing
                        -list {
                            +flat { -list1; -list2; -array; +nothing; }
                            +flat {
                                -list3; -list4; add(unit); +nothing
                            }
                        }
                    }
                }
            }
        }.apply {
            assertEquals(4, size)
            val firstFlatSize = list1.size + list2.size + array.size + 1
            val secondFlatSize = list3.size + list4.size + 2
            val firstFlattenList = list1 + list2 + array + nothing
            val secondFlattenList = list3 + list4 + unit + nothing

            assertEquals(firstFlatSize, this[0]!!.size)
            assertEquals(secondFlatSize, this[1]!!.size)
            assertIterableEquals(firstFlattenList, this[0]!!)
            assertIterableEquals(secondFlattenList, this[1]!!)

            val lists = this[2] as List<List<Int?>>
            assertEquals(2, lists.size)
            assertEquals(firstFlatSize, lists[0].size)
            assertEquals(list3.size + list4.size + 2, lists[1].size)
            assertIterableEquals(firstFlattenList, lists[0])
            assertIterableEquals(secondFlattenList, lists[1])

            val thirdList = this[3] as List<Int?>
            assertEquals(firstFlatSize * 3 + secondFlatSize * 2, thirdList.size)

            assertIterableEquals(
                arrayListOf<Int?>().apply {
                    addAll(firstFlattenList)
                    addAll(firstFlattenList)
                    addAll(secondFlattenList)
                    addAll(firstFlattenList)
                    addAll(secondFlattenList)
                }
                , thirdList
            )
        }
    }


    /**
     * Test for [MutableListCreationDsl.ListDslInfo.unaryPlus].
     */
    @Test
    fun unaryPlusTest() {
        val size = 10
        val range = 1..size
        list {
            range.forEach { + "$it" }
        }.apply {
            assertEquals(size, this.size)
            assertIterableEquals(range.toList().map { "$it" }, this)
        }
    }

    /**
     * Test for [MutableListCreationDsl.mutableList] and [MutableListCreationDsl.list].
     */
    @ParameterizedTest
    @MethodSource("listDslFunctionsTestData")
    @DisplayName("Case: when elements setting happens consequently (setting to 0, then 1, 2 and etc.)")
    fun consequentElementSettingTest(
        beingTestedMethod: (size: Int, body: MutableListCreationDsl.ListDslInfo<String?>.() -> Unit) -> List<String?>
    ) {
        val maxInt = Int.MAX_VALUE.toString()
        val double: (Int) -> String = { "${it * 2}" }
        val elements = MutableList(3) { randomString() }
        val lastIndex = 6
        val withinIndices = 3..lastIndex
        val sameElement = { _: Int -> "3" }
        val sameElementIndices = 7..12

        val fromElements = MutableList(5) { randomString() }
        val unaryPlusCheck = "unaryPlusCheck"
        val size = elements.size + withinIndices.size + sameElementIndices.size + fromElements.size
        beingTestedMethod(size) {
            elements.forEachIndexed { index, element ->
                at(index) { element }
            }

            within(withinIndices) { index ->
                when (index) {
                    lastIndex -> maxInt
                    else -> double(index)
                }
            }

            within(sameElementIndices, sameElement)
            from(13) { fromElements }
            add(maxInt)
            + unaryPlusCheck
        }.apply {
            val expected =
                elements +
                        withinIndices.toList().dropLast(1).map(double) +
                        listOf(maxInt) +
                        MutableList(sameElementIndices.size, sameElement) + fromElements + listOf(maxInt) +
                        unaryPlusCheck


            assertIterableEquals(expected, this)
        }
    }

    private fun randomString() = Random(System.currentTimeMillis()).nextInt().toString()

    /**
     * Test for [MutableListCreationDsl.mutableList] and [MutableListCreationDsl.list].
     */
    @ParameterizedTest
    @MethodSource("listDslFunctionsTestData")
    @DisplayName("Case: when elements setting happens to empty list (out of bound situation seems to appear)")
    fun emptyListElementsSettingTest(
        beingTestedMethod: (size: Int, body: MutableListCreationDsl.ListDslInfo<String?>.() -> Unit) -> List<String?>
    ) {
        val start = 1
        val unit = "1"
        val withinIndices = 5..7
        val elementsStart = 9
        val fromElements = elementsStart..elementsStart + 3
        val intMin = Int.MIN_VALUE.toString()
        val unaryPlusCheck = "unaryPlusCheck"

        beingTestedMethod(0) {
            at(start) { unit }
            within(withinIndices) { index -> "$index" }
            from(elementsStart) { fromElements.toList().map { "$it" } }
            set(elementsStart + fromElements.size, unit)
            add(0, intMin)
            +unaryPlusCheck
        }.apply {
            val default1 = defaultValues(0, start)
            val default3 = defaultValues(start + 1, withinIndices.first)
            val default5 = defaultValues(withinIndices.last + 1, fromElements.first)

            val expected =
                    intMin and default1 +
                    listOf(unit) +
                    default3 +
                    withinIndices.toList().map { it.toString() } +
                    default5 +
                    fromElements.toList().map { it.toString() }  +
                    listOf(unit) + unaryPlusCheck

            assertIterableEquals(expected, this)
        }
    }

    private fun defaultValues(first: Int, lastExclusive: Int) = (first until lastExclusive).map { null }.toList()

    companion object {
        @JvmStatic
        fun listDslFunctionsTestData(): Stream<Arguments> {
            return stream {
                args {
                    +{ size: Int,
                       body: MutableListCreationDsl.ListDslInfo<String?>.() -> Unit -> mutableList(size, body)
                    }
                }

                args {
                    +{ size: Int,
                       body: MutableListCreationDsl.ListDslInfo<String?>.() -> Unit -> list(size, body)
                    }
                }
            }
        }
    }
}