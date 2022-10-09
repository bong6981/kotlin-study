package ch02

class SmartCast {
}

interface Expr
class Num(val value: Int) : Expr // value라는 프로퍼티만 존재하는 클래스로 Expr 인터페이스 구현
class Sum(val left: Expr, val right: Expr) : Expr // Expr 타입의 객체라면 어떤 것이나 Sum연산의 인자가 될 수 있다 따라서 Num이나 다른 Sum이 인자로 올 수 있다

fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }

    throw IllegalArgumentException("Unknown expression")
}
