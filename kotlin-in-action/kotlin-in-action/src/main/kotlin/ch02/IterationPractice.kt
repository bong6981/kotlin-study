package ch02

import java.util.TreeMap

class IterationPractice {
}

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz"
    i % 5 == 0 -> "Buzz"
    else -> "$i"
}

// 문자에 대한 2진 표현 출력 프로그램
// 2진 표현을 맵에 저장
fun main(args: Array<String>) {
   val binaryReps = TreeMap<Char, String>()

    for ( c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    val list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index: $element")
    }

    println(isLetter('q'))
    println(isNotDigit('x'))
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

//when에서 in 사용하기
fun recognize(c:Char) = when(c) {
    in '0'..'9' -> "it's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "it's letter"
    else -> "i dont know"
}
