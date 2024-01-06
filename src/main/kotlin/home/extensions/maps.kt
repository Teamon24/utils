package home.extensions

object MapExtensions {
    @JvmStatic inline fun <K, V> Map<K, V>.exclude(key: K)                 = filter { it.key != key }
    @JvmStatic inline fun <K, V> Map<K, V>.excludeAll(keys: Collection<K>) = filter { it.key !in keys }
    @JvmStatic inline fun <K, V> Map<K, V>.excludeAll(vararg keys: K)      = filter { it.key !in keys }
    @JvmStatic inline fun <K, V> Map<K, V>.isEmpty(onTrue: () ->Unit)      = isEmpty().also { if (it) onTrue() }
    @JvmStatic inline fun <K, V> Map<K, V>.isNotEmpty(onTrue: () ->Unit)   = isNotEmpty().also { if (it) onTrue() }
}