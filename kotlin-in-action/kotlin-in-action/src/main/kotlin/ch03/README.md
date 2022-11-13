# 3.1 코틀린에서 컬렉션 만들기 
```kotlin
   fun practice() {
        val set = hashSetOf(1, 7, 53)
        val list = listOf(1, 7, 53)
        val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

        println(set.javaClass) //class java.util.HashSet
        println(list.javaClass) //class java.util.Arrays$ArrayList
        println(map.javaClass) //class java.util.HashMap
    }
```
- 컬렉션 만들어서 출력하면 결과과 자바 컬렉션. 코틀린이 자신만의 컬렉션 기능을 제공하지 않는다. 
  - 자바 코드와 상호 작용하기가 편해서 
- 코틀린 컬렉션은 자바 컬렉션과 똑같은 클래스이지만 자바보다 더 많은 기능 쓸 수 있다. 
```kotlin
  fun moreThanJava() {
        val strings = listOf("first", "second", "third")
        println(strings.last()) // "third"
        val numbers = setOf(1, 14, 2)
        println(numbers.max()) //14
    }
```

# 3.2 함수를 호출하기 쉽게 만들기
```kotlin
 fun basic() {
        val list = listOf(1, 2, 3)
        println(list) //[1, 2, 3]
    }
```
- 원소 사이를 세미콜론으로 구분하고 괄호로 리스트를 둘러싸고 싶다면? 
```kotlin
   fun <T> joinToString(
        collection: Collection<T>,
        separator: String,
        prefix: String,
        postfix: String
    ) : String {
        val result = StringBuilder(prefix)
        for ( (index, element) in collection.withIndex()) {
            if (index > 0) result.append(separator)
            result.append(element)
        }
        result.append(postfix)
        return result.toString()
    }
```
- 좀 더 간편하게 호출?

## 3.2.1 이름 붙인 인자 
```kotlin
joinToString(collection = list, separator = ":", prefix = "(", postfix = ")")
```
- 인자 중 일부 또는 전부를 명시 가능 
- 인자 중 어느 하라도 이름을 명시하고 나면 그 뒤에 오는 모든 인자는 이름을 꼭 명시해야 한다 
  - 이름 명시하기 때문에 인자로 들어오는 변수 바꿀 때 리팩터링 활용해서 바꾸기 
- 자바로 작성한 코드 호출할 때는 이름 붙인 인자 사용할 수 없다 
- 이름붙인 인자는 디폴트 파라미터랑 함께 쓸 때 의미가 있다 

## 3.2.2 디폴트 파라미터 값 
- 자바에서 오버로딩하는 생성자 생각해봐. 인자 일부 생략 버전 마다 다 만들어야 하잖아 
- 코틀린에서는 파라미터의 디폴트 값을 지정할 수 있으니까 이런 오버로드 중 상당 수를 피할 수 있다 
- 디폴트 값을 이용해 joinToString 개선 
```kotlin
 var list = listOf(1, 2, 3)
    println( MakeEasyToCallFun3_2().joinToString(collection = list, separator = ":", prefix = "(", postfix = ")"))
    println(MakeEasyToCallFun3_2().joinToString(list, ",", "", ""))
    println(MakeEasyToCallFun3_2().joinToString(list))
    println(MakeEasyToCallFun3_2().joinToString(list, ":"))
```
- 함수의 디폴트 파라미터 값은 함수 호출 쪽이 아니라 선언 쪽에서 지정되는 사실을 기억해라 
- 자바에서는 디폴트 파라미터 없으니까 코틀린 함수를 자바에서 호출할 때 그 코틀린 함수가 디폴트 파라미터를 제공한다 해도 모든 인자를 명시해야 한다
- @JvmOverloads 애노테이션 추가시 코틀린 컴파일러가 자동으로 맨 마지막 파라미터로부터 파라미터를 하나씩 생략한 오버로딩한 자바 메서드 추가해준다.

## 3.2.3 정적인 유틸리티 클래스 없애기: 최상위 함수와 프로퍼티 
- 자바에서는 함수가 클래스 안에 있어야 하는데 실전에서는 특정 클래스에 포함시키기 애매한 함수가 많다. 
  - 한 메서드의 대해 두 클래스가 중요한 역할을 할 때 어떤 클래스에 넣을지 고민 
  - 해당 연산을 해당 클래스에 추가할시 api가 너무 커져 다른 곳에 두고 싶을 때 등 
