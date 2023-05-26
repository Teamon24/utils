package home

data class Quadruple<out A, out B, out C, out D>
constructor(val first: A, val second: B, val third: C, val forth: D) {
    override fun toString(): String = toString(arrayOf(first, second, third, forth))
}

data class Quintuple<out A, out B, out C, out D, out E>
constructor(val first: A, val second: B, val third: C, val forth: D, val fifth: E) {
    override fun toString(): String = toString(arrayOf(first, second, third, forth, fifth))
}

data class Sextuple<A, B, C, D, E, F>
constructor(val first: A, val second: B, val third: C, val forth: D, val fifth: E, val sixth: F) {
    override fun toString(): String = toString(arrayOf(first, second, third, forth, fifth, sixth))
}

data class Septuple<A, B, C, D, E, F, G>
constructor(val first: A, val second: B, val third: C, val forth: D, val fifth: E, val sixth: F, val seventh: G) {
    override fun toString(): String = toString(arrayOf(first, second, third, forth, fifth, sixth, seventh))
}

data class Octuple<A, B, C, D, E, F, G, H>
constructor(val first: A, val second: B, val third: C, val forth: D, val fifth: E, val sixth: F, val seventh: G, val eighth: H) {
    override fun toString(): String = toString(arrayOf(first, second, third, forth, fifth, sixth, seventh, eighth))
}

private fun toString(anys: Array<*>): String = "(${anys.joinToString(separator = ", ", truncated = "") { it.toString() }})"


inline fun <reified T> Pair      <T, T>                . toList(): List<T> = listOf(first, second)
inline fun <reified T> Triple    <T, T, T>             . toList(): List<T> = listOf(first, second, third)
inline fun <reified T> Quadruple<T, T, T, T>           . toList(): List<T> = listOf(first, second, third, forth)
inline fun <reified T> Quintuple<T, T, T, T, T>        . toList(): List<T> = listOf(first, second, third, forth, fifth)
inline fun <reified T> Sextuple<T, T, T, T, T, T>      . toList(): List<T> = listOf(first, second, third, forth, fifth, sixth)
inline fun <reified T> Septuple<T, T, T, T, T, T, T>   . toList(): List<T> = listOf(first, second, third, forth, fifth, sixth, seventh)
inline fun <reified T> Octuple<T, T, T, T, T, T, T, T> . toList(): List<T> = listOf(first, second, third, forth, fifth, sixth, seventh, eighth)

inline fun <reified T> Pair      <T, T>                . toArray(): Array<T> = arrayOf(first, second)
inline fun <reified T> Triple    <T, T, T>             . toArray(): Array<T> = arrayOf(first, second, third)
inline fun <reified T> Quadruple<T, T, T, T>           . toArray(): Array<T> = arrayOf(first, second, third, forth)
inline fun <reified T> Quintuple<T, T, T, T, T>        . toArray(): Array<T> = arrayOf(first, second, third, forth, fifth)
inline fun <reified T> Sextuple<T, T, T, T, T, T>      . toArray(): Array<T> = arrayOf(first, second, third, forth, fifth, sixth)
inline fun <reified T> Septuple<T, T, T, T, T, T, T>   . toArray(): Array<T> = arrayOf(first, second, third, forth, fifth, sixth, seventh)
inline fun <reified T> Octuple<T, T, T, T, T, T, T, T> . toArray(): Array<T> = arrayOf(first, second, third, forth, fifth, sixth, seventh, eighth)

/* *************************************************************************************************************************************** */
/* **************************************************************** Triple-3 ************************************************************* */
/* *************************************************************************************************************************************** */
/**
 * [Any] to [Pair] = [Triple];
 */
infix fun <A, B, C>
        A.to(pair: Pair<B, C>) = Triple(this, pair.first, pair.second)

/**
 * [Pair] to [Any] = [Triple];
 */
infix fun <A, B, C>
        Pair<A, B>.to(third: C) = Triple(first, second, third)
