package home

object ExceptionUtils {
    fun throwsOn(function: () -> Unit): Boolean {
        return try {
            function()
            false
        } catch (e: Exception) {
            true
        }
    }
}