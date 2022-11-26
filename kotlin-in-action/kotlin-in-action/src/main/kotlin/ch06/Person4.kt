package ch06

class Person4(
    val name: String,
    val age: Int? = null
) {
    fun isOrderThan(other: Person4): Boolean? {
        if (age == null || other.age == null) return null
        return age > other.age
    }
}
