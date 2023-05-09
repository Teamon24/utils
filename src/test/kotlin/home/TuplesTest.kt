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
        assertEquals(pair.toList(), list(2, any))
        assertEquals(triple.toList(), list(3, any))
        assertEquals(quadruple.toList(), list(4, any))
        assertEquals(quintuple.toList(), list(5, any))
        assertEquals(septuple.toList(), list(6, any))
        assertEquals(octuple.toList(), list(7, any))
    }

    @Test
    fun testToArray() {
        assertArrayEquals(pair.toArray() as Array<Any>, array(2, any))
        assertArrayEquals(triple.toArray() as Array<Any>, array(3, any))
        assertArrayEquals(quadruple.toArray() as Array<Any>, array(4, any))
        assertArrayEquals(quintuple.toArray() as Array<Any>, array(5, any))
        assertArrayEquals(septuple.toArray() as Array<Any>, array(6, any))
        assertArrayEquals(octuple.toArray() as Array<Any>, array(7, any))
    }

    private fun list(size: Int, any: Any): List<Any> =
        ArrayList<Any>(size).apply { repeat(size) { add(any) } }

    private fun array(size: Int, any: Any): Array<Any> {
        return Array(size) { any }
    }
}