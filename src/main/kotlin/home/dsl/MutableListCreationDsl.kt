package home.dsl

import home.dsl.MutableListCreationDsl.mutableList
import home.extensions.BooleansExtensions.thus
import kotlin.random.Random

object MutableListCreationDsl {

    /**
     * ### Пример использования:
     * ```
     *             mutableList {
     *                 at(0) { Random(1).nextInt() }
     *                 at(1) { i -> 2 }
     *                 set(1, Long.MAX_VALUE)
     *                 within(2..6) { i ->
     *                     when (i) {
     *                         4 -> Int.MAX_VALUE
     *                         else -> i * 2
     *                     };
     *                 }
     *                 within(7..12) { i -> 2 }
     *                 at(13) { 4 }
     *                 from(14) { listOf(1, 3, 4, 5, 6) } }
     *                 add(0, Int.MIN_VALUE)
     *             }.apply {
     *                 println(this)
     *             }
     *
     * ```
     * ###Вывод в консоль:
     *       [
     *          //[0]:       add(0, Int.MIN_VALUE)
     *          -2147483648,
     *          //[0]:       at(0) { Random(1).nextInt() }
     *          600123930,
     *          //[1]:       at(1) { i -> 2 }; set(1, Long.MAX_VALUE);
     *          9223372036854775807,
     *          //[2 - 6]:   within(2..6) { i -> when (i) { 4 -> Int.MAX_VALUE else -> i * 2 };}
     *          4, 6, 2147483647, 10, 12,
     *          //[7 - 12]:  within(7..12) { i -> 2 }
     *          2, 2, 2, 2, 2, 2,
     *          //[13]:      at(13) { 4 }
     *          4,
     *          //[14]:      from(14) { listOf(1, 3, 4, 5, 6) } }
     *          1, 3, 4, 5, 6
     *       ]
     */
    fun <E> mutableList(size: Int = 0, defaultValue: E? = null, create: ListDslInfo<E?>.() -> Unit): MutableList<E?> {
        return MutableList(size) { defaultValue }.apply { ListDslInfo<E?>(defaultValue, this).create() }
    }

    class ListDslInfo<E>(private val defaultValue: E?, private val mutableList: MutableList<E?>) {

        fun at(index: Int, block: (index: Int) -> E?) = apply {
            set(index, block(index))
        }

        fun set(index: Int, e: E?) = apply {
            mutableList.initIfOutOfRange(index, defaultValue)
            mutableList[index] = e
        }

        fun add(index: Int, e: E?) = apply {
            mutableList.initIfOutOfRange(index, defaultValue)
            mutableList.add(index, e)
        }

        fun add(e: E?) = apply {
            mutableList.add(e)
        }

        fun within(indices: IntRange, block: (Int) -> E?) = apply {
            (indices.first > indices.last) thus {
                throw IllegalArgumentException("Indices are reversed in intRange: [${indices.first}, ${indices.last}]")
            }

            indices.forEach { index ->
                mutableList.initIfOutOfRange(index, defaultValue)
                mutableList[index] = block(index)
            }
        }

        fun from(start: Int, block: () -> List<E?>) = apply {
            val elements = block()

            (start..start + (elements.size - 1)).forEach { index ->
                mutableList.initIfOutOfRange(index, defaultValue)
                mutableList[index] = elements[index - start]
            }
        }

        private fun MutableList<E?>.initIfOutOfRange(index: Int, defaultValue: E? = null) {
            if (size <= index) {
                (size..index).forEach { _ ->
                    add(defaultValue)
                }
            }
        }
    }
}

private fun main() {
    mutableList {
        at(0) { Random(1).nextInt() }
        at(1) { i -> 2 }
        set(1, Long.MAX_VALUE)
        within(2..6) { i -> when (i) { 4 -> Int.MAX_VALUE; else -> i * 2 }; }
        within(7..12) { i -> 2 }
        at(13) { 4 }
        from(14) { listOf(1, 3, 4, 5, 6) }
        add(0, Int.MIN_VALUE)
    }.apply {
        println(this)
    }
}
