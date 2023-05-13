package home.extensions

object BooleansExtensions {

    inline infix fun <T> Boolean.then(body: () -> T) = if (this) body() else null
    inline infix fun <T> T?.or(body: () -> T)= this ?: body()

    inline infix fun <T> Boolean.then(body: T) = if (this) body else null
    inline infix fun <T> T?.or(body: T) = this ?: body

    inline infix fun Boolean.so(body: () -> Unit) {
        if (this) body()
    }

    inline infix fun Boolean.thus(body: () -> Unit): Boolean = apply { if (this) body() }

    inline infix fun Boolean.otherwise(body: () -> Unit) {
        if (!this) body()
    }

    inline operator fun Boolean.invoke(body: () -> Unit) {
        if (this) body()
    }

    inline infix fun Boolean.yes(body: () -> Unit) = apply { if (this) body() }
    inline infix fun Boolean.no(body: () -> Unit) = apply { if (!this) body() }
}
