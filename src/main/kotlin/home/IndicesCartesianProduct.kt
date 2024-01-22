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

        fun pair(lists: List<List<*>>): List<Pair<Any?, Any?>> {
            val product = tuple(lists, expectedSize = 2)
            return product.map { list -> list[0] to list[1] }
        }

        fun triple(lists: List<List<*>>): List<Triple<Any?, Any?, Any?>> {
            val product = tuple(lists, expectedSize = 3)
            return product.map { list -> Triple(list[0], list[1], list[2])  }
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

        private fun tuple(lists: List<List<*>>, expectedSize: Int): List<List<*>> {
            val product = product(lists)
            product.forEach { list ->
                require(list.size == expectedSize) {
                    "To pair cartesian product each result list in product should has size equals $expectedSize"
                }
            }
            return product
        }
    }
}