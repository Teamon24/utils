package home.extensions

object StringsExtensions {

    inline fun String.ifNotEmpty(function: () -> Unit) {
        if (isNotEmpty()) {
            function()
        }
    }

    inline fun String.delete(toDelete: String) = replace(toDelete, "")

    inline val String.lowercaseFirst: String
        get() {
            val chars = toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return String(chars);
        }
}


