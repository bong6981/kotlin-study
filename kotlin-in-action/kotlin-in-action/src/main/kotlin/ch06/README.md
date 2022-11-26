# 6.1.1 널이 될 수 있는 타입 
- 코틀린과 자바의 첫 번째이자 가장 중요한 차이는 코틀린 타입 시스템이 널이 될 수 있는 타입을 명시적으로 지원한다는 점이다 
- 널이 될 수 있는 타입은 프로그램 안의 프로퍼티나 변수에  null 을 허용하게 만드는 방법이다 
- 어떤 변수가 널이 될 수 있다면 그 변수에 대해 ( 그 변수를 수신 객체로 ) 메서드를 호출하면 NullPointerException 이 발생할 수 있으므로 안전하지 않다 
- 코틀린은 그런 메서드 호출을 금지함으로써 많은 오류를 방지한다 
```java
int strLen(String s) {
    return s.length
}
```
- 이 함수가 안전한가? 이 함수에 null을 넘기면 NullpointerException이 발생한다 
  - 그렇다면 이 함수에서 s가 null인지를 꼭 검사해야 할까?
  - 검사가 필요할지 여부는 이 함수를 사용하는 의도에 따라 달라진다 
- 이 함수를 코틀린으로 다시 작성해보자
  - 코틀린에서 이런 함수를 작성할 때 가장 먼저 답을 알아야 할 질문은 이 함수가 널을 인자로 받을 수 있는가다 
  - 여기서 널을 인자로 받을 수 있다는 말은
    - strLen(null) 처럼 직접 null 리터럴로 사용하는 경우 뿐 아니라 
    - 변나 식의 값이 실행 시점에 null이 될 수 있는 경우를 모두 포함한다 
- 널이 자로 들어올 수 없다면 코틀린에서는 다음과 같이 함수를 정의할 수 있다 
```kotlin
fun strLen(s: String) = s.length
```
- strLen 에 null 이나 null 이 될 수 있는 값을 넘기는 것은 금지되면 혹시 그런 값을 넘기면 컴파일 시 오류가 발행한다 
- 따라서 strLen 함수가 결코 실행 시점에 nullpointerexception 을 발생시키지 않으리라 장담할 수 있다 
- 이 함수가 널과 문자열을 인자로 받을 수 있게 하려면 타입 이름 뒤에 물음표를 명시해야 한다 
```kotlin
fun strLen(s: String?) = s.length
```
- 어떤 타입이든 이름 뒤에 물음표를 붙이면 그 타입의 변수나 프로퍼티에 null 참조를 저장할 수 있다는 뜻이다 
- 다시 말하지만 물음표가 없는 타입은 그 변수가 null 참조를 저장할 수 없다는 뜻이다 
  - 따라서 모든 타입은 기본적으로 널이 될 수 없는 타입 
  - 뒤에 ? 가 붙어야 널이 될 수 있다 
- 널이 될 수 있는 타입의 변수가 있다면 그에 대해 수행할 수 있는 연산이 제한된다 
- 예를 들어 널이 될 수 있는 타입인 변수에 대해 변수.메서드() 처럼 메서드를 직접 호출할 수 없다 
```kotlin
fun strLen(s: String?) = s.length // 이거 에러. (?.) 또는 (!!) 를 써야 한다
```
- 널이 될 수 있있는 값을 널이 될 수 없는 타입의 변수에 대입할 수 는 없다. 
```kotlin
val x :String? = null
var y? String = x // 에러
```
- 널이 될 수 있는 타입의 값을 널이 될 수 없는 타입의 파라미터를 받는 함수에 전달 할 수 없다 
- 이렇게 제약이 많다면 널이 될 수 있는 타입의 값으로 대체 뭘 할 수 있을까?
- 가장 중요한 일은 바로 null 과 비교하는 것 
- 일단 null 과 비교하고 나면 컴파일러는 그 사실을 기억하고 null 이 아님이 확실한 영역에서는 해당 값을 널이 될 수 없는 타입의 값처럼 사용할 수 있다 
```kotlin
fun strLenSafe(s: String?): Int = 
    if (s != null) s.length else 0 // 널 검사를 추가하면 코드가 컴파일 된다 

val x : String? = null
println(strLenSafe((x)))
println(strLenSafe("abc"))
```
- 널 가능성을 다루기 위해 사용할 수 있 도구가 if 검사뿐이라면 코드가 번잡
- 다행히 코틀린은 널이 될 수 있는 값을 다룰 때 도움이 되는 여러 도구를 제공한다

## 6.1.2 타입의 의미
- 타입이란 무엇이고 왜 변수에 타입을 지정해야 하나? 
- 타입은 어떤 값들이 가능한지와 그 타입에 대해 수행할 수 있는 연산의 종류를 결정한다 
- double : 64비트 부동소수점 
  - double 타입의 값에 일반 수학 연산을 수행할 수 있다 
