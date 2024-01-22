package home.dsl

object MutableListCreationDsl {

    /**
     * ### Example:
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
     * ###Console output:
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
    fun <E> mutableList(size: Int = 0, create: ListDslInfo<E?>.() -> Unit): MutableList<E?> {
        return MutableList(size) { null as E? }.apply { ListDslInfo<E?>(this).create() }
    }

    fun <E> list(size: Int = 0, create: ListDslInfo<E?>.() -> Unit): List<E?> {
        return MutableList(size) { null as E? }.apply { ListDslInfo<E?>(this).create() }
    }

    /**
     * ### Example
     * ```
     *      list {
     *          +flat {
     *               -listOf(1, 2, 3);
     *               -listOf(4, 5)
     *          }
     *          +flat {
     *               -listOf(7, 8);
     *               -listOf(9, 10, 11)
     *               -(12..20).map { it -> it * 2 }
     *          }
     *          +flat {
     *               -flat {
     *                   +"A"; -listOf(21, 22); -listOf(23, 24); +null
     *               }
     *               -list {
     *                   +"B"
     *                   +flat { -listOf(25, 26); -listOf(27, 28); }
     *                   +null
     *               }
     *          }
     *      }.apply {
     *          println(this)
     *      }
     *
     * ```
     * ### Console output
     * ```
     *      //result of list
     *      [
     *          //result of flat
     *          [
     *              1, 2,  //listOf(1 ,2, 3);
     *              3, 4, 5 //listOf(4, 5)
     *          ],
     *          //result of flat
     *          [
     *              7, 8,  //listOf(7, 8);
     *              9, 10, 11,  //listOf(9, 10, 11)
     *              24, 26, 28, 30, 32, 34, 36, 38, 40 //(12..20).map { it -> it * 2 }
     *         ]
     *         //result of flat[ flat, list[flat] ]
     *         ["A", 21, 22, 23, 24, null, "B", 25, 26, 27, 28, null]
     *      ]
     * ```
     */
    fun <E> flat(size: Int = 0, create: FlattenDslInfo<E?>.() -> Unit): List<E?> {
        return MutableList(size) { null as E? }.apply {
            FlattenDslInfo<E?>(this).create()
        }
    }


    @JvmInline
    value class FlattenDslInfo<E>(private val mutableCollection: MutableCollection<E?>) {
        operator fun E.unaryPlus() = add(this)

        operator fun Iterable<E>.unaryMinus() = apply {
            mutableCollection.addAll(traverse(this, arrayListOf()))
        }

        operator fun Array<E>.unaryMinus() = apply {
            mutableCollection.addAll(traverse(this as Array<Any?>, arrayListOf()))
        }

        private fun traverse(collection: Iterable<Any?>, container: MutableCollection<E?>): MutableCollection<E?> {
            collection.forEach { check(it, container) }
            return container
        }

        private fun traverse(array: Array<Any?>, container: MutableCollection<E?>): MutableCollection<E?> {
            array.forEach { check(it, container) }
            return container
        }

        private fun check(it: Any?, container: MutableCollection<E?>) {
            when (it) {
                is Iterable<*> -> traverse(it, container)
                is Array<*> -> traverse(it as Array<Any?>, container)
                else -> container.add(it as E?)
            }
        }

        fun add(e: E?) = apply { mutableCollection.add(e) }
    }

    @JvmInline
    value class ListDslInfo<E>(private val mutableList: MutableList<E?>) {

        operator fun E.unaryPlus() = add(this)
        operator fun E.unaryMinus() = mutableList.remove(this)

        fun at(index: Int, block: (index: Int) -> E?) = apply {
            set(index, block(index))
        }

        fun set(index: Int, e: E?) = apply {
            mutableList.initIfOutOfRange(index, null)
            mutableList[index] = e
        }

        fun add(index: Int, e: E?) = apply {
            mutableList.initIfOutOfRange(index, null)
            mutableList.add(index, e)
        }

        fun add(e: E?) = apply {
            mutableList.add(e)
        }

        fun within(indices: IntRange, block: (Int) -> E?) = apply {
            require(indices.first <= indices.last) {
                "Indices shouldn't be reversed : [${indices.first}, ${indices.last}]"
            }

            indices.forEach { index ->
                mutableList.initIfOutOfRange(index, null)
                mutableList[index] = block(index)
            }
        }

        fun from(start: Int, block: () -> List<E?>) = apply {
            val elements = block()

            (start..start + (elements.size - 1)).forEach { index ->
                mutableList.initIfOutOfRange(index, null)
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
