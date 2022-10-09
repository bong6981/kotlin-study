package ch02

import java.lang.Exception

//프로퍼티와 메서드가 있는 enum클래스 선언하기
enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0), YELLOW(255, 255, 0),
    GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238); // 여긴 반드시 세미콜론 ; 을 사용해야 한다

    fun rgb() = (r * 256 + g) * 256 + b

    fun getMnemonic(color: Color) =
        when (color) {
            RED -> "Richard"
            ORANGE -> "Of"
            YELLOW -> "York"
            else -> "Else"
        }

    //한 when 분기 안에 여러 값 사용하기, 상수값 import 시에 Color 생략 가능
    fun getWarmth(color: Color) = when (color) {
        Color.RED, Color.ORANGE, YELLOW -> "warm"
        Color.GREEN -> "natural"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
    }

    fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }

    fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == RED && c2 == ORANGE) || (c1 == ORANGE && c2 == RED) ->
                ORANGE
            // ...
            else -> throw Exception("Dirty color")
        }
}
