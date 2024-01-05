package home.dsl

import home.dsl.MutableListCreationDsl.mutableList
import home.extensions.CollectionsExtensions.and
import home.extensions.IntRangesExtensions.size
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.random.Random


class MutableListCreationDslTest {

    private val defaultValue = 0

    /**
     * Тест для [MutableListCreationDsl.mutableList]
     */
    @Test
    @DisplayName("Case: when elements setting happens consequently (setting to 0, then 1, 2 and etc.)")
    fun consequentElementSettingTest() {
        val maxInt = Int.MAX_VALUE

        val double = { it: Int -> it * 2 }
        val elements = MutableList(3) { randomInt() }
        val lastIndex = 6
        val withinIndices = 3..lastIndex
        val sameElement = { _: Int -> 3 }
        val sameElementIndices = 7..12

        val fromElements = MutableList(5) { randomInt() }

        val size = elements.size + withinIndices.size + sameElementIndices.size + fromElements.size

        mutableList(size, defaultValue) {
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
        }.apply {
            val expected =
                     elements +
                     withinIndices.toList().dropLast(1).map(double) +
                     listOf(maxInt) +
                     MutableList(sameElementIndices.size, sameElement) + fromElements + listOf(maxInt)

            assertIterableEquals(expected, this)
        }
    }

    private fun randomInt() = Random(System.currentTimeMillis()).nextInt()

    /**
     * Тест для [MutableListCreationDsl.mutableList].
     */
    @Test
    @DisplayName("Case: when elements setting happens to empty list (out of bound situation appears)")
    fun emptyListElementsSettingTest() {
        val unit = 1
        val withinIndices = 5..7
        val start = 9
        val fromElements = start..start + 3
        val intMin = Int.MIN_VALUE

        mutableList(size = 0, defaultValue) {
            at(unit) { unit }
            within(withinIndices) { index -> index }
            from(start) { fromElements.toList() }
            set(start + fromElements.size, unit)
            add(0, intMin)
        }.apply {
            val default1 = defaultValues(0, unit)
            val default3 = defaultValues(unit + 1, withinIndices.first)
            val default5 = defaultValues(withinIndices.last + 1, fromElements.first)

            val expected =
                    intMin and default1 +
                    listOf(unit) +
                    default3 +
                    withinIndices.toList() +
                    default5 +
                    fromElements.toList() +
                    listOf(unit)
            assertIterableEquals(expected, this)
        }
    }

    private fun defaultValues(first: Int, lastExclusive: Int) = (first until lastExclusive).map { 0 }.toList()
}