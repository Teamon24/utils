package home.dsl

@JvmInline
value class DslContainer<E>(val elements: MutableList<E> = mutableListOf()) {
    inline operator fun E.unaryPlus(): E { return also { elements.add(it) } }

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
