package home

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LazyMutable<T>(val initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private object Uninitialized

    private var prop: Any? = Uninitialized

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (prop == Uninitialized) {
            synchronized(this) {
                return if (prop == Uninitialized) initializer().also { prop = it } else prop as T
            }
        } else prop as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        synchronized(this) {
            prop = value
        }
    }
}