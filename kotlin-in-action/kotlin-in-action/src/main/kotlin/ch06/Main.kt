package ch06

class Main

fun main(args: Array<String>) {
    val p1 = Person1("Dmitry", "Jemerov")
    val p2 = Person1("Dmitry", "Jemerov")
    println(p1 == p2) // == 연산자는 equals 메서드 호출
    println(p1.equals(42))
}
