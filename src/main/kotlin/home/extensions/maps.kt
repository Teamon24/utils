package home.extensions

object MapExtensions {
    @JvmStatic inline fun <K, V> Map<K, V>.exclude(key: K) = filter { it.key != key }
    @JvmStatic inline fun <K, V> Map<K, V>.excludeAll(keys: Collection<K>) = filter { it.key !in keys }
    @JvmStatic inline fun <K, V> Map<K, V>.isEmpty(onTrue: () ->Unit) = isEmpty().apply { if (this) onTrue() }
    @JvmStatic inline fun <K, V> Map<K, V>.isNotEmpty(onTrue: () ->Unit) = (!isEmpty()).apply { if (this) onTrue() }
}