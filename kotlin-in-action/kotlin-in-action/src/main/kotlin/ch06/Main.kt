package ch06

import ch03.lastchar
import java.util.*

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

//    var email: String? = "yole@example.com"
//    email?.let { sendToEmail(it) } // Sending email to yole@example.com
//
//    email = null
//    email?.let { sendToEmail(it) } //
//
//    val person: Person3? = getTheBestPersonInTheWorld()
//    if (person != null) sendToEmail(person.email)
//
//    getTheBestPersonInTheWorld()?.let { sendToEmail(it.email) }

    //6.1.9 ~
//    verifyUserInput(" ")
//    verifyUserInput(null)

    // 6.1.10
//    printHashCode(null) // T 타입은 Any? 로 추론된다
//    println(null.hasCode())

    // 6.1.11
//    val person = PersonJava(null)
////    yellAt(person)
//
////    val i: Int = person.name
//
//    val name1: String = person.name
//    val name2: String? = person.name

    // 6.1.12
    showProgress(146)
}

fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done!")
}

fun yellAt(person: PersonJava) {
    println((person.name ?: "AnyOne").toUpperCase() + "! ! !") // ANYONE! ! !
}

//fun yellAt(person: PersonJava) {
//    println(person.name)
//    println(person.name.toUpperCase() + " ! ! !")
//    // toUpperCase 수신 객체인 person.name이 널이라 예외가 발생한다
//    // person.name must not be null
//}

fun <T: Any> printHashCode(t: T) { // 이제 T는 널이 될 수 없는 타입
    println(t.hashCode())
}

//fun <T> printHashCode(t: T) {
//    println(t.hashCode()) //
//    println(t?.hashCode()) // t가 널이 될수 있어서 안전한 호출을 써야만 한다
//}


fun String?.isNullOrBlank(): Boolean = // 널이 될 수 있는 String 의 확장
    this == null || this.isBlank() // 두번째 this 에는 스마트 캐스트 적용

fun verifyUserInput(input: String?) {
    if (input.isNullOrBlank()) { // 안전한 호출을 하지 않아도 된다
        println("Please fill in the required fields")
    }
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

class StringPrinter: StringProcessor {
    override fun process(value: String) {
        println(value)
    }
}

class NullableStringPrinter: StringProcessor {
    override fun process(value: String?) {
        println(value)
    }
}
