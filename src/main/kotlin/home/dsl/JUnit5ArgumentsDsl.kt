package home.dsl

import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

object JUnit5ArgumentsDsl {

    fun list(block: MutableList<Arguments>.() -> Unit): MutableList<Arguments> {
        return mutableListOf<Arguments>().apply { block() }
    }

    fun stream(block: MutableList<Arguments>.() -> Unit): Stream<Arguments> {
        return list(block).stream()
    }

    fun MutableList<Arguments>.args(block: DslContainer<Any?>.() -> Unit) {
        this.add(Arguments.of(*dslElements(block).toTypedArray()))
    }
}