- 그 결과 다양한 정적 메서드를 모아두는 역할만 담당하며, 특별한 상태나 인스턴스 메서드는 없는 클래스가 생겨난다 
  - JDK의 Collections 클래스가 전형적인 예다 
  - 우리가 작성한 클래스에서는 Utill 클래스 
- joinToString() 함수를 최상위 함수로 선언하기 
```kotlin
pacakage strings
fun joinToString(...): String(...)
```
- 이 함수가 실행 될 수 있는 이유 : JVM이 클래스 안에 들어 있는 코드만을 실행할 수 있기 떄문에 컴파일러는 이 파일을 컴파일 할 때 새로운 클래스를 정의해준다. 
- 코틀린 최상위 함수가 포함되는 클래스의 이름을 바꾸고 싶다면 파일에 @JvmName 애노테이션을 추가해라
  - @JvmName 애노테이션은 파일의 맨 앞, 패키지 이름 선언 이전에 위치해야 한다 
```kotlin
@file:JvmName("StringFuctions")
package ch03
```
### 최상위 프로퍼티 
- 프로퍼티도 최상위 수준에 놓을 수 있다. 
```kotlin
package ch03

var opCount = 0

fun performOperation() {
    opCount++
}
```
- 이런 프로퍼티의 값은 정적필드에 저장된다 
- 최상위 프로퍼티를 활용해 코드에 상수를 추가할 수 있다 
```kotlin
val UNIX_LINE_SEPARATOR = "\n"
```
- 기본적으로 최상위 프로퍼티도 다른 모든 프로퍼티처럼 접근자 메서드를 통해 자바 코드에 노출된다 
  - val 의 경우 게터, var의 경우 게터, 세터가 생긴다 
- 겉으로는 상수처럼 보이는데 실제로는 게터를 사용해야 한다면 자연스럽지 못하다 더 자연스럽게 사용하려면 이 상수를 public static final필드로 컴파일 해야 한다. 
- const 변경자를 추가하면 프로퍼티를 public static final 필드로 컴파일하게 만들 수 있다 
- 단 원시 타입과 String 타입의 프로퍼티만 const로 지정할 수 있다 
```kotlin
const val UNIX_LINE_SEPARATOR = "\n"
```
- 이 코드는 다음과 같은 자바 코드와 동등한 바이트 코드를 만들어낸다
```java
public static final String UNIX_LINE_SEPERATOR = "\n";
```

# 3.3. 메서드를 다른 클래스에 추가: 확장 함수와 확장 프로퍼티
- 확장함수는 어떤 클래스의 멤버 메서드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수다
```kotlin
pacakge strings
fun String.lastChar(): Char = this.get(this.length-1)
```
- 확장함수를 만들려면 추가하려는 함수 이름 앞에 그 함수가 확장할 클래스의 이름을 덧붙이기만 하면 된다 
- 클래스의 이름을 수신 객체 타입 receiver type 이라 부르며 
- 확장함수가 호출되는 대상이 되는 값(객체)을 수신 객체 receiver object라고 부른다 
- 이 함수를 호출하는 구문은 다른 일반 클래스 멤버를 호출하는 구문과 똑같다
```kotlin
println("Kotlin".lastChar())
```
- 이 예제에서는 String이 수신 객체 타입이고 "Kotlin" 이 수신 객체다 
- 어떤 면에서 이는 String 클래스에 새로운 메서드를 추가하는 것과 같다 
- String클래스가 여러분이 직접 작성한 코드가 아니고 심지어 String소스코드 소유한 거솓 아니지만 여전이 원하는 메서드를 String에 추가할 수 있다 
- 심지어 String이 자바나 코트릴린 그루비와 같은 다른 JVM언어로 작성된 어떤 클래스도 확장 가능 
- 확장함수 본문에도 일반 함수와 마찬가지로 this를 쓸 수 있다. 그리고 생략할 수도 있다. 일반함수와 마찬가지. 
```kotlin
pacakge strings
fun String.lastChar(): Char = get(length-1)
```
- 확장함수 내부에서는 일반적인 인스턴스 메서드 내부에서와 마찬가지로 수신객체의 메서드나 프로퍼티를 사용할 수 있다 
- 하지만 확장함수가 캡슐화를 깨지는 않는다 
  - 클래스 안에서 정의한 메서드와 달리 확장함수 안에서는 클래스 내부에서만 사용할 수 있는 비공개 private 멤버나 보호된 protected 멤버를 사용할 수 있다 
