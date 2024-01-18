package home.extensions

import home.extensions.StringsExtensions.decapitalized
import home.extensions.StringsExtensions.lowercaseFirst

object AnysExtensions {

    /**
     * returns *"Horn@12as3"* if receiver is object *"org.horns.hooves.Horn@12as3"*
     *
     * returns *"Horn"* if receiver is class *"org.horns.hooves.Horn"*
     */
    @JvmStatic inline val Any.name: String
        get() {
            var result = this.toString().replace("class", "").trim()
            result = result.substring(result.lastIndexOf('.') + 1)
            result = result.replace('$', '.')

            return result
        }

    @JvmStatic inline val Any.simpleName: String get() = this.javaClass.simpleName
    @JvmStatic inline val Thread.isNotAlive get() = !isAlive
    @JvmStatic inline val Any.refClass get() = name.split("@")[0]
    @JvmStatic inline val Any.refNumber get() = name.split("@")[1]

    @JvmStatic inline fun <T> T.isNotUnit(function: (T) -> Unit) {
        when (this) {
            !is Unit -> function(this)
        }
    }

    /**
     * Analogical method as [also], but [so] returns [Unit].
     *
     * Ignores [body] if receiver [T] is null.
     */
    @JvmStatic inline fun <T> T.so(body: T.() -> Unit) { this?.body() }

    /**
     * Ignores [body] if receiver [T] is null.
     */
    @JvmStatic inline operator fun <T> T.invoke(body: T.() -> Unit) { this?.body() }

    @JvmStatic inline fun <T> T.removeFrom(collection: MutableCollection<T>) = collection.remove(this)
    @JvmStatic inline fun <T> T.removeFrom(map: MutableMap<T, *>) = map.remove(this)

    @JvmStatic inline fun <T> T.addTo(collection: MutableCollection<T>) = collection.add(this)

    @JvmStatic inline infix fun <T> T.notIn(collection: Collection<T>) = this !in collection
    @JvmStatic inline       fun <T> T.notIn(vararg ts: T) = this !in ts

    @JvmStatic inline operator fun <T> T.plus(that: T) = listOf(this, that)
    @JvmStatic inline infix    fun <T> T.and(that: T) = listOf(this, that)

    @JvmStatic inline fun <T> T.anyOf(vararg ts: T) = ts.any { it == this }

    @JvmStatic inline val <T> T?.isNull get() = this == null
    @JvmStatic inline fun <T> T?.isNull(onTrue: (T?) -> Unit) { if (isNull) { onTrue(this) } }

    @Deprecated("Use decapitalizedSimpleName instead.", ReplaceWith("this.decapitalizedSimpleName"))
    @JvmStatic inline val Any.lowercaseFirstSimpleName: String get() = javaClass.simpleName.lowercaseFirst

    @JvmStatic inline val Any.decapitalizedSimpleName: String get() = javaClass.simpleName.decapitalized
}



