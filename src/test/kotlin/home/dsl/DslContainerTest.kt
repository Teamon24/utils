package home.dsl

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

/**
 * Test for [DslContainer].
 */
internal class DslContainerTest {

    private var container: DslContainer<Any>? = null;

    private fun dslPrintln(add: DslContainer<Any>.() -> Unit) {
        dslContainer(add) {
            container = this
            println(container!!.elements)
        }
    }

    private val actualElements = { container!!.elements }

    /**
     * Test for [DslContainer.unaryPlus].
     */
    @Test
    fun dslTest() {
        val typedArray = (1..4).toList().toTypedArray()
        val list = listOf("A", "B", "C")

        case {
            dslPrintln { list.forEach { + it } }
            expected = list
        }

        iterableCase {
            dslPrintln { val temp = +list }
            expected = list
        }

        arrayCase {
            dslPrintln { val temp = + typedArray }
            expected = typedArray.toList()
        }

        case {
            dslPrintln {
                val s1 = + list[0]
                val s2 = + list[1]
                val s3 = + list[2]
            }
            expected = list
        }

        iterableCase {
            val intRange = 1..3
            dslPrintln { println(+ intRange) }
            expected = intRange.toList()
        }

        case {
            val forRange = 1..5
            val string = "string"
            dslPrintln { for (i in forRange) +string }
            expected = forRange.map { string }
        }

        case {
            val addable = 1 to 2
            val ints = addable.toList() + 3
            dslPrintln {
                this + ints[0]
                this + ints[1]
                +ints[2]
            }
            expected = addable.toList()
        }
    }

    private fun innerCaseLogic(function: CaseBox.() -> Unit, assertion: CaseBox.() -> Unit) =
        CaseBox().apply { function(); assertion() }

    private fun case(function: CaseBox.() -> Unit) {
        innerCaseLogic(function) {
            val actualElements = actualElements()
            assertIterableEquals(expected, actualElements)
        }
    }

    private fun iterableCase(function: CaseBox.() -> Unit) {
        innerCaseLogic(function) {
            val actualElements = actualElements()[0] as Iterable<Any?>
            assertIterableEquals(expected, actualElements)
        }
    }

    private fun arrayCase(function: CaseBox.() -> Unit) {
        innerCaseLogic(function) {
            val actualElements = actualElements()[0] as Array<out Any?>
            assertIterableEquals(expected, actualElements.toList())
        }
    }

    class CaseBox { var expected: Iterable<*>? = null }
}
