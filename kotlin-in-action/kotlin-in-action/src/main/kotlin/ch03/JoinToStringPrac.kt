@file:JvmName("StringFuctions")

package ch03

fun <T> Collection<T>.joinToString( // Collection<T> 에 대한 확장 함수를 선언한다
    separator: String = ",", // 파라미터 디폴트 값을 지정한다
    prefix: String = "",
    postfix: String = ""
) : String {
    val result = StringBuilder(prefix)
    for ( (index, element) in this.withIndex()) { //this는 수신 객체를 가리킨다. 여기서는 T타입의 원소로 이뤄진 컬렉션
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun Collection<String>.join(
    separator: String = ":",
    prefix: String = "",
    postfix: String = ""
) = joinToString(separator, prefix, postfix)
