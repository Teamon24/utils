package home.extensions

inline val Int.isEven: Boolean get() = this % 2 == 0
inline val Int.isOdd: Boolean get() = this % 2 != 0