- 이제 클래스의 멤버메서드와 확장함수를 모두 메서드라고 부를게 
  - 확장 함수 내부에는 수신 객체의 모든 메서드를 호출할 수 있다고 말하면 
    - 확장 함수 내부에서 수신 객체의 멤버 메서드와 확장함수 메서드를 모두 호출할 수 있다는 뜻 

## 3.3.1 임포트와 확장함수 
- 확장함수 정의했다고 해도 프로젝트의 모든 소스코드에서 그 함수를 사용할 수 있지는 않다. 
- 임포트가 필요하다 
```kotlin
import strings.lastChar

val c = "kotlin".lastChar()
```
- * 을 사용한 임포트도 ok
```kotlin
import strings.*

val c = "kotlin".lastChar()
```
- 확장함수 이름이 같은 것이 둘 이상 있다면 임포트해서 쓸 때 충돌
  - as 키워드 쓰면 다른 이름으로 부를 수 있다
```kotlin
import strings.lastChar as last

val c = "kotlin".last()
```
- 일반적인 클래스나 함수라면 그 전체 이름 FQN 을 써도 된다. 하지만 코틀린 문법상 확장함수는 짧은 이름을 써야 해서 임포트할 때 이름을 바꾸는 것이 확장함수 이름 충돌을 해결할 수 있는 유일한 방법

## 3.3.2 자바에서 확장 함수 호출 
- 내부적으로 확장함수는 수신 객체를 첫번째 인자로 받는 정적 메서드 
  - 그래서 함수를 호출해도 다른 어댑터 adater 객체나 실행 시점 부가 비용이 들지 않는다 (물고기)
  - 단지 정적 메서드를 호출하면서 첫번째 인자로 수신객체를 넘기기만 하면 자바에서 확장함수를 사용하기도 편하다 
```java
char c = StringUtilKt.lastChar("Java");
```

## 3.3.3 확장함수로 유틸리티 함수 정의
```kotlin
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
```
```kotlin
    val list = listOf(1, 2, 3)
    println(list.joinToString(separator = ":",
    prefix = "(",
    postfix = ")"))
```
- 이제 joinToString을 마치 클래스의 멤버인 것처럼 호출할 수 있다 
```kotlin

    val list = arrayListOf(1, 2, 3)
    println(list.joinToString(" ")) // 1 2 3
```
- 확장 함수는 정적 메서드 호출에 대한 문법적 편의일 뿐 
- 그래서 클래스가 아닌 더 구체적인 타입을 수신 객체 타입으로 지정 가능 
## 3.3.4 확장 함수는 오버라이드 할 수 없다 
코틀린의 메서드 오버라이드도 일반 객체지향의 오버라이드와 마찬가지다 
```kotlin
open class View {
    open fun click() = println("view clicked")
}

class Button : View() {
  override fun click() = println("button clicked")
}

//   val view : View = Button()
// view.click() // button clicked
```
- 확장함수는 오버라이드할 수 없다. 
- 확장함수는 클래스 밖에 선언된다 
- 확장함수를 호출할 때 수신객체로 지정한 변수의 정적 타입에 의해 결정되지 그 변수에 저장된 객체의 동적인 타입에 의해 확장함수가 결정되진 않는다 
```kotlin
fun View.showOff() = println("im a view")
fun Button.showOff() = println("im a button")

//      val view : View = Button()
//      view.showOff() // im a view // 확장 함수는 정적으로 결정된다 
```
- 확장함수를 첫번째 인자가 수신객체인 정적 자바 메서드로 컴파일 한다는 것을 기억 
```java
View view = new Button();
ExtensionKt.showOff(view);
```
- 어떤 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같다면 확장 함수가 아니라 멤버 함수가 호출된다. 
- 멤버 함수의 우선순위가 더 높다 
- 클래스의 api 를 변경할 경우 이를 항상 염두에 둬야 한다 
- 여러분이 코드 소유권을 가진 클래스에 대한 확장함수를 정의해서 사용하는 외부 클라이언트 프로젝트가 있다고 했을 때 
- 확장함수와 이름과 시그니처가 같은 함수를 클래스 내부에 추가하면 클라이언트는 프로젝트를 재컴파일 한 후 부터는 확장함수 말고 새로 추가된 함수를 사용하게 된다 
## 3.3.5 확장 프로퍼티
- 확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의 구문으로 사용할 수 있는 API를 추가할 수 있다
- 프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할 적절한 방법이 없기 때문에 (긴존 클래스으의 인스턴스 객체에 필드를 추가할 방법은 없다)
- 실제로 확장 프로퍼티는 아무런 상태를 가질 수 없다 
- 하지만 프로퍼티 문법으로 더 짧게 코드를 작성할 수 있어서 편한 경우가 있다 
- 리스트 3.7 확장 프로퍼티 선언하기  
```kotlin
val String.lastchar: Char
get() = get(length-1)

```
- 확장 함수와 마찬가지로 확장 프로퍼티도 일반적인 프로퍼티와 같은데 
- 단지 수신 객체 클래스가 추가됐을 뿐이다
- String.lastchar 에서는 뒷받침 하는 필드가 없어서 기본 게터 구현을 제공할 수 없으므로 최소한 게터는 꼭 정의를 해야 한다 (p73 참고)
- 마찬가지로 초기화 코드에서 계산한 값을 담을 장소가 전혀 없으므로 초기화 코드도 쓸 수 없다 
- 리스트 3.8 변경 가능한 확장 프로퍼티 선언하기 
```kotlin
fun main(args: Array<String>) {
    println("kotlin".lastchar) // n
    val sb = StringBuilder("kotlin?")
    sb.lastchar = '!'
    println(sb) // kotlin!
}

var StringBuilder.lastchar: Char
    get() = get(length-1)
    set(value: Char) {
        this.setCharAt(length-1, value) 
    }
```


