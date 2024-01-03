package home.extensions

object ArrayExtensions {
    @JvmStatic inline fun <E> Array<E>.isEmpty(onTrue: () -> Unit) = isEmpty().apply { if (this) onTrue() }
    @JvmStatic inline fun <E> Array<E>.isNotEmpty(onTrue: () -> Unit) = (!isEmpty()).apply { if (this) onTrue() }
}