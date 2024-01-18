package home.dsl

@JvmInline
value class DslContainer<E>(val elements: MutableList<E> = mutableListOf()) {
    inline operator fun E.unaryPlus(): E { return also { elements.add(it) } }

    /**
     * Method was added as [DslContainer.unaryPlus] can't override methods [Int.unaryPlus], [Double.unaryPlus]
     *
     *       'does not add to dls-container'                'works'
     *                 call [Int.unaryPlus]
     *                           dsl { +1 }       'vs'       dsl { this + 1   }
     *
     *              call [Double.unaryPlus]
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
