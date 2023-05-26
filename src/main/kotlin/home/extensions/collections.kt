package home.extensions

object CollectionsExtensions {

    @JvmStatic inline operator fun <T> Collection<T>.plus(that: T) = ArrayList<T>(this.size + 1).apply { addAll(this); add(that) }
    @JvmStatic inline operator fun <T> T.plus(list: List<T>): List<T> = ArrayList(list).also { it.add(0, this) }

    @JvmStatic fun <K> Collection<K>.exclude(exception: K) = filter { it != exception }
    @JvmStatic fun <K> Collection<K>.exclude(vararg exceptions: K) = filter { it !in exceptions }

    /**
     * return "this" if "this" is MutableList else creates MutableList with elements of "this".
     */
    fun <E> Collection<E>.asMutableList(): MutableList<E> {
        if (this is MutableList<E>) {
            return this
        }
        return this.toMutableList()
    }

    /**
     * returns "true" if size > 1
     */
    @JvmStatic inline val <T> Collection<T>.hasElements get() = size > 1
    @JvmStatic inline val <T> Collection<T>.hasElement get() = size == 1

    /**
     * returns "true" if contains only one element that was passed into function.
     */
    @JvmStatic fun <T> Collection<T>.containsOnly(t: T): Boolean = hasElement && contains(t)

    /**
     * invokes lambda if:
     * - receiving collections are empty;
     * - passed collection: absent or empty;
     */
    @JvmStatic
    inline fun <E, C: Collection<E>> MutableCollection<C>.ifAbsent(
        absent: C,
        onAbsence: MutableCollection<C>.() -> Unit
    ) {
        isEmpty { onAbsence(); return }

        absent.isEmpty {
            this.hasEmpty { return }
        }

        find { collection -> collection.size == absent.size && collection.containsAll(absent) } ?: onAbsence()
    }

    @JvmStatic inline fun <E, C: Collection<E>> Collection<C>.hasEmpty(onTrue: () -> Unit) =
        any { it.isEmpty() }.apply { if(this) onTrue() }

    @JvmStatic inline val <T> Collection<T>.isNotEmpty get() = isNotEmpty()
    @JvmStatic inline val <T> Collection<T>.isEmpty get() = isEmpty()

    @JvmStatic inline fun <T> Collection<T>.isNotEmpty(onTrue: () -> Unit) = isNotEmpty().apply { if(this) onTrue() }
    @JvmStatic inline fun <T> Collection<T>.isEmpty(onTrue: () -> Unit) = isEmpty().apply { if(this) onTrue() }

}
