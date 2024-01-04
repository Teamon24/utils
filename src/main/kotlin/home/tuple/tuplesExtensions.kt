package home.tuple

object TuplesExtensions {
    inline operator fun Pair<Int, Int>.times(i: Int):
            Pair<Int, Int> =
        Pair(first * i, second * i)

    inline operator fun Triple<Int, Int, Int>.times(i: Int):
            Triple<Int, Int, Int> =
        Triple(first * i, second * i, third * i)

    inline operator fun Quadruple<Int, Int, Int, Int>.times(i: Int):
            Quadruple<Int, Int, Int, Int> =
        Quadruple(first * i, second * i, third * i, forth * i)

    inline operator fun Quintuple<Int, Int, Int, Int, Int>.times(i: Int):
            Quintuple<Int, Int, Int, Int, Int> =
        Quintuple(first * i, second * i, third * i, forth * i, fifth * i)

    inline operator fun Sextuple<Int, Int, Int, Int, Int, Int>.times(i: Int):
            Sextuple<Int, Int, Int, Int, Int, Int> =
        Sextuple(first * i, second * i, third * i, forth * i, fifth * i, sixth * i)

    inline operator fun Septuple<Int, Int, Int, Int, Int, Int, Int>.times(i: Int):
            Septuple<Int, Int, Int, Int, Int, Int, Int> =
        Septuple(first * i, second * i, third * i, forth * i, fifth * i, sixth * i, seventh * i)

    inline operator fun Octuple<Int, Int, Int, Int, Int, Int, Int, Int>.times(i: Int):
            Octuple<Int, Int, Int, Int, Int, Int, Int, Int> =
        Octuple(first * i, second * i, third * i, forth * i, fifth * i, sixth * i, seventh * i, eighth * i)

    /* ***************************************************************************************************************** */
/* ******************************************** Object to tuple **************************************************** */
/* ***************************************************************************************************************** */
    inline fun <reified T> T.pair() = Pair(this, this)
    inline fun <reified T> T.triple() = Triple(this, this, this)
    inline fun <reified T> T.quadruple() = Quadruple(this, this, this, this)
    inline fun <reified T> T.quintuple() = Quintuple(this, this, this, this, this)
    inline fun <reified T> T.sextuple() = Sextuple(this, this, this, this, this, this)
    inline fun <reified T> T.septuple() = Septuple(this, this, this, this, this, this, this)
    inline fun <reified T> T.octuple() = Octuple(this, this, this, this, this, this, this, this)

    /* ***************************************************************************************************************** */
/* ********************************************* Tuple to list ***************************************************** */
/* ***************************************************************************************************************** */
    inline fun <reified A, B> Pair<A, B>.toList() =
        listOf(first, second)

    inline fun <reified A, B, C> Triple<A, B, C>.toList() =
        listOf(first, second, third)

    inline fun <reified A, B, C, D> Quadruple<A, B, C, D>.toList() =
        listOf(first, second, third, forth)

    inline fun <reified A, B, C, D, E> Quintuple<A, B, C, D, E>.toList() =
        listOf(first, second, third, forth, fifth)

    inline fun <reified A, B, C, D, E, F> Sextuple<A, B, C, D, E, F>.toList() =
        listOf(first, second, third, forth, fifth, sixth)

    inline fun <reified A, B, C, D, E, F, G> Septuple<A, B, C, D, E, F, G>.toList() =
        listOf(first, second, third, forth, fifth, sixth, seventh)

    inline fun <reified A, B, C, D, E, F, G, H> Octuple<A, B, C, D, E, F, G, H>.toList() =
        listOf(first, second, third, forth, fifth, sixth, seventh, eighth)

    /* ***************************************************************************************************************** */
/* ********************************************* Tuple to array **************************************************** */
/* ***************************************************************************************************************** */
    inline fun <reified A, B> Pair<A, B>.toArray() =
        arrayOf(first, second)

    inline fun <reified A, B, C> Triple<A, B, C>.toArray() =
        arrayOf(first, second, third)

    inline fun <reified A, B, C, D> Quadruple<A, B, C, D>.toArray() =
        arrayOf(first, second, third, forth)

    inline fun <reified A, B, C, D, E> Quintuple<A, B, C, D, E>.toArray() =
        arrayOf(first, second, third, forth, fifth)

    inline fun <reified A, B, C, D, E, F> Sextuple<A, B, C, D, E, F>.toArray() =
        arrayOf(first, second, third, forth, fifth, sixth)

    inline fun <reified A, B, C, D, E, F, G> Septuple<A, B, C, D, E, F, G>.toArray() =
        arrayOf(first, second, third, forth, fifth, sixth, seventh)

    inline fun <reified A, B, C, D, E, F, G, H> Octuple<A, B, C, D, E, F, G, H>.toArray() =
        arrayOf(first, second, third, forth, fifth, sixth, seventh, eighth)

