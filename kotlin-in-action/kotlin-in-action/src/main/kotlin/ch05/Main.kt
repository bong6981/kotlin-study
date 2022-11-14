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


}
