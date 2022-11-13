package ch04

//data class Person(val name: String) {
//    object NameComparator : Comparator<Person> {
//        override fun compare(p1: Person, p2: Person): Int =
//           p1.name compareTo p2.name
//    }
//}

class Person(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person {
            TODO("Not yet implemented")
        }
    }
}
