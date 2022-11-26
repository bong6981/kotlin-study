package ch06

import ch03.lastchar

class Main

fun main(args: Array<String>) {
//    val p1 = Person1("Dmitry", "Jemerov")
//    val p2 = Person1("Dmitry", "Jemerov")
//    println(p1 == p2) // == 연산자는 equals 메서드 호출
//    println(p1.equals(42))
//
//    ignoreNulls(null)

    // 6.1.7 ~
//    val email: String? = ""
////    sendToEmail(email) // Type mismatch: inferred type is String? but String was expected
//
//    if (email != null) sendToEmail(email)
//
//    email?.let { sendToEmail(email) }
//    email?.let { sendToEmail(it) }

    var email: String? = "yole@example.com"
    email?.let { sendToEmail(it) } // Sending email to yole@example.com

    email = null
    email?.let { sendToEmail(it) } //

    val person: Person3? = getTheBestPersonInTheWorld()
    if (person != null) sendToEmail(person.email)

    getTheBestPersonInTheWorld()?.let { sendToEmail(it.email) }


}

data class Person3(val email: String)

//fun getTheBestPersonInTheWorld(): Person3? {
//    TODO()
//}

fun getTheBestPersonInTheWorld(): Person3? = null
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

fun sendToEmail(email: String) {
    println("Sending email to $email")
}
