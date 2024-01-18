package home.extensions

import java.util.*

object StringsExtensions {

    inline fun String.isNotEmpty(function: () -> Unit) {
        if (isNotEmpty()) {
            function()
        }
    }

    inline fun String.delete(toDelete: String) = replace(toDelete, "")

    inline val String.decapitalized: String get() = replaceFirstChar { it.lowercase(Locale.getDefault()) }

    @Deprecated("Use decapitalized instead.", ReplaceWith("this.decapitalized"))
    inline val String.lowercaseFirst: String
        get() {
            val chars = toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return String(chars);
        }
}


