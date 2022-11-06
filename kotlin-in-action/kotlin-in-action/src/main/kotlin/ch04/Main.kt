package ch04

import java.io.File
import java.time.Clock

fun main(args: Array<String>) {
//    val user = User("Alice")
//    user.address = "Elsenhemierstrases 47, 80687 Muenchen"

    // 4.2.5
//    val lengthCounter = LengthCounter()
//    lengthCounter.addWord("Hi!")
//    println(lengthCounter.counter) //3

    // 4.3.1 ~ 4.3.5
//    val client1 = Client("오현석", 4122)
//    println(client1)
//
//    val client2 = Client("오현석", 4122)
//    println(client1 == client2)
//
//    val processed = hashSetOf(Client("오현석", 4122))
//    println(processed.contains(Client("오현석", 4122))) // false
//
//    val lee = Client("이계명", 4223)
//    lee.copy(postalCode = 4000)
//    println(lee)

    // 4.4.1 ~
//    println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user"))) // 0
//
//    val files = listOf(File("/z"), File("/a"))
//    println(files.sortedWith(CaseInsensitiveFileComparator)) // [/a, /z]
//
//    val persons = listOf(Person("Bob"), Person("Alice"))
//    println(persons.sortedWith(Person.NameComparator)) // [Person(name=Alice), Person(name=Bob)]

    // 4.4.2 ~
    val subscribingUser = User.newSubscribingUser("bog@gmail.com")
    val facebookUser = User.newFacebookUser(4)
}


