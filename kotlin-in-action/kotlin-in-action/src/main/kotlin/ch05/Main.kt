package ch05

import java.util.*

fun main(args: Array<String>) {
//    val people = listOf(Person("Alice", 29), Person("Bob", 31))
//    println(people.maxBy { it.age })
//    println(people.maxBy(Person::age))
//    println(people.maxBy {p: Person -> p.age})
//
//    val sum = {x: Int, y: Int -> x + y}
//    println(sum(3, 5))
//    run{ println(42) }

//

    // 5.2.1
//    val list = listOf(1, 2, 3, 4)
//    println(list.filter { it % 2 == 0 }) // [2, 4]

//    val people = listOf(Person("Alice", 45), Person("Bob", 31))
//    println(people.filter { it.age > 30 }) // [Person(name=Alice, age=45), Person(name=Bob, age=31)]
//
//    val list = listOf(1, 2, 3, 4)
//    println(list.map{ it * it })

    val people = listOf(Person("Alice", 45), Person("Bob", 31))
    println(people.map { it.name }) // [Alice, Bob]
    println(people.map(Person::name)) // [Alice, Bob]

    println(people.filter { it.age > 30 }.map(Person::name))

    println(people.filter { it.age == people.maxBy(Person::age)!!.age })

    val maxAge = people.maxBy(Person::age)!!.age
    people.filter { it.age == maxAge }

    val numbers = mapOf(0 to "zero", 1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() }) // {0=ZERO, 1=ONE}
    println(numbers.mapValues { it.value.uppercase(Locale.getDefault()) }) // {0=ZERO, 1=ONE}

}

fun Person.isAdult() = age >= 21

fun salute() = println("Salute!!")

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")
    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var severErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            severErrors++
        }
    }
    println("$clientErrors client errors, $severErrors server errors")
}
