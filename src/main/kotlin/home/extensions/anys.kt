package home.extensions

object AnysExtensions {


    /**
     * org.horns.hooves.Horn@12as3 -> Horn@12as3
     * org.horns.hooves.Horn -> Horn
     */
    @JvmStatic
    inline val Any.name: String
        get() {
            var result = this.toString().replace("class", "").trim()
            result = result.substring(result.lastIndexOf('.') + 1)
            result = result.replace('$', '.')

            return result
        }


    @JvmStatic
    inline val Any.className: String get() = this.javaClass.simpleName

    @JvmStatic
    inline val Thread.isNotAlive get() = !isAlive

    @JvmStatic
    inline val Any.refClass get() = name.split("@")[0]

    @JvmStatic
    inline val Any.refNumber get() = name.split("@")[1]

    @JvmStatic
    inline fun <T> T.isNotUnit(function: (T) -> Unit) {
        when (this) {
            !is Unit -> function(this)
        }
    }

    @JvmStatic
    inline fun <T> Int.repeat(function: () -> T) = repeat(this) { function() }

    @JvmStatic
    inline operator fun <T> T?.invoke(body: T.() -> Unit) {
        this?.body()
    }


    @JvmStatic
    inline fun <T> T.removeFrom(collection: MutableCollection<T>) = collection.remove(this)

    @JvmStatic
    inline fun <T> T.removeFrom(map: MutableMap<T, *>) = map.remove(this)

    @JvmStatic
    inline fun <T> T.notIn(collection: Collection<T>) = this !in collection

    @JvmStatic
    inline operator fun <T> T.plus(that: T) = listOf(this, that)
}



