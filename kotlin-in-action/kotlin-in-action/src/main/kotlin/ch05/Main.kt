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
//
//    val people = listOf(Person("Alice", 45), Person("Bob", 31))
//    println(people.map { it.name }) // [Alice, Bob]
//    println(people.map(Person::name)) // [Alice, Bob]
//
//    println(people.filter { it.age > 30 }.map(Person::name))
//
//    println(people.filter { it.age == people.maxBy(Person::age)!!.age })
//
//    val maxAge = people.maxBy(Person::age)!!.age
//    people.filter { it.age == maxAge }
//
//    val numbers = mapOf(0 to "zero", 1 to "one")
//    println(numbers.mapValues { it.value.toUpperCase() }) // {0=ZERO, 1=ONE}
//    println(numbers.mapValues { it.value.uppercase(Locale.getDefault()) }) // {0=ZERO, 1=ONE}

    val canBeInClub27 = { p: Person -> p.age > 27 }
//    val people = listOf(Person("Alice", 27), Person("Bob", 31))
//    println(people.all(canBeInClub27)) // false
//    println(people.any(canBeInClub27)) //true

    // 5.2.2 ~
//    val list = listOf(1, 2, 3)
//    println(!list.all{it == 3})  // true
//    /**
//     * !를 눈치 채지 못하는 경우가 자주 있다 이럴 땐 any가 더 낫다
//     */
//    println(list.any{it == 3}) // ture any를 사용하려면 술어를 부정해야 한다
//
//    val people = listOf(Person("Alice", 27), Person("Bob", 31))
////    println(people.count(canBeInClub27))
//    println( people.filter(canBeInClub27).size)
//    println(people.find(canBeInClub27)) // Person(name=Bob, age=31)

    //5.2.3 ~
//    val people = listOf(Person("Alice", 27), Person("Bob", 29), Person("Carol", 31))
//    println(people.groupBy { it.age }) //{27=[Person(name=Alice, age=27)], 29=[Person(name=Bob, age=29)], 31=[Person(name=Carol, age=31)]}
//
//    val list = listOf("a", "ab", "b")
//    println(list.groupBy(String::first))

    //5.2.4 ~
//    val strings = listOf("abc", "def")
//    println(strings.flatMap { it.toList() })
//
//    val books = listOf(
//        Book("Thursday Next", listOf("Jasper Fforde")),
//        Book("Mort", listOf("Terry Pratchett")),
//        Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman"))
//    )
//    println(books.flatMap { it.authors }.toSet()) // [Jasper Fforde, Terry Pratchett, Neil Gaiman]
//    println(books.flatMap { it.authors }) // [Jasper Fforde, Terry Pratchett, Terry Pratchett, Neil Gaiman]

    //5.3 ~
    val people = listOf(Person("Alice", 27), Person("Bob", 29), Person("Carol", 31))
    println(people.map(Person::name).filter { it.startsWith("A") })

    people.asSequence()
        .map(Person::name)
        .filter { it.startsWith("A") }
        .toList()
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
