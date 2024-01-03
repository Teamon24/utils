package home

/**
 * Can do cartesian product with nullable elements.
 */
class IndicesCartesianProduct
private constructor(private val lengths: IntArray) : Iterable<IntArray>, MutableIterator<IntArray> {
    private val indices: IntArray = IntArray(lengths.size)
    private var hasNext = true

    override fun hasNext(): Boolean {
        return hasNext
    }

    override fun next(): IntArray {
        val result = indices.copyOf(indices.size)
        for (i in indices.indices.reversed()) {
            if (indices[i] == lengths[i] - 1) {
                indices[i] = 0
                if (i == 0) {
                    hasNext = false
                }
            } else {
                indices[i]++
                break
            }
        }
        return result
    }

    override fun iterator(): MutableIterator<IntArray> {
        return this
    }

    override fun remove() {
        throw UnsupportedOperationException()
    }

    companion object {

        /**
         * returns cartesian product of lists elements indices.
         */
        fun indicesProduct(vararg lists: List<*>): IndicesCartesianProduct {
            return indicesProduct(lists.toList())
        }

        /**
         * returns cartesian product of lists.
         */
        fun product(vararg lists: List<*>): List<List<*>> {
            return product(lists.toList())
        }

        fun indicesProduct(lists: List<List<*>>): IndicesCartesianProduct {
            val lengths = IntArray(lists.size)
            for (i in lists.indices) {
                lengths[i] = lists[i].size
            }
            return IndicesCartesianProduct(lengths)
        }

        fun product(lists: List<List<*>>): List<List<*>> {
            val lengths = IntArray(lists.size)
            for (i in lists.indices) {
                lengths[i] = lists[i].size
            }

            val indicesCartesianProduct = IndicesCartesianProduct(lengths)

            val result: MutableList<List<*>> = ArrayList()
            for (indices in indicesCartesianProduct) {
                val objects = ArrayList<Any?>()
                for (i in indices.indices) {
                    val index = indices[i]
                    val o = lists[i][index]
                    objects.add(o)
                }
                result.add(objects)
            }
            return result
        }
    }
}