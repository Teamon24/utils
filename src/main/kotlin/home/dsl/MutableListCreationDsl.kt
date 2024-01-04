package home.dsl

import home.extensions.BooleansExtensions.thus

object MutableListCreationDsl {
    class ListDslInfo<E>(val defaultValue: E?, val mutableList: MutableList<E?>)

    /**
     * ### Пример использования:
     * ```
     *             mutableList {
     *                 at(0) { Random(1).nextInt() }
     *                 at(1) { 2 }
     *                 within(2..6) { index ->
     *                     when (index) {
     *                         4 -> set(index, Int.MAX_VALUE)
     *                         else -> index * 2
     *                     };
     *                 }
     *
     *                 within(7..12) { index -> 2 }
     *                 set(13, 4)
     *                 from(14) { listOf(1,3,4,5,6) }
     *             }
     * ```
     */
    fun <E> mutableList(size: Int, defaultValue: E?, create: ListDslInfo<E?>.() -> Unit): MutableList<E?> {
        return MutableList(size) { defaultValue }.apply { ListDslInfo<E?>(defaultValue, this).create() }
    }

    fun <E> ListDslInfo<E?>.at(index: Int, block: () -> E?) = apply {
        mutableList.initIfOutOfRange<E>(index, defaultValue)
        mutableList[index] = block()
    }

    fun <E> ListDslInfo<E?>.within(indices: IntRange, block: (Int) -> E?) = apply {
        (indices.first > indices.last) thus {
            throw IllegalArgumentException("Indices are reversed in intRange: [${indices.first}, ${indices.last}]")
        }

        indices.forEach { index ->
            mutableList.initIfOutOfRange<E>(index, defaultValue)
            mutableList[index] = block(index)
        }
    }

    fun <E> ListDslInfo<E?>.from(start: Int, block: () -> List<E?>) = apply {
        val elements = block()

        (start..start + (elements.size - 1)).forEach { index ->
            mutableList.initIfOutOfRange<E>(index, defaultValue)
            mutableList[index] = elements[index - start]
        }
    }

    private fun <E> MutableList<E?>.initIfOutOfRange(index: Int, defaultValue: E? = null) {
        if (size <= index) {
            (size..index).forEach { _ ->
                add(defaultValue)
            }
        }
    }
}
