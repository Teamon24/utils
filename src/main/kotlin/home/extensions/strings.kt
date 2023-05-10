package home.extensions

inline fun String.ifNotEmpty(function: () -> Unit) {
    if (isNotEmpty()) {
        function()
    }
}

inline fun String.delete(toDelete: String) = replace(toDelete, "")

