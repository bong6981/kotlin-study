package ch02

import java.io.BufferedReader
import java.lang.NumberFormatException

class ExceptionHandlePractice {

}

fun readNumber(reader: BufferedReader) : Int? {
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    }
    catch (e: NumberFormatException) {
        return null
    }
    finally {
        reader.close()
    }
}
