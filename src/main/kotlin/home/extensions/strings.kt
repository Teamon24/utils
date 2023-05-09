package home.extensions

inline fun String.ifNotEmpty(function: () -> Unit) {
    if (isNotEmpty()) {
        function()
    }
}

