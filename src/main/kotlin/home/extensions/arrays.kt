package home.extensions

object ArrayExtensions {
    @JvmStatic inline fun <E> Array<E>.isEmpty(onTrue: () -> Unit) = isEmpty().also { if (it) onTrue() }
    @JvmStatic inline fun <E> Array<E>.isNotEmpty(onTrue: () -> Unit) = (!isEmpty()).also { if (it) onTrue() }
}