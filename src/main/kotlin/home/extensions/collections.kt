package home.extensions

object CollectionsExtensions {

    @JvmStatic inline infix fun <T> Collection<T>.and(that: T) = ArrayList<T>(this.size + 1).apply { addAll(this); add(that) }
    @JvmStatic inline infix fun <T> T.and(list: List<T>): List<T> = ArrayList(list).also { it.add(0, this) }

    @JvmStatic fun <K> Collection<K>.exclude(exception: K) = filter { it != exception }
    @JvmStatic fun <K> Collection<K>.exclude(vararg exceptions: K) = filter { it !in exceptions }
    @JvmStatic fun <K> Collection<K>.exclude(predicate: (K) -> Boolean) = filter { !(predicate(it)) }

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
     * Returns "true" if collection has more than one element.
     */
    @JvmStatic inline val <T> Collection<T>.hasElements get() = size > 1

    /**
     * Returns "true" if collection has only one element.
     */
    @JvmStatic inline val <T> Collection<T>.hasElement get() = size == 1

    /**
     * Returns "true" if collection contains only one element that was passed into function.
     */
    @JvmStatic fun <T> Collection<T>.containsOnly(t: T): Boolean = hasElement && contains(t)

    /**
     * Invokes lambda if:
     * * receiving collections are empty;
     * * passed collection is absent or empty;
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

    inline fun <T> Collection<T>.doIf(predicate: (T) -> Boolean, action: (T) -> Unit) =
        forEach { if (predicate(it)) action(it) }

    /**
     * If collection has empty element, lamba is invoked.
     */
    @JvmStatic inline fun <E, C: Collection<E>> Collection<C>.hasEmpty(onTrue: () -> Unit) =
        any { it.isEmpty() }.apply { if(this) onTrue() }

    @JvmStatic inline val <T> Collection<T>.isNotEmpty get() = isNotEmpty()
    @JvmStatic inline val <T> Collection<T>.isEmpty get() = isEmpty()

    @JvmStatic inline fun <T> Collection<T>.isNotEmpty(onTrue: () -> Unit) = isNotEmpty().apply { if(this) onTrue() }
    @JvmStatic inline fun <T> Collection<T>.isEmpty(onTrue: () -> Unit) = isEmpty().apply { if(this) onTrue() }
}
