package home.extensions

object ArrayExtensions {
    @JvmStatic fun <E> Array<E>.isEmpty(onTrue: () ->Unit) = isEmpty().apply { if (this) onTrue() }
    @JvmStatic fun <E> Array<E>.isNotEmpty(onTrue: () ->Unit) = (!isEmpty()).apply { if (this) onTrue() }
}