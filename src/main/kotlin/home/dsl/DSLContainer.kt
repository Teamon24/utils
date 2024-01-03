package home.dsl

@JvmInline
value class DSLContainer<E>(val elements: MutableList<E> = mutableListOf()) {
    operator fun E.unaryPlus(): E { return apply { elements.add(this) } }
    operator fun Collection<E>.unaryPlus(): Collection<E> { return apply { elements.addAll(this) }}
}

inline fun <E> dslContainer(add: DSLContainer<E>.() -> Unit, after: DSLContainer<E>.() -> Unit = {}) =
    DSLContainer<E>().apply(add).after()

inline fun <E> dslElements(add: DSLContainer<E>.() -> Unit, after: MutableList<E>.() -> Unit = {}) =
    DSLContainer<E>().run { add(); elements.apply(after) }
