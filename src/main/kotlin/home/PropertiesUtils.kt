package home

object PropertiesUtils {

    fun prettyPrint(properties: Map<String, Any>) {
        println(prettyToString(properties))
    }

    fun prettyToString(properties: Map<String, *>): String {
        val max = properties.maxBy { it.key.length }.key
        return properties
            .asSequence()
            .sortedBy { it.key }
            .map { entry -> entry to max.length - entry.key.length }
            .map {
                val entry = it.first
                val diff = it.second
                "${entry.key + " ".repeat(diff)} = ${entry.value}"
            }
            .joinToString(separator = "\n") { it }
    }
}