package ch04

import java.time.Clock

fun main(args: Array<String>) {
//    val user = User("Alice")
//    user.address = "Elsenhemierstrases 47, 80687 Muenchen"

    // 4.2.5
//    val lengthCounter = LengthCounter()
//    lengthCounter.addWord("Hi!")
//    println(lengthCounter.counter) //3

    // 4.3.1
//    val client1 = Client("오현석", 4122)
////    println(client1)
//
//    val client2 = Client("오현석", 4122)
//    println(client1 == client2)

    val processed = hashSetOf(Client("오현석", 4122))
    println(processed.contains(Client("오현석", 4122))) // false

}