/* *************************************************************************************************************************************** */
/* *************************************************************** Quadruple-4 *********************************************************** */
/* *************************************************************************************************************************************** */
/**
 * [1] to [3] = [4];
 * [Any] to [Triple] = [Quadruple];
 */
infix fun <A, B, C, D>
        A.to(triple: Triple<B, C, D>) = Quadruple(this, triple.first, triple.second, triple.third)

/**
 * [2] to [2] = [4];
 * [Pair] to [Pair] = [Quadruple];
 */
infix fun <A, B, C, D>
        Pair<A, B>.to(pair: Pair<C, D>) = Quadruple(first, second, pair.first, pair.second)

/**
 * [3] to [1] = [4];
 * [Triple] to [Any] = [Quadruple];
 */
infix fun <A, B, C, D>
        Triple<A, B, C>.to(fourth: D) = Quadruple(first, second, third, fourth)

/* *************************************************************************************************************************************** */
/* *************************************************************** Quintuple-5 *********************************************************** */
/* *************************************************************************************************************************************** */
/**
 * [1] to [4] = [5];
 * [Any] to [Quadruple] = [Quintuple];
 */
infix fun <A, B, C, D, E>
        A.to(quadruple: Quadruple<B, C, D, E>) = Quintuple(this, quadruple.first, quadruple.second, quadruple.third, quadruple.forth)

/**
 * [2] to [3] = [5];
 * [Pair] to [Triple]  = [Quintuple];
 */
infix fun <A, B, C, D, E>
        Pair<A, B>.to(triple: Triple<C, D, E>) = Quintuple(first, second, triple.first, triple.second, triple.third)

/**
 * [3] to [2] = [5];
 * [Triple] to [Pair] = [Quintuple];
 */
infix fun <A, B, C, D, E>
        Triple<A, B, C>.to(pair: Pair<D, E>) = Quintuple(first, second, third, pair.first, pair.second)

/**
 * [4] to [1] = [5];
 * [Quadruple] to [Any] = [Quintuple];
 */
infix fun <A, B, C, D, E>
        Quadruple<A, B, C, D>.to(fifth: E) = Quintuple(first, second, third, forth, fifth)

/* *************************************************************************************************************************************** */
/* *************************************************************** Sextuple-6 ************************************************************ */
/* *************************************************************************************************************************************** */
/**
 * [1] to [5] = [6];
 * [Any] to [Quintuple] = [Sextuple];
 */
infix fun <A, B, C, D, E, F>
        A.to(quintuple: Quintuple<B, C, D, E, F>) = Sextuple(this, quintuple.first, quintuple.second, quintuple.third, quintuple.forth, quintuple.fifth)

/**
 * [2] to [4] = [6];
 * [Pair] to [Quadruple] = [Sextuple];
 */
infix fun <A, B, C, D, E, F>
        Pair<A, B>.to(quadruple: Quadruple<C, D, E, F>) = Sextuple(first, second, quadruple.first, quadruple.second, quadruple.third, quadruple.forth)

/**
 * [3] to [3] = [6];
 * [Triple] to [Triple] = [Sextuple];
 */
infix fun <A, B, C, D, E, F>
        Triple<A, B, C>.to(triple: Triple<D, E, F>) = Sextuple(first, second, third, triple.first, triple.second, triple.third)

/**
 * [4] to [2] = [6];
 * [Quadruple] to [Pair] = [Sextuple];
 */
infix fun <A, B, C, D, E, F>
        Quadruple<A, B, C, D>.to(pair: Pair<E, F>) = Sextuple(first, second, third, forth, pair.first, pair.second)

/**
 * [5] to [1] = [6];
 * [Quintuple] to [Any] = [Sextuple];
 */
infix fun <A, B, C, D, E, F>
        Quintuple<A, B, C, D, E>.to(sixth: F) = Sextuple(first, second, third, forth, fifth, sixth)

