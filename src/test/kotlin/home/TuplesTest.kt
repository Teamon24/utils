package home

import home.extensions.IntRangesExtensions.times
import home.tuple.Octuple
import home.tuple.Quadruple
import home.tuple.Quintuple
import home.tuple.Septuple
import home.tuple.Sextuple
import home.tuple.TuplesExtensions.octuple
import home.tuple.TuplesExtensions.pair
import home.tuple.TuplesExtensions.quadruple
import home.tuple.TuplesExtensions.quintuple
import home.tuple.TuplesExtensions.septuple
import home.tuple.TuplesExtensions.sextuple
import home.tuple.TuplesExtensions.times
import home.tuple.to
import home.tuple.TuplesExtensions.toArray
import home.tuple.TuplesExtensions.toList
import home.tuple.TuplesExtensions.triple
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.test.assertEquals

typealias IllegalArgEx = IllegalArgumentException

class TuplesTest {
    
    private val pair      = 0 to 1
    private val triple    = 0 to 1 to 2
    private val quadruple = 0 to 1 to 2 to 3
    private val quintuple = 0 to 1 to 2 to 3 to 4
    private val sextuple  = 0 to 1 to 2 to 3 to 4 to 5
    private val septuple  = 0 to 1 to 2 to 3 to 4 to 5 to 6
    private val octuple   = 0 to 1 to 2 to 3 to 4 to 5 to 6 to 7


    @Test
    fun test() {
        assertEquals(pair      , 0         to 1)
        assertEquals(triple    , pair      to 2)
        assertEquals(quadruple , triple    to 3)
        assertEquals(quintuple , quadruple to 4)
        assertEquals(sextuple  , quintuple to 5)
        assertEquals(septuple  , sextuple  to 6)
        assertEquals(octuple   , septuple  to 7)
    }

    @Test
    fun testToList() {
        assertIterableEquals(pair.toList()      , MutableList(2) { i -> i })
        assertIterableEquals(triple.toList()    , MutableList(3) { i -> i })
        assertIterableEquals(quadruple.toList() , MutableList(4) { i -> i })
        assertIterableEquals(quintuple.toList() , MutableList(5) { i -> i })
        assertIterableEquals(sextuple.toList()  , MutableList(6) { i -> i })
        assertIterableEquals(septuple.toList()  , MutableList(7) { i -> i })
        assertIterableEquals(octuple.toList()   , MutableList(8) { i -> i })
    }

    @Test
    fun testToArray() {
        assertArrayEquals(pair.toArray()      , Array(2) { i -> i } )
        assertArrayEquals(triple.toArray()    , Array(3) { i -> i } )
        assertArrayEquals(quadruple.toArray() , Array(4) { i -> i } )
        assertArrayEquals(quintuple.toArray() , Array(5) { i -> i } )
        assertArrayEquals(sextuple.toArray()  , Array(6) { i -> i } )
        assertArrayEquals(septuple.toArray()  , Array(7) { i -> i } )
        assertArrayEquals(octuple.toArray()   , Array(8) { i -> i } )
    }

    @Test
    fun testListToTuple() {
        assertEquals(MutableList(2) { i -> i }.pair()      , pair)
        assertEquals(MutableList(3) { i -> i }.triple()    , triple)
        assertEquals(MutableList(4) { i -> i }.quadruple() , quadruple)
        assertEquals(MutableList(5) { i -> i }.quintuple() , quintuple)
        assertEquals(MutableList(6) { i -> i }.sextuple()  , sextuple)
        assertEquals(MutableList(7) { i -> i }.septuple()  , septuple)
        assertEquals(MutableList(8) { i -> i }.octuple()   , octuple)
    }

    @Test
    fun testListToTupleException() {
        assertThrows<IllegalArgEx> { MutableList(1) { Random(1).nextInt() }.pair() }
        assertThrows<IllegalArgEx> { MutableList(2) { Random(1).nextInt() }.triple() }
        assertThrows<IllegalArgEx> { MutableList(3) { Random(1).nextInt() }.quadruple() }
        assertThrows<IllegalArgEx> { MutableList(4) { Random(1).nextInt() }.quintuple() }
        assertThrows<IllegalArgEx> { MutableList(5) { Random(1).nextInt() }.sextuple() }
        assertThrows<IllegalArgEx> { MutableList(6) { Random(1).nextInt() }.septuple() }
        assertThrows<IllegalArgEx> { MutableList(7) { Random(1).nextInt() }.octuple() }
    }

    @Test
    fun testObjectToTuple() {
        val a = 1;
        assertEquals(a.pair()      , Pair(a, a))
        assertEquals(a.triple()    , Triple(a, a, a))
        assertEquals(a.quadruple() , Quadruple(a, a, a, a))
        assertEquals(a.quintuple() , Quintuple(a, a, a, a, a))
        assertEquals(a.sextuple()  , Sextuple(a, a, a, a, a, a))
        assertEquals(a.septuple()  , Septuple(a, a, a, a, a, a, a))
        assertEquals(a.octuple()   , Octuple(a, a, a, a, a, a, a, a))
    }

    @Test
    fun `testIterableToTuple case intRange`() {
        assertEquals((0..1 step 1).pair()      , pair)
        assertEquals((0..2 step 1).triple()    , triple)
        assertEquals((0..3 step 1).quadruple() , quadruple)
        assertEquals((0..4 step 1).quintuple() , quintuple)
        assertEquals((0..5 step 1).sextuple()  , sextuple)
        assertEquals((0..6 step 1).septuple()  , septuple)
        assertEquals((0..7 step 1).octuple()   , octuple)

        assertEquals(((0..1) * 2 step 2).pair()      , pair * 2)
        assertEquals(((0..2) * 2 step 2).triple()    , triple * 2)
        assertEquals(((0..3) * 2 step 2).quadruple() , quadruple * 2)
        assertEquals(((0..4) * 2 step 2).quintuple() , quintuple * 2)
        assertEquals(((0..5) * 2 step 2).sextuple()  , sextuple * 2  )
        assertEquals(((0..6) * 2 step 2).septuple()  , septuple * 2 )
        assertEquals(((0..7) * 2 step 2).octuple()   , octuple * 2 )
    }
}