package home

class ShiftList<E>(private val list: List<E>) : AbstractList<E>() {
    var shifts = 0
    override val size = list.size

    override fun get(index: Int): E {
        val i = cycleIndex(index + size - shifts, size)
        return list[i]
    }

    fun shiftRight() {
        shifts++
        if (shifts % size == 0) {
            shifts = 0
        }
    }

    private fun cycleIndex(i: Int, length: Int): Int {
        return i % (length)
    }

    companion object {
        fun <E> List<E>.toShiftList() = ShiftList(this)
    }
}