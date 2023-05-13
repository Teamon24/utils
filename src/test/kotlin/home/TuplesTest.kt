package home

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TuplesTest {

    private val any        = 2
    private val pair       = any to any
    private val triple     = any to any to any
    private val quadruple  = any to any to any to any
    private val quintuple  = any to any to any to any to any
    private val sextuple   = any to any to any to any to any to any
    private val septuple   = any to any to any to any to any to any to any
    private val octuple    = any to any to any to any to any to any to any to any

    @Test
    fun test() {
        assertEquals(pair, any to any)

        assertEquals(triple, pair to any)
        assertEquals(triple, any to pair)

        assertEquals(quadruple, pair to pair)
        assertEquals(quadruple, any to triple)
        assertEquals(quadruple, triple to any)

        assertEquals(quintuple, any to quadruple)
        assertEquals(quintuple, pair to triple)
        assertEquals(quintuple, triple to pair)
        assertEquals(quintuple, quadruple to any)

        assertEquals(sextuple, any to quintuple)
        assertEquals(sextuple, pair to quadruple)
        assertEquals(sextuple, triple to triple)
        assertEquals(sextuple, quadruple to pair)
        assertEquals(sextuple, quintuple to any)

        assertEquals(septuple, any to sextuple)
        assertEquals(septuple, pair to quintuple)
        assertEquals(septuple, triple to quadruple)
        assertEquals(septuple, quadruple to triple)
        assertEquals(septuple, quintuple to pair)
        assertEquals(septuple, sextuple to any)

        assertEquals(octuple, any to septuple)
        assertEquals(octuple, pair to sextuple)
        assertEquals(octuple, triple to quintuple)
        assertEquals(octuple, quadruple to quadruple)
        assertEquals(octuple, quintuple to triple)
        assertEquals(octuple, sextuple to pair)
        assertEquals(octuple, septuple to any)
    }

    @Test
    fun testToList() {
        assertEquals(list(2, any), pair.toList())
        assertEquals(list(3, any), triple.toList())
        assertEquals(list(4, any), quadruple.toList())
        assertEquals(list(5, any), quintuple.toList())
        assertEquals(list(6, any), sextuple.toList())
        assertEquals(list(7, any), septuple.toList())
        assertEquals(list(8, any), octuple.toList())
    }

    @Test
    fun testToArray() {
        assertArrayEquals(array(2, any), pair.toArray() as Array<Any>)
        assertArrayEquals(array(3, any), triple.toArray() as Array<Any>)
        assertArrayEquals(array(4, any), quadruple.toArray() as Array<Any>)
        assertArrayEquals(array(5, any), quintuple.toArray() as Array<Any>)
        assertArrayEquals(array(6, any), sextuple.toArray() as Array<Any>)
        assertArrayEquals(array(7, any), septuple.toArray() as Array<Any>)
        assertArrayEquals(array(8, any), octuple.toArray() as Array<Any>)
    }

    private fun list(size: Int, any: Any): List<Any> =
        ArrayList<Any>(size).apply { repeat(size) { add(any) } }

    private fun array(size: Int, any: Any): Array<Any> {
        return Array(size) { any }
    }
}