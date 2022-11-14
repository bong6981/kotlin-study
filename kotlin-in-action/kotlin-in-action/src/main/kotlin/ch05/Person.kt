package ch05

data class Person (val name: String, val age:Int)

fun findTheOldest(people: List<Person>) {
    var maxAge = 0 // 가장 많은 나이 저장
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}