/* *************************************************************************************************************************************** */
/* *************************************************************** Septuple-7 ************************************************************ */
/* *************************************************************************************************************************************** */

/**
 * [1] to [6] = [7];
 * [Any] to [Sextuple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        A.to(sextuple: Sextuple<B, C, D, E, F, G>) = Septuple(this, sextuple.first, sextuple.second, sextuple.third, sextuple.forth, sextuple.fifth, sextuple.sixth)
/**
 * [2] to [5] = [7];
 * [Pair] to [Quintuple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        Pair<A, B>.to(quintuple: Quintuple<C, D, E, F, G>) = Septuple(first, second, quintuple.first, quintuple.second, quintuple.third, quintuple.forth, quintuple.fifth)
/**
 * [3] to [4] = [7];
 * [Triple] to [Quadruple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        Triple<A, B, C>.to(quadruple: Quadruple<D, E, F, G>) = Septuple(first, second, third, quadruple.first, quadruple.second, quadruple.third, quadruple.forth)

/**
 * [4] to [3] = [7];
 * [Quadruple] to [Triple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        Quadruple<A, B, C, D>.to(triple: Triple<E, F, G>) = Septuple(first, second, third, forth, triple.first, triple.second, triple.third)

/**
 * [5] to [2] = [7];
 * [Quintuple] to [Pair] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        Quintuple<A, B, C, D, E>.to(pair: Pair<F, G>) = Septuple(first, second, third, forth, fifth, pair.first, pair.second)

/**
 * [6] to [1] = [7];
 * [Sextuple] to [Any] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G>
        Sextuple<A, B, C, D, E, F>.to(seventh: G) = Septuple(first, second, third, forth, fifth, sixth, seventh)

/* *************************************************************************************************************************************** */
/* *************************************************************** Octuple-8 ************************************************************* */
/* *************************************************************************************************************************************** */

/**
 * [1] to [7] = [8];
 * [Any] to [Septuple] = [Octuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        A.to(`7`: Septuple<B, C, D, E, F, G, H>) = Octuple(this, `7`.first, `7`.second, `7`.third, `7`.forth, `7`.fifth, `7`.sixth, `7`.seventh)
/**
 * [2] to [6] = [8];
 * [Pair] to [Sextuple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Pair<A, B>.to(`6`: Sextuple<C, D, E, F, G, H>)  = Octuple(first, second, `6`.first, `6`.second, `6`.third, `6`.forth, `6`.fifth, `6`.sixth)
/**
 * [3] to [5] = [8];
 * [Triple] to [Quintuple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Triple<A, B, C>.to(`5`: Quintuple<D, E, F, G, H>) = Octuple(first, second, third, `5`.first, `5`.second, `5`.third, `5`.forth, `5`.fifth)

/**
 * [4] to [4] = [8];
 * [Quadruple] to [Quadruple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Quadruple<A, B, C, D>.to(`4`: Quadruple<E, F, G, H>) = Octuple(first, second, third, forth, `4`.first, `4`.second, `4`.third, `4`.forth)

/**
 * [5] to [3] = [8];
 * [Quadruple] to [Triple] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Quintuple<D, E, F, G, H>.to(`3`: Triple<A, B, C>) = Octuple(first, second, third, forth, fifth, `3`.first, `3`.second, `3`.third)

/**
 * [6] to [2] = [8];
 * [Sextuple] to [Pair] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Sextuple<A, B, C, D, E, F>.to(`2`: Pair<G, H>) = Octuple(first, second, third, forth, fifth, sixth, `2`.first, `2`.second)

/**
 * [7] to [1] = [8];
 * [Septuple] to [Any] = [Septuple]
 */
infix fun <A, B, C, D, E, F, G, H>
        Septuple<A, B, C, D, E, F, G>.to(eighth: H) = Octuple(first, second, third, forth, fifth, sixth, seventh, eighth)