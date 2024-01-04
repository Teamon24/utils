package home.extensions

object IntRangesExtensions {
    operator fun IntProgression.times(i: Int): IntProgression {
        return first * i .. last * i step step
    }

    inline val IntRange.size: Int get() = last - first + 1

    inline fun Int.until(number: Int, onEach: (Int) -> Unit) {
        for (i in this until number) {
            onEach(i)
        }
    }

    inline fun Int.range(number: Int, onEach: (Int) -> Unit) {
        for (i in this .. number) {
            onEach(i)
        }
    }
}