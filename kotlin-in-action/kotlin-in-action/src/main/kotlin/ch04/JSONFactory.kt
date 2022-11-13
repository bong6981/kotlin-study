package ch04

interface JSONFactory<T> {
    fun fromJSON(jsonText : String): T
}

fun <T> loadFromJson(factory: JSONFactory<T>) : T {
 TODO()
}
