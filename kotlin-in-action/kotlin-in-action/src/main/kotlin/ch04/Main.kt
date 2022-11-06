package ch04

fun main(args: Array<String>) {
//    val user = User("Alice")
//    user.address = "Elsenhemierstrases 47, 80687 Muenchen"

    // 4.2.5
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!")
    println(lengthCounter.counter) //3
}


