package home.dsl

@JvmInline
value class DslContainer<E>(val elements: MutableList<E> = mutableListOf()) {
    operator fun E.unaryPlus(): E { return also { elements.add(it) } }

    /**
     * Переменные range-типа необходимо брать в скобки,
     * иначе первым будет выполнен метод [Int.unaryPlus]:
     *
     *      dsl { +(1..10) } 'vs' dsl { +1 .. 10 }
     *
     */
    operator fun Iterable<E>  . unaryPlus(): Iterable<E>  { return also { elements.addAll(it) }}
    operator fun Collection<E>. unaryPlus(): Collection<E>{ return also { elements.addAll(it) }}
    operator fun Array<out E> . unaryPlus(): Array<out E> { return also { elements.addAll(it) }}


    /**
     * Метод делает то, что и [unaryPlus] - добавляет объект в dsl-контейнер,
     *
     * но типы, у которых нельзя перегрузить unaryPlus:
     *
     *  [Int.unaryPlus], [Double.unaryPlus]
     *
     *          dsl { +1 }   'vs' dsl { this + 1 }
     *          dsl { +1.0 } 'vs' dsl { this + 1.0 }
     *
     */
    operator fun DslContainer<E>.plus(e: E): E { return run { elements.add(e); e } }
}

inline fun <E> dslContainer(add: DslContainer<E>.() -> Unit, after: DslContainer<E>.() -> Unit = {}) =
    DslContainer<E>().apply(add).after()

inline fun <E> dslElements(add: DslContainer<E>.() -> Unit, after: MutableList<E>.() -> Unit = {}) =
    DslContainer<E>().run { add(); elements.apply(after) }
