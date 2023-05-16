package home.extensions

object CollectionsExtensions {

    @JvmStatic
    inline operator fun <T> Collection<T>.plus(that: T) = ArrayList<T>(this.size + 1).apply { addAll(this); add(that) }

    @JvmStatic
    inline operator fun <T> T.plus(list: List<T>): List<T> = ArrayList(list).also { it.add(0, this) }

    @JvmStatic
    fun <K, V> Map<K, V>.exclude(exception: K) = filter { it.key != exception }

    @JvmStatic
    fun <K, V> Map<K, V>.excludeAll(exceptions: Collection<K>) = filter { it.key !in exceptions }

    @JvmStatic
    fun <K> Collection<K>.exclude(exception: K) = filter { it != exception }

    @JvmStatic
    fun <K> Collection<K>.exclude(vararg exceptions: K) = filter { it !in exceptions }

    fun <E> List<E>.asMutableList(): MutableList<E> {
        if (this is MutableList<E>) {
            return this
        }
        return this.toMutableList()
    }

    @JvmStatic
    inline val <T> Collection<T>.hasElements get() = size > 1

    @JvmStatic
    inline val <T> Collection<T>.hasElement get() = size == 1

    /**
     * returns true if contains only one element that was passed into function.
     */
    @JvmStatic
    fun <T> Collection<T>.containsOnly(t: T): Boolean = hasElement && contains(t)

    @JvmStatic
    inline val <T> Collection<T>.isNotEmpty get() = isNotEmpty()

    @JvmStatic
    inline val <T> Collection<T>.isEmpty get() = isEmpty()
}
