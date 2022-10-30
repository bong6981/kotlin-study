package ch03

class MakeCollectionPractice {
    fun practice() {
        val set = hashSetOf(1, 7, 53)
        val list = listOf(1, 7, 53)
        val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

        println(set.javaClass)
        println(list.javaClass)
        println(map.javaClass)
    }

    fun moreThanJava() {
        val strings = listOf("first", "second", "third")
        println(strings.last())
        val numbers = setOf(1, 14, 2)
        println(numbers.max())
    }
}