# 3.4 컬렉션 처리: 가변 길이 인자, 중위 함수 호출, 라이브러리 지원 

## 3.4.1 자바 컬렉션 API 확장 
- 코틀린이 어떻게 자바 라이브러리 클래스의 인스턴스 클래스에 대해 새로운 기능을 추가했는지 궁금했을 것. 이제 우린 답을 안다. 그건 확장함수 

## 3.4.2 가변 인자 함수 : 인자의 개수가 달라질 수 있는 함수 정의
- 리스트를 생성하는 함수를 호출할 때 원하는 만큼 많이 원소를 전달할 수 있다 
```kotlin
val list = listOf(2, 3, 5, 7, 11)
```
- 라이브러리에서 이 함수의 정의를 보면 다음과 같다 
```kotlin
fun listOf<T>(vararg values: T) : List<T> { ... }
```
- 가변길이 인자 vararg는 호출할 때 원하는 개수만큼 값을 인자로 넘기면 자바 컴파일러가 배열에 그 값들을 넣어주는 기능
- 코틀린에서는 타입 뒤에 ... 를 붙이는 대신 파라미터 앞에 vararg 변경자를 붙인자 
- 이미 배열에 들어있는 원소를 가변 길이 인자로 넘길 때 자바랑 구문이 다르다 
  - 자바에서는 배열을 그냥 넘기 되지만 
  - 코틀린에서는 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되게 해야 한다 
  - 기술적으로는 스프레드 spread 연산자가 그런 작업을 해주는데, 실제로는 배열 앞에 * 를 붙이기만 하면 된다 
```kotlin
fun main(args: Array<String>) {
    val list = listOf("args: ", *args)
    println(list)
}

```

## 3.4.3 값으 쌍 다루기: 중위 호출과 구조 분해 선언
- 맵을 만들려면 mapOf 함수를 사용한다 
```kotlin
val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```
- to 는 코틀린 키워드가 아니다. 중위 호출 infix call 이라는 특별한 방식으로 to 라는 일반 메서드를 호출한 것
- 중위 호출 시에는 수신 객체와 유일한 메서드 인자 사이에 메서드 이름을 넣는다 
- 객체, 메서드 이름, 유일한 인자 사이에는 공백이 들어가야 한다 
- 다음 두 호출은 동일하다 
```kotlin
1.to("one")
1 to "one"
```
- 인자가 하나뿐인 일반 메서드나 인자가 하나뿐인 확장함수에 중위호출을 사용할 수 있다 
- 함수(메서드)를 중위 호출에 사용하게 허용하고 싶으면 infix 변경자를 함수(메서드) 선언 앞에 추가해야 한다. 
- 다음은 to 함수의 정의를 간략하게 줄인 코드 
```kotlin
infix fun Any.to(other: Any) = Pair(this, other)
```
- 이 to 함수는 Pair의 인스턴스를 반환한다 
- Pair는 코틀린 표준 라이브러리 클래스로 그 이름대로 두 원소로 이뤄진 순서쌍을 표현한다 
- 실제로는 to는 제네릭이지만 설명을 위해 세부사항을 생략
- Pair의 내용으로 두 변수를 즉시 초기화할 수 있다 
```kotlin
val (number, name) = 1 to "one"
```
- 이런 기능을 구조 분해 선언 destructuring declaration 이라고 부른다. 
- Pair 인스턴스 외 다른 객체에도 구조 분해를 적용할 수 있다. 

