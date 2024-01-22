package home

/**
 * Can do cartesian product with nullable elements.
 */
class CartesianProduct
private constructor(private val lengths: IntArray) : Iterable<IntArray>, MutableIterator<IntArray> {
    private val indices: IntArray = IntArray(lengths.size)
    private var hasNext = true

    override fun hasNext() = hasNext

    override fun next(): IntArray {
        val result = indices.copyOf(indices.size)
        for (i in indices.indices.reversed()) {
            if (indices[i] == lengths[i] - 1) {
                indices[i] = 0
                if (i == 0) hasNext = false
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
        fun indices(vararg lists: List<Any?>): List<IntArray> {
            return indices(lists.toList())
        }

        /**
         * returns cartesian product of lists.
         */
        fun elements(vararg lists: List<Any?>): List<List<*>> {
            return elements(lists.toList())
        }

        fun iterator(lists: List<List<*>>): CartesianProduct {
            val lengths = IntArray(lists.size)
            for (i in lists.indices) {
                lengths[i] = lists[i].size
            }
            return CartesianProduct(lengths)
        }

        fun indices(lists: List<List<*>>): List<IntArray> {
            val indicesProduct = mutableListOf<IntArray>()
            iterator(lists).apply {
                indicesProduct.apply {
                    while (hasNext) { add(next()) }
                }
            }
            return indicesProduct
        }

        fun elements(lists: List<List<Any?>>): List<List<*>> {
            val cartesianProduct = iterator(lists)

            val result: MutableList<List<*>> = ArrayList()
            for (indices in cartesianProduct) {
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

        fun pair(lists: List<List<Any?>?>): List<Pair<Any?, Any?>> {
            val product = tuple(lists, expectedSize = 2)
            return product.map { list -> list[0] to list[1] }
        }

        fun triple(lists: List<List<Any?>?>): List<Triple<Any?, Any?, Any?>> {
            val product = tuple(lists, expectedSize = 3)
            return product.map { list -> Triple(list[0], list[1], list[2])  }
        }

        private fun tuple(lists: List<List<Any?>?>, expectedSize: Int): List<List<*>> {
            require(lists.size == expectedSize) {
                "To pair cartesian product size of lists should be $expectedSize"
            }

            lists.forEachIndexed { index, list ->
                requireNotNull(list) { "Lists[$index] is null" }
            }

            val product = elements(lists as List<List<Any?>>)
            return product
        }
    }
}