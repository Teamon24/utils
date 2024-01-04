package home.dsl

@JvmInline
value class DslContainer<E>(val elements: MutableList<E> = mutableListOf()) {
    inline operator fun E.unaryPlus(): E { return also { elements.add(it) } }

    /**
     * Переменные range-типа необходимо брать в скобки,
     * иначе первым будет выполнен метод [Int.unaryPlus]:
     *
     *     'не работает'         'работает'
     *      dsl { +(1..10) } 'vs' dsl { +1 .. 10 }
     *
     */
    inline operator fun Iterable<E>  . unaryPlus(): Iterable<E>   { return also { elements.addAll(it) } }
    inline operator fun Collection<E>. unaryPlus(): Collection<E> { return also { elements.addAll(it) } }
    inline operator fun Array<out E> . unaryPlus(): Array<out E>  { return also { elements.addAll(it) } }


    /**
     * Метод был добавлен, т.к. [DslContainer.unaryPlus] не может перекрыть [Int.unaryPlus], [Double.unaryPlus]
     *
     *        'не добавляет в dls-контейнер'                'работает'
     *                вызов [Int.unaryPlus]
     *                           dsl { +1 }       'vs'       dsl { this + 1   }
     *
     *             вызов [Double.unaryPlus]
     *                         dsl { +1.0 }       'vs'       dsl { this + 1.0 }
     *
     */
    inline operator fun plus(e: E): E { return run { elements.add(e); e } }
    inline fun add(e: E): E { return run { elements.add(e); e } }
}

inline fun <E> dslContainer(add: DslContainer<E>.() -> Unit, after: DslContainer<E>.() -> Unit = {}) =
    DslContainer<E>().apply(add).after()

inline fun <E> dslElements(add: DslContainer<E>.() -> Unit, after: MutableList<E>.() -> Unit = {}) =
    DslContainer<E>().run { add(); elements.apply(after) }