# 3.5 문자열과 정규식 다루기 
- 코틀린 문자열은 자바 문자열과 같다 

## 3.5.1 문자열 나누기

## 3.5.2 정규식과 3중 따옴표로 묶은 문자열 
-  substringBeforeLast(), substringAfterLast("/") 등의 메소드를 활용하면 문자열 쉽게 파시앟ㄹ 수 있다. 
- 3중 따옴표 문자열에서는 역슬래시를 포함한 어떤 문자도 이스케이프 할 필요가 없다. 
  - 일반 문자열을 사용해 정규식을 작성하는 경우 마침표 기호를 이스케이프하려면 \\. 
  - 3중 따옴표 문자열에서는 \. 라고 쓰면 된다 
- 패턴 . 는 임의의 문자와 매치될 수 있다 
- 따로 지정하지 않으면 정규식 엔진은 각 패턴을 가능한 가장 긴 부분 문자열과 매치하려고 시도한다 
  - "path/to/dir/filename.kt" 에서 (.+)/ 와 일치하틑 패턴을 찾으면 "path"가 아니라 "path/to/dir" 라는 부분 문자열이 매치
  - 이와 관련해서는 탐욕, 게으름 greedy, lazy 찾아보면 된다 
```kotlin
fun parsePath(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}
```
## 3.5.3 여러 줄 3중 따옴표 문자열 
- 3중 따옴표 문자열을 문자열 이스케이프를 피하기 위해서만 사용하지는 않는다
- 3중 따옴표 문자열을 쓰면 줄바꿈이 있는 프로그램 텍스트를 쉽게 문자열로 만들 수 있다 
```kotlin
    val kotlinLogo = """|  //
                       .| //
                       .|/\"""
    println(kotlinLogo.trimMargin("."))
```
- 더 보기 좋게 표현하기 위해 들여쓰기를 하되 들여쓰기의 끝부분을 특별한 문자열로 표시하고, trimMargin 사용해서 그 문자열과 그 직전의 공백을 제건한다.
- 이 예제에서는 마침표를 들여쓰기 구분 문자열로 사용했다. 

# 3.6 코드 다듬기 : 로컬 함수와 확장 
- 리팩토링 하려고 메소드 분리 많이 하는데 그러면 메소드 관계를 파악하기 어렵다 
- 코틀린에서는 함수에서 추출한 함수를 원 함수 내부에 중첩시킬 수 있다 
- 그렇게 하면 문법적인 부가 비용을 들이지 않고 깔끔하게 코드 조작 가능
- 코드 중복을 로컬 local 함수를 통해 어떻게 제거할 수 있는지 살펴보자 
- 로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있다 

```kotlin
package ch03

class User (val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                "Can't save user $id: empty $fieldName"
            )
        }
    }
    
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser (user: User) {
    user.validateBeforeSave()
    
    //user를 데이터베이스에 저장한다.
}

```
- 검증 로직은 User를 사용하는 다른 곳에는 쓰이지 않는 기능이기 때문에 User 에 포함시키고 싶지는 않다 
- User를 간결하게 유지해야 쉽게 코드 파악 가능
- 확장함수를 로컬 함수로 정의할 수도 있다. 
  - User.validateBeforeSave를 saveUser 내부에 로컬함수로 넣을 수도 있다 
  - 하지만 중첩된 함수의 깊이가 깊어질수록 코드 읽기가 어려워진다 
  - 일반적으로는 한 단계만 함수를 중첩시키라고 권정한다 




