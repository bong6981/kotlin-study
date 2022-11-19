package ch05

fun main(args: Array<String>) {
//    val people = listOf(Person("Alice", 29), Person("Bob", 31))
//    println(people.maxBy { it.age })
//    println(people.maxBy(Person::age))
//    println(people.maxBy {p: Person -> p.age})
//
//    val sum = {x: Int, y: Int -> x + y}
//    println(sum(3, 5))
//    run{ println(42) }

    val people = listOf(Person("이몽룡", 29), Person("성춘향", 31))
    var names = people.joinToString(separator = " ",
        transform = { p: Person -> p.name })
    println(names)
    names = people.joinToString(separator = " ") {
            p: Person -> p.name
    }
    println(names)

    // 5.1.4 ~
    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessageWithPrefix(errors, "Error : ")

    val responses = listOf("200 ok", "418 i'm a teapot", "500 internal server error")
    printProblemCounts(responses)

    // 5.1.5 ~
    run(::salute) // Salute!!

    val createPerson = ::Person
    val p = createPerson("Alice", 29)
    println(p)

    val predicate = Person::isAdult
}

fun Person.isAdult() = age >= 21

fun salute() = println("Salute!!")

fun printMessageWithPrefix(messages: Collection<String>, prefix:String) {
    messages.forEach{
        println("$prefix $it")
    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var severErrors = 0
    responses.forEach{
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            severErrors++
        }
    }
    println("$clientErrors client errors, $severErrors server errors")
}
