package ch02

import java.io.BufferedReader
import java.lang.NumberFormatException

class ExceptionHandlePractice {

}

fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    }
    catch (e: NumberFormatException) {
        null
    }
    println(number)
}
