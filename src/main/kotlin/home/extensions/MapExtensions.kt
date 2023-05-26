package home.extensions

object MapExtensions {
    @JvmStatic fun <K, V> Map<K, V>.exclude(key: K) = filter { it.key != key }
    @JvmStatic fun <K, V> Map<K, V>.excludeAll(keys: Collection<K>) = filter { it.key !in keys }
    @JvmStatic fun <K, V> Map<K, V>.isEmpty(onTrue: () ->Unit) = isEmpty().apply { if (this) onTrue() }
    @JvmStatic fun <K, V> Map<K, V>.isNotEmpty(onTrue: () ->Unit) = (!isEmpty()).apply { if (this) onTrue() }
}