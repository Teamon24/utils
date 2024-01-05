package home.extensions

object IterablesExtensions {

    fun <T: Comparable<T>> Iterable<T>.toRange(): ClosedRange<T> {
        val sorted = this.sorted()
        val first = sorted.first()
        val last = sorted.last()
        return first..last
    }
}