    /* ***************************************************************************************************************** */
/* ********************************************* List to tuple ***************************************************** */
/* ***************************************************************************************************************** */
    inline fun <reified T> List<T>.pair() = run {
        checkSize(2); Pair(get(0), get(1))
    }

    inline fun <reified T> List<T>.triple() = run {
        checkSize(3); Triple(get(0), get(1), get(2))
    }

    inline fun <reified T> List<T>.quadruple() = run {
        checkSize(4); Quadruple(get(0), get(1), get(2), get(3))
    }

    inline fun <reified T> List<T>.quintuple() = run {
        checkSize(5); Quintuple(get(0), get(1), get(2), get(3), get(4))
    }

    inline fun <reified T> List<T>.sextuple() = run {
        checkSize(6); Sextuple(get(0), get(1), get(2), get(3), get(4), get(5))
    }

    inline fun <reified T> List<T>.septuple() = run {
        checkSize(7); Septuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6))
    }

    inline fun <reified T> List<T>.octuple() = run {
        checkSize(8); Octuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7))
    }

    /* ***************************************************************************************************************** */
/* ********************************************* Array to tuple **************************************************** */
/* ***************************************************************************************************************** */
    inline fun <reified T> Array<T>.pair() = run {
        checkSize(2); Pair(get(0), get(1))
    }

    inline fun <reified T> Array<T>.triple() = run {
        checkSize(3); Triple(get(0), get(1), get(2))
    }

    inline fun <reified T> Array<T>.quadruple() = run {
        checkSize(4); Quadruple(get(0), get(1), get(2), get(3))
    }

    inline fun <reified T> Array<T>.quintuple() = run {
        checkSize(5); Quintuple(get(0), get(1), get(2), get(3), get(4))
    }

    inline fun <reified T> Array<T>.sextuple() = run {
        checkSize(6); Sextuple(get(0), get(1), get(2), get(3), get(4), get(5))
    }

    inline fun <reified T> Array<T>.septuple() = run {
        checkSize(7); Septuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6))
    }

    inline fun <reified T> Array<T>.octuple() = run {
        checkSize(8); Octuple(get(0), get(1), get(2), get(3), get(4), get(5), get(6), get(7))
    }

    /* ***************************************************************************************************************** */
/* ******************************************** Iterable to tuples ************************************************* */
/* ***************************************************************************************************************** */
    inline fun <reified T> Iterable<T>.pair(): Pair<T, T> = run { toPair(); }
    inline fun <reified T> Iterable<T>.triple(): Triple<T, T, T> = run { toTriple(); }
    inline fun <reified T> Iterable<T>.quadruple(): Quadruple<T, T, T, T> = run { toTuple(4); }
    inline fun <reified T> Iterable<T>.quintuple(): Quintuple<T, T, T, T, T> = run { toTuple(5); }
    inline fun <reified T> Iterable<T>.sextuple(): Sextuple<T, T, T, T, T, T> = run { toTuple(6); }
    inline fun <reified T> Iterable<T>.septuple(): Septuple<T, T, T, T, T, T, T> = run { toTuple(7); }
    inline fun <reified T> Iterable<T>.octuple(): Octuple<T, T, T, T, T, T, T, T> = run { toTuple(8); }

    inline fun <reified E, reified T : Tuple> Iterable<E>.toTuple(size: Int): T {
        if (this is List) {
            checkSize(size)
            return toTuple(this)
        }
        val list = toList()
        list.checkSize(size)
        return toTuple(list)
    }

    inline fun <reified E, reified T : Tuple> toTuple(list: List<E>): T {
        return when (T::class.java) {
            Quadruple::class.java -> list.quadruple() as T
            Quintuple::class.java -> list.quintuple() as T
            Sextuple::class.java -> list.sextuple() as T
            Septuple::class.java -> list.septuple() as T
            Octuple::class.java -> list.octuple() as T
            else -> throw UnsupportedOperationException("there is no when branch for type: ${T::class}")
        }
    }

    inline fun <reified E> Iterable<E>.toTriple(): Triple<E, E, E> {
        return toList().run {
            val tripleSize = 3
            require(size == tripleSize) { iterableSizeMessage(tripleSize) }
            triple()
        }
    }

    inline fun <reified E> Iterable<E>.toPair(): Pair<E, E> {
        return toList().run {
            val pairSize = 2
            require(size == pairSize) { iterableSizeMessage(pairSize) }
            pair()
        }
    }

    inline fun listSizeMessage(i: Int) = "Size of the list should be '$i'"
    inline fun arraySizeMessage(i: Int) = "Size of the array should be '$i'"
    inline fun iterableSizeMessage(i: Int) = "Size of the iterable should be '$i'"

    inline fun <reified T> List<T>.checkSize(i: Int) = require(size == i) { listSizeMessage(i) }
    inline fun <reified T> Array<T>.checkSize(i: Int) = require(size == i) { arraySizeMessage(i) }
}