- double과 String 변수를 비교해보자 
  - 자바에서 String 타입의 변수에는 String, null 두가지 종류의 값이 들어 갈 수 있다 
  - 이 두 가지 값으 서로 완전히 다르다 
  - 자바의 isntanceof 연산자도 null이 string이 아니라고 답한다 
  - 두 종류의 값에 대해 실행할 수 있는 연산도 완전히 다르다 
- 이는 자바의 타입시스템이 널을 제대로 다루지 못한다는 뜻 
- 변수에 선언된 타입이 있지만 널 여부를 추가로 검사하기 전에는 그 변수에 대해 어떤 연산을 수행할 수 있을지 알 수 없다 
- 프로그램을 작성하면서 프로그램의 데이터 흐름 속에서 특정 위체에 특정 변수가 절대로 널일 수 없다는 사실을 확신하고 이런 검사를 생략하는 겨웅가 많다 
- 그런데 그 판단이 틀리면 실행 시점에 프로그램이 null pointer exception 예외를 발생시미켜 오류로 중단된다 
- null pointer exception 오류를 다루는 다른 방법 
  - 자바에도 null pointer exception 을 해결하는데 도구가 있다 
  - @Nullable @NotNull 
  - 이런 애노테이션 활용해 NullPointerException 발생할 수 있는 위치를 찾아주는 도구가 있다 
  - 하지만 자바 컴파일 절차 일부가 아니라 일관성 있게 적용한다는 보장 없고
  - 오류가 발생할 위치를 정확하게 찾기 위해 라이브러리를 포함하는 모든 코드베이스 애노테이션을 추가하는 일도 쉽지 않다 
  - 이 문제를 해결하든 다른 방법은 null 값을 코드에서 절대로 쓰지 안흔ㄴ 것이다 
    - null 대신 자바8에 새로 도입된 optional 타입당의 null을 감싸는 특별한 래퍼 타입을 활용할 수 있다 
    - optional 은 어떤 값이 정의되거나 정의되지 않을 수 있음을 표현하는 타입니다 
    - 이런 해법에는 몇 가지 단점 
      - 코드가 지저분해지고 
      - 래퍼가 추가됨에 따라 실행 시점에 성능이 저하 
      - 전체 에코시스템에서 일관되게 활용하기 어렵다 
        - 여러분이 작성한 코드에서는 Optional 을 사용하더라도 여전이 jdk 메서드나 안드로이드 프레임워크, 다른 서드파티 라이브러리 등에서 반환되는 null을 처리해야 한다 
- 코틀린의 널이 될 수 있는 타입은 이런문제에 종합적인 해법 제공 
- 널이 될 수 있는 타입과 널이 될 수 없는 타입을 구분하면 
  - 각 타입에 대해 어떤 연산이 가능할지 미리 정확이 이해 가능 
  - 실행 시점에 예외를 발생시키리 수 있는 연산을 판단할 수 있다 
  - 따라서 그런 연산을 아예 금지시킬 수 있다 
- 실행시점에 널이 될 수 있는 타입이나 널이 될 수 없는 타입의 객체는 같다 
  - 널이 될 수 있는 타입은 널이 될 수 없는 타입을 감싼 래퍼 타입은 아닌다 
  - 모든 검사는 컴파일 시점에 수행된다 
  - 따라서 코틀린에서는 널이 될 수 있는 타입을 처리하는 데 별도의 실행 시점 부가 비용이 들지 않는다 
- 이제 코틀린에서 널이 될 수 있는 타입을 어떻게 다루는지와 널이 될 수 있는 타입을 다루더라도 전혀 불편하지 않는 이유를 살펴보자 

## 6.1.3 안전한 호출 연산자: ?.
- ?. 은 null 검사와 메서드 호출을 한 번의 연산으로 수행 
- s?.toUpperCase() 는 if (s != null) s.toUpperCase() else null 과 같다 
- 호출하려는 값이 null 이 아니라면 ?. 는 일반 메서드 호출처럼 작동한다. 
  - 호출하려는 값이 null이면 이 호출은 무시되고 null이 결과 값이 된다 
- 안전한 호출의 결과 타입도 널이 될 수 있는 타입이라는 점에 유의 
  - String.toUpperCase 는 String 타입의 값을 반환 
  - s 가 널이 될 수 있는 타입인 경우 s?.toUpperCase() 식의 결과 타입은 String? 이다 
- 프로퍼티 읽거나 쓸 때도 안전한 호출 사용 가능 
- 리스트 6.2 널이 될 수 있는 프로퍼티 다루기 위해 안전한 호출 사용하기 
```kotlin
class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

val ceo = Employee("Bob, null") // println(managerName(ceo)) // null
```
- 객체 그래프에서 널이 될 수 있는 중간 객체가 여럿 있다면 안전한 호출을 연쇄해서 사용하면 펼할 때가 자주 있다 
```kotlin
class Address(val streetAddress: String, val zipCode: Int,
val city: String, val country: String) 

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
    return company?.address?.country ?: "Unknown"
}
```

