package home.extensions


object IntegersExtensions {
    inline val Int.isEven: Boolean get() = this % 2 == 0
    inline val Int.isOdd: Boolean get() = this % 2 != 0

    @JvmStatic inline fun <T> Int.repeat(block: () -> T) = repeat(this) { block() }
}

