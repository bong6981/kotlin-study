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

## 6.1.4 엘비스 연산자 ?:
- 코틀린은 null 대신 사용할 디폴트 값을 지정할 때 편리하게 사용할 수 있는 연산자 제공 
- 엘비스 연산자 elvis 
```kotlin
fun foo(s: String?) {
    val t: String = s ?:"" // s가 null이면 ""
}
```
- 좌항 값이 널이 아니면 좌항 값을 결과로 하고 좌항 값이 널이면 우항 값을 결과로 한다 
- 엘비스 연산자를 객체가 널인 경우 널을 반환하는 안전한 호출 연산자와 함께 사용해서 객체가 널인 경우에 대비한 값을 지저장하는 경우도 많다 
- 리스트 6.3엘비스 연산자 이용해 널 값 다루기 (6.1 리스트 줄여쓰기)
```kotlin
fun strLenSafe(s: Stirng?): Int = s?.length ?: 0
```
```kotlin
fun Person.countryName() = company?.address?.country ?: "Unknown"
```
- 코틀린이세너는 return, throw 등의 연산도 식이다 
- 그래서 엘비스 연산자 우항에 return, throw 넣을 수 있고 엘비스 연산자 더 편하게 상용할 수 있다 
- 그런 경우 엘비스 연산자 좌항이 널이면 함수가 즉시 어떤 값 반환 혹은 예외를 던진다 
- 이런 패턴은 함수의 전제 조건을 검사하는 경우 특히 예외 
- 리스트 6.5 throw 와 엘비스 연산자 함께 사용하기 
```kotlin

class Address(val streetAddress: String, val zipCode: Int,
val city: String, val country: String)

class Company(val name: String, val address: Address?)

class Person(val name: String, val company: Company?)


fun printShippingLabel(person: Person) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address") // 주소 없으면 예외
    with (address) {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}


```

## 6.1.5 안전한 캐스트: as? 
- as를 사용할 때마다 is 를 통해 미리 as 로 변경 가능한 타입인지 검사해볼 수도 잇다 
- 더 좋은 해법은 
  - as? 어떤 값을 지정한 타입으로 캐스트 
  - 값을 대상으로 타입으로 변환할 수 없으면 null 반환 
- 리스트 6.6 안전한 연산자 사용해 equals 구현
```kotlin
package ch06

class Person1 (val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person1 ?: return  false // 타입 불일치 false 반환

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName // 안전한 캐스트 후 Person1 로 스마트 캐스트
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}

fun main(args: Array<String>) {
  val p1 = Person1("Dmitry", "Jemerov")
  val p2 = Person1("Dmitry", "Jemerov")
  println(p1 == p2) // == 연산자는 equals 메서드 호출
  println(p1.equals(42))
}
```

- 이 패턴을 이용하면 파라미터로 받은 값이 원하는 타입인지 쉽게 검사, 캐스트, 타입이 맞지 않으면 false 반환 
- 이 모든 동작을 한 식으로 해결 가능 
- 컴파일러에게 어떤 값이 널이 아니라는 사실을 알려주고 싶을 때 (계속)

## 6.1.6 널이 아님 단언 : !! 
- !! 어떤 값이든 널이 될 수 없는 타입으로 강제로 바꿀 수 있다 
```kotlin
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!! // 예외가 터지면 이 지점을 가리킨다 
    println(sNotNull.length)
}

//     ignoreNulls(null)

```
- !! 는 컴파일러가에게 난 null 이 아니라고 확신하니 예외가 발생해도 상관없음을 감언 
  - !! 못생김 : 코틀린 설계자들은 다른 방법을 찾아봐! 하는 것 
- !! 가 더 나은 해법인 경우가 있다 
  - 어떤 함수가 값이 널인지 검사한 다음에 다른 함수 호출해도 
  - 컴파일러는 호출된 함수가 언제나 다른 함수에서 널이 아닌 값을 전달받는 사실을 인식할 수 없을 때 
- 실무에서는 이에 해당하는 예제가 스윙과 같은 다양한 ui 프레임워크에 있는 액션 클래스에서 이런 일이 발생한다 
```kotlin
class CopyRowAction(val list: JList<String>): AbstractAction() {
    override fun isEnabled(): Boolean =
        list.selectedValue != null

    override fun actionPerformed(e: ActionEvent?) {
        val value = list.selectedValue!! // actionPerformed는 isEnabled가 "true"인 경우에만 호출 
    }
}
```
- 이 경우 !! 를 사용하지 않으려면 val value = list.selectedValue ?: return 처럼 널이 될 수 없는 타입의 값을 얻어야 한다 
  - 이런 패턴을 사용하면 list.selectedValue 가 null 이면 함수가 조기 종료되므로 함수의 나머지 본문에서는 value가 항상 널이 아니게 된다 
- !! 를 널에 대해 사용해서 발생하는 예외의 스택 트레이스 strace 에는 어떤 파일의 몇 번 째 줄인지에 대한 정보는 들어있지만 
  - 어떤 식에서 예외가 발생했는지에 대한 정보는 들어있지 않다 
  - 어떤 값이 널이었는지 확실히 하기 위해 !! 단언문을 한 줄에 함께 쓰는 일을 피하라 
  - person.company!!.address!.country // 이런 식으로 코드 작성하지 말라 
- 널이 될 수 있는 값을 널이 아닌 값만 인자로 받는 함수에 넘기려면 어떻게 해야 할까? let (계속)

## 6.1.7 let 함수 

- let 함수를 안전한 호출 연산자와 함께 사용하면 원하는 식을 평가해서 결과가 널인지 검사한 다음에 그 결과를 변수에 넣는 작업을 간단한 식을 사용해 한 꺼번에 처리할 수 있다
- let 을 사용하는 가장 흔한 용례는 널이 될 수 있는 값을 널이 아닌 값만 인자로 받는 함수에 넘기는 경우다. 
```kotlin
fun sendEmailTo(email: String){}

val email: String? = ""
//    sendToEmail(email) // Type mismatch: inferred type is String? but String was expected
```
- 이 함수에게 널이 될 수 있는 타입의 값을 넘길 수는 없다 
```kotlin
if (email != null) sendToEmail(email)
```
- 하지만 let 함수를 통해 인자를 전달 할 수도 있다 
  - let 함수는 자신의 수신 객체를 인자로 전달 받은 람다에게 넘긴다 
  - 널이 될 수 있는 값에 대해 안전한 호출 구문을 사용해 let 을 호출하되 널이 될 수 없는 타입을 인자로 받는 람다를 let에 전달한다 
  - 이렇게 하면 널이 될 수 있는 타입의 값을 널이 될 수 없는 타입의 값으로 바꿔서 람다에게 전달하게 된다 
```kotlin
    email?.let { sendToEmail(email) }
```
- let 함수는 ?. 덕분에 이메일 주소 값이 널이 아닐 때만 호출된다. 
- it 을 사용하여 더 간결하게 
```kotlin
    email?.let { sendToEmail(it) }
```
- 다음이 이 패턴을 보여주는 더 복잡한 예 
- 리스트 6.9 let 을 사용해 null 이 아닌 인자로 함수 호출하기 
```kotlin
fun sendToEmail(email: String) {
    println("Sending email to $email")
}
//
//var email: String? = "yole@example.com"
//email?.let { sendToEmail(it) } // Sending email to yole@example.com
//
//email = null
//email?.let { sendToEmail(it) } //
```
- 아주 긴식이 있고 그 값이 널이 아닐 때 수행해야 하는 로직이 있을 때 let을 쓰면 훨씬 더 편하다 
- let을 쓰면 긴 식의 결과를 저장하는 변수를 따로 만들 필요가 없다 
- 다음 명시인 if 검사가 있다고 치자 
```kotlin
    val person: Person3? = getTheBestPersonInTheWorld()
    if (person != null) sendToEmail(person.email)
```
- 굳이 명시적인 변수 person 을 추가할 필요 없디 다음과 같이 쓸 수 있다 
```kotlin
    getTheBestPersonInTheWorld()?.let { sendToEmail(it.email) }
```
- 만약 getTheBestPersonInTheWorld() 가 null 만을 반환한다면 결코 실행되지 않는다 
- 여러 값이 널인지 검사해야 한다면 let 호출을 중첩시켜서 처리할 수 있다 
  - 그렇게 let을 중첩시켜 사용하면 코드가 복잡해져 알아보기 어렵다 
  - 그런 경우 일반적인 if 를 사용해 모든 값을 한 번에 검사하는 편이 낫다 
- 자주 발생하는 다른상황 : 실제로는 널이 될 수 없는 프로퍼티 인데 생성자 안에 널이 아닌 값으로 초기화할 방법이 없는 경우 
  - 이런 상황을 코틀린에서는 어떻게 처리할지 (계속)

# 6.1.8 나중에 초기화할 프로퍼티 
- 객체 인스턴스를 일단 생성한 다음에 나중에 초기화하는 프레임워크가 많다 
  - 예를 들어 안드로이드에서는 onCreate 에서 액티비티를 초기화한다 
  - 제이유닛에서는 @Before 로 애노테이션된 메서드 안에서 초기화 로직을 수행해야만 한다 
- 하지만 코틀린에서는 클래스 안의 널이 될 수 없는 새ㅇ성자 안에서 초기화하지 않고 특별한 메서드 안에서 초기화할 수는 없다 
- 코틀린은 일반적으로 생성자에서 모든 프로퍼티를 초기화해야 한다 
- 게다가 프로퍼티 타입이 널이 될 수 없는 타입이라면 반드시 널이 아닌 값으로 그 프로퍼티를 초기화해야 한다 
- 그런 초기화 값을 제공할 수 없으면 널이 될 수 있는 타입을 사용할 수밖에 없다. 
- 하지만 널이 될 수 있는 타입을 사용하면 모든 프로퍼티 접근에 널 검사를 넣거나 !! 연산자를 써야 한다 
- 리스트 6.10 널 아님 단언을 사용해 널이 될 수 있는 프로퍼티 접근하기 
```kotlin
package ch05

class MyService {
    fun performAction(): String = "foo"
}

class MyTest {
    private var myService: MyService? = null // null 로 초기화하기 위해 널이 될 수 있는 타입으로 선언

    @Before fun setUp() {
        myService = MyService() // setUp 에서 진짜 초깃값을 지정 
    }
    
    @Test fun testAction() {
        Assert.assertEquals("foo", 
        myService!!.performAction()) // 반드시 널 가능성에 신경써야 한다. !!나 ? 를 꼭 써야 한다 
    }
}

```
- 이 코드는 보기 나쁘다. 특히 프로퍼티를 여러 번 사용해야 하면 코드가 더 못생겨진다. 
- 이를 해결하기 위해 myService 프로퍼티를 나중에 초기화 할 수 있다 
- lateinit 변경자를 붙이면 프로퍼티를 나중에 초기화 할 수 있다 
```kotlin
package ch05

class MyService {
    fun performAction(): String = "foo"
}

class MyTest {
    private lateinit var myService: MyService // 초기화하지 않고 널이 될 수 없는 프로퍼티 선언

    @Before fun setUp() {
        myService = MyService() // setUp 에서 진짜 초깃값을 지정 
    }
    
    @Test fun testAction() {
        Assert.assertEquals("foo", 
        myService.performAction()) // 널 검사 수행하지 않고 프로퍼티 사용
    }
}

```
- 나중에 초기화하는 프로퍼티는 항상 var이어야 한다 
  - val 프로퍼티는 final 필드로 컴파일 되며 생성자 안에서 반드시 초기화 해야 한다 
- 나중에 초기화하는 프로퍼티는 널이 될 수 없는 타입이라 해도 더 이상 생성자 안에서 초기화할 필요가 없다 
- 그 프로퍼티를 초기화하기 전에 프로퍼티에 접근하면 "lateinit property mySerivce has not initialized" 라는 예외 발생 
- 예외를 보면 어디가 잘못되 있는지 확실히 알 수 있고 nullPointerException보다 훨씬 좋다 
- 노트 
  - lateinit 프로퍼티를 의존관계 주입 프레임워크와 함께 사용하는 경우가 많다 
  - 그런 시나리오에서는 lateinit 프로퍼티 값을 di 프레임 워크가 외부에서 설정해준다 
  - 다양한 자바 프레임워크와의 호환성을 위해 코틀린은 lateinit 가 지정된 프로퍼티와 가시성이 똑같은 필드를 생성해준다 
  - 어떤 프로퍼티가 public이라면 코틀린이 생성한 필드도 public 

```kotlin
TODO() // @postCounstruct 처럼 빈 생성된 후에 어떤 로직을 수행해야 할 때, init 말고 
```
# 6.1.9 널이 될 수 있는 타입 확장 
- 널이 될 수 있는 타입에 대한 확장함수를 정의하면 null값을 다루는 강력한 도구로 활용할 수 있다 
- 어떤 메서드를 호출하기 전에 수신객체 역할을 하는 변수가 널이 될 수 없다고 보장하는 대신 
  - 직접 변수에 대해 메서드를 호출해도 확장함수인 메서드가 알아서 널을 처리해준다 
- 이런 처리는 확장함수에서만 가능하다 
  - 일반 멤버 호출은 객체 인스턴스를 통해 디스패치 dispatch 되므로 그 인스턴스가 널인지 여부를 검사하지 않는다 
    - 객체 지향언어에서 객체의 동적 타입에 따라 적절한 메서드를 호출해주는 방식을 동적 디스패치라 부른다. 
    - 반대로 컴파일러가 컴파일 시점에 어떤 메서드가 호출될지 결정해서 코드를 생성하는 방식을 직접 디스패치라고 한다 
    - 일반적으로 동적 디스패치를 처리할 때는 객체별로 자신의 메서드에 대한 테이블을 저장하는 방법을 가장 많이 사용한다 
    - 물론 대부분의 객체지향 언어에서 같은 클래스에 속한 객체는 같은 메서드 테이블을 공유하므로 보통 메서드 테이블은 클래스마다 하나씩만 만들고 
      - 각 객체는 자신의 클래스에 대한 참조를 통해 그 메서드 테이블을 찾아보는 경우가 많다 
- 예를 들어 코틀린 라이브러리에서 String(널이 될 수 없는 타입)을 확장해 정의된 isEmpty, isBlank라는 함수를 생각해보자 
  - isEmpty 는 문자열이 빈문자열인지("") 검사하고 
  - isBlank 는 문자열이 모든 공백 문자로 이뤄졌는지 검사한다 
  - 이 문자열로 무언가 작업할 경우 보통 이런함수들로 문자열 검사 
- isEmpty, isBlank 처럼 널을 검사하면 편하지 않을까? 
  - 실제로 String? 타입에 대해 호출할 수 있는 isNullorEmpty isNullOrBlank 가 있다 
  - isNullOrBlank 는 널을 명시적으로 검사해서 널인 경우 true를 반환 널이 아니면 isBlank 호출
  - isBlank 는 널이 아닌 문자열 타입의 값에 대해서만 호출 가능 
```kotlin
fun verifyUserInput(input: String?) {
    if (input.isNullOrBlank()) { // 안전한 호출을 하지 않아도 된다
        println("Please fill in the required fields")
    }
}

//verifyUserInput(" ") // Please fill in the required fields
//verifyUserInput(null) // Please fill in the required fields
```
```kotlin
fun String?.isNullOrBlank(): Boolean = // 널이 될 수 있는 String 의 확장 
    this == null || this.isBlank() // 두번째 this 에는 스마트 캐스트 적용
```
- 널이 될 수 있는 타입 (? 로 끄타는 타입)에 대한 확장을 정의하면 널이 될 수 있는 값에 대해 그 함수를 호출할 수 있다 
  - 그 함수의 내부에서 this 는 널이 될 수 있다 
  - 따라서 명시적으로 널 검사를 해야 한다 
  - 자바에서는 메서드 안의 this 는 그 메서드가 호출된 수신 객체를 가리키므로 항상 널이 아니다 
    - 수신객체가 널이면다면 NPE가 발생해서 그 메서드 안으로 들어가지도 못한다 
  - 코틀린에서는 널이 될수 있는 타입의 확장함수 안에서는 this가 널이 될 수 있다는 점이 자바와 다르다 
- 앞에서 살펴본 let 함수도 널이 될 수 있는 타입의 값에 대해 호출할 수 있지만 let은 this가 널인지 아닌지 검사 안한다
  - 널이 될 수 있는 타입에 대한 안전한 호출을 사용하지 않고 let을 호출하면 람다의 인자는 널이 될 수 있는 타입으로 추론된다 
  - 따라서 let을 사용할 때 수신객체가 널인지 아닌지 검사하고 싶으면 앞에서 봤든 안전한 호출 연산자인 ?. 을 사용해야 한다 
- 노트 
  - 여러분이 직접 확장 함수를 작성시 그 확장 함수를 널이 될 수 있는 타입에 대해 정의할지 여부를 고민할 필요가 있다 
  - 처음에는 널이 될 수 없는 타입에 대한 확장함수를 정의하라 
  - 나중에 대부분 널이 될 수 있는 타입에 대해 그 함수를 호출했다는 사실을 깨닫게 되면 
    - 확장 함수 안에서 널을 제대로 처리하게 하면 ( 그 확장 함수를 사용하는 코드가 깨지지 않으므로 )
    - 안전하게 그 확장함수를 널이 될 수 있는 타입에 대한 확장함수로 바꿀 수 있다 

## 6.1.10 타입 파라미터의 널 가능성 
- 코틀린에서는 함수나 클래스의 모든 타입 파라미터는 기본적이로 널이 될 수 있다 
- 널이 될 수 있는 타입을 포함하는 어떤 타입이라도 타입 파라미터를 대신할 수 이싿 
- 따라서 타입 파라미터 T 를 클래스나 함수 안에서 타입 이름으로 사용하면 이름끝에 물음표가 없더라고 T가 널이 될 수 있는 타입니다 
```kotlin
fun <T> printHashCode(t: T) {
    println(t.hashCode()) // 
    println(t?.hashCode()) // t가 널이 될수 있어서 안전한 호출을 써야만 한다
}

//printHashCode(null) // T 타입은 Any? 로 추론된다
```
```kotlin
fun <T> printHashCode(t: T) {
    println(t.hashCode()) // Todo (오류지적) 이것의 결과는 0 
  /***
   * fun Any?.hashCode(): Int
   * Returns a hash code value for the object or zero if the object is null.
   * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/hash-code.html
   */

}
```
- 타입 파라미터가 널이 아님을 확실하게 하려면 널이 될 수 없는 타입의 상한을 지정해야만 한다 
  - 이렇게 널이 될 수 없는 타입 상한을 지정하면 널이 될 수 있는 값을 거부하게 된다 
```kotlin
fun <T: Any> printHashCode(t: T) { // 이제 T는 널이 될 수 없는 타입 
    println(t.hashCode())
}

printHashCode(null) // Null can not be a value of a non-null type TypeVariable(T)

```
- 타입 파라미터는 널이 될 수 있는 타입을 표시하려면 반드시 물음표를 타입 이름 뒤에 붙여야 한다는 규칙의 유일한 예외다 

## 6.1.11 널 가능성과 자바 
- 자바 타입 시트메은 널 가능성을 지원하지 않는다. 그렇다면 자바와 코틀린을 조합하면 어떤 일이 발생?
- 자바 코드에도 애노테이션 표시된 널 가능성의 정보가 있다 
  - 이런 정보가 코드에 있으면 코틀린도 이 정보 활용 
  - 자바의 @Nullable String은 코틀린에서 String?과 같고 자바의 @NotNull String 은 String
- 코틀린은 여러 널 가능성 애노테이션을 알아본다 
- 이런 널 가능성 애토테이션이 소스코드에 없는 경우는 코틀린의 플랫폼 타입 platform type 이 된다

### 플랫폼 타입 
- 플랫폼 타입은 코틀린이 널 관련 정보를 알 수 없는 타입 
- 그 타입을 널이 될 수 있는 타입 / 널 될 수 없는 타입 어떤 타입으로 처리해도 된다 
- 이는 플랫폼 타입에 대해 수행하는 모든 연산에 대한 책임은 자바와 마찬가지로 여러분에게 있다는 뜻 
  - 컴파일러는 모든 연산 허용
    - 코틀린은 보통 널이 될 수 없는 타입의 값에 대해 널 안전성 검사를 수행하는 연산 수행시 경고 표시하지만 
    - 플랫폼 타이브이 값에 대해 널 안전성 검사를 중복 수행해도 아무 겨옥 없다 
- 어떤 플랫폼 타입이 값이 널이 될 수도 있음을 알고 있다면 그 값을 사용하기 전에 널인지 검사할 수 있다 
- 어떤 플랫폼 타입이 값이 널이 될 수 없음을 알고 있다면 아무 널 검사 없이 그 값 사용 
  - 자바와 마찬가지로 여러분이 틀리면 NPE 
````java
public class PersonJava {
    private final String name;
    
    public PersonJava(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
````
- 자바로 작성된 클래스 
- getName 은 null을 리턴할까 아닐까?
  - 코틀린 컴파일러는 이 경우 String 의 널 가능성에 대해 전혀 알지 못한다 
  - 따라서 여러분이 널 가능성을 직접 처리해야만 한다. 
    - 이 변수가 널이 아님을 확실할 수 있다면 자바와 마찬가지로 추가 검사 없이 이를 참조 
    - 하지만 추가 검사 안하면 예외 발생 가능성도 있다 
```kotlin
fun yellAt(person: PersonJava) {
  println(person.name.toUpperCase() + " ! ! !")
  // toUpperCase 수신 객체인 person.name이 널이라 예외가 발생한다
  // person.name must not be null 이라고 난 나오는데 책에서는 toUpperCase() 에 대해 널이라 예외가 발생한다고 나온다 
  // 어쨌든 컴파일은 되니까 자바처럼 null 이 들어갈 수 있다는 것은 맞고 ... 오류 메시지의 차이인듯 
  /*
  fun yellAt(person: PersonJava) {
    println(person.name) // null
    println(person.name.toUpperCase() + " ! ! !") // person.name must not be null
  }
  이렇게 했을 때도 두번째에서 에러 나니까 
   */
}

//  yellAt(PersonJava(null))
```
- 여기서 NPE가 아니라 toUpperCase 가 수신 객체로 널을 받을 수 없다는 더 자세한 예외가 발생함을 유의
- 실제로 코틀린 컴파일러는 공개 public 가시성인 코틀린 함수의 널이 아닌 타입의 파라미터와 수신 객체에 대한 널 검사를 추가해준다 
  - 따라서 공개 가시성 함수에 널 값을 사용하면 즉시 예외가 발생한다. 
  - 이런 파라미터 값 검사는 함수 내부에서 파라미터를 사용하는 시점이 아니라 호출 시점에 이뤄진다 
  - 따라서 잘못된 인자로 함수를 호출해도 그 인자가 여러 함수에 전달돼 전혀 엉뚱한 위치에서 예외가 발생하지 않고 
    - 가능한 빨리 예외가 발생해 예외가 발생해도 더 원인을 파악할 수 있다 
- 물론 getName()의 반환 타입을 널이 될 수 있는 타입으로 해석에서 널 안전성 연산을 활용해도 된다 
```kotlin
fun yellAt(person: PersonJava) {
    println((person.name ?: "AnyOne").toUpperCase() + "! ! !") // ANYONE! ! !
}
```
- 이 예에서는 널 값을 제대로 실행하므로 실행 시점에 예외 발생 안한다 
- 자바 api다룰 때는 조심해 
  - 대부분 라이브러리는 널 관련 애노테이션을 쓰지 않는다 
  - 따라서 모든 타입을 널이 아닌 것처럼 다루기 쉽지만 그렇게 하면 오류가 발생할 수 있다 
- 코틀린은 왜 플랫폼 타입 도입?
  - 모든 자바 타입을 널이 될 수 있는 타입으로 다루면 더 안전하지 않을까?
  - 절대로 널이 될 수 없는 값에 대한 불필요한 널 검사를 막기 위해 
  - 특히 제네릭을 다룰 때 다 검사하면 상황이 나빠진다
    - 예를 들어 모든 자바 ArrayList<String> 을 코틀린에서 ArrayList<String?> 처럼 다루면 
      - 이 배열의 원소에 접근할 때마다 널 검사 하거나 안전한 캐스트를 수행해야 한다 
      - 이런식으로 처리하면 널 검사시 얻는 이익보다 검사에 드는 비용이 더 커진다 
  - 또한 모든 타입의 값에 대해 항상 널 검사를 작성하는 것은 너무 성가신 일 
  - 그래서 프로그래머에게 타입 제대로 처리하라는 책임 부여 
- 코틀린에서 플랫폼 타입을 선언할 수는 없다 
  - 자바 코드에서 가져온 타입만 플랫폼 타입이 된다 
```kotlin
    val person = PersonJava(null)
    val i: Int = person.name // Type mismatch: inferred type is String! but Int was expected
```
- 여기서 String!은 자바 코드에서 온 타입 
- 하지만 이런타입 표기를 코틀린 코드에 사용할 수 없고 느낌표가 이런 오류 메시지의 근원과 관련이 있는 경우가 거의 없어 대부분 지나칠 것 
- ! 표기는 String! 타입의 널 가능성에 대해 아무 정보도 없다는 뜻 
- 플랫폼 타입을 널이 될 수 있는 타입이나 널이 될 수 없는 타입 어느쪽으로든 사용할 수 있다 
```kotlin
val name1: String = person.name
val name2: String? = person.name
```
- 메서드를 호출할 때 처럼 이 경우에도 여러분이 프로퍼티의 널 가능성을 제대로 알고 사용해야 한다 

### 상속 
- 코틀린에서 자바 메서드 오버라이드 할 때 
  - 그 메서드의 파라미터와 반환 타입을 널이 될 수 있는 타입으로 선언할지 결정해야 한다 
- 리스트 5.18 String 파라미터가 있는 자바 인터페이스 
```java
public interface StringProcessor {
    void process(String value);
}
```
```kotlin
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
```
- 코틀린 컴파일러는 두 구현을 다 받아 들인다 
  - 자바 클래스나 인터페이스를 코틀린에서 구현시 널 가능성을 제대로 처리하는 것이 중요 
  - 구현 메서드를 다른 코틀린 코드가 호출할 수 있으므로 코틀린 컴파일러는 널이 될 수 없는 타입으로 선언한 모든 파라미터에 대해 널이 아님을 검사하는 단언문을 만들어준다 
  - 자바 코드가 그 메서드에게 널 값을 넘기면 이 단언문이 발동돼 예외가 발생한다 
  - 설령 파라미터를 메서드 안에서 결코 사용하지 않아도 이런 예외는 피할 수 없다 
- 정리 
  - 안전한 연산을 위한 안전 연산자 : 안전한 호출(?.) 엘비스연산자(?:) 안전한 캐스트(as?) 
  - 안전하지 못한 참조를 위한 연산자: 널아님 단언(!!)
  - let 을 사용해 널 안전성을 검증한 결과를 널이 될 수 없는 타입의 인자에 간결하게 전달 
  - 확장함수를 통해 널 검사를 함수 안으로 옮길 수 있음 
  - 자바 타입을 코틀린에서 표현할 때 사용하는 플랫폼 타입 

# 6.2 코틀린의 원시 타입 
- 코틀린은 원시타입과 래퍼타입을 구분하지 않는다 
## 6.2.1 원시 타입 : Int, Boolean 등 
- 자바는 원시타입과 참조타입 구분 
  - primitive type 원시타입 변수에는 그 값이 직접 들어가지만 
  - reference type 참조타입의 변수에는 메모리상의 객체 위치가 들어간다 
- 원시타입의 값에 대해 메서드를 호출하거나 컬렉션에 원시 타입 값을 담을 수는 없다 
- 자바는 참조 타입이 필요한 경우 특별한 래퍼로 원시타입 값을 감싸서 사용 
  - 정수의 컬렉션을 정의하려면 Collection<Integer> (Collection<Int> 가 아니라)
- 코틀린은 원시타입과 래퍼타입을 구분하지 않아 항상 같은 타입 사용 
- 래퍼 타입을 구분하지 않으면 편리 
- 더 나아가 코틀린에서는 숫자 타입 등 원시 타입의 값에 대해 메서드를 호출할 수 있다. 
```kotlin
fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done!")
}

//  showProgress(146) // We're 100% done!
```
- 그렇다면 코틀린은 모든 것을 항상 객체로 표현? 그럼 너무 비효율 아님? 코틀린은 그렇지 않다 
- 실행시점에 숫자 타입은 가능한 한 가장 효율적인 방식으로 표현된다 
  - 대부분의 경우 코틀린의 Int타입은 자바 int 타입으로 컴파일된다 
  - 이런 컴파일이 불가능한 경우는 컬렉션과 같은 제네릭 클래스를 사용하는 경우 뿐 
    - 예를 들어 Int타입을 컬렉션의 타입 파라미터로 넘기면 그 컬렉션에는 Int의 래퍼 타입에 해당하는 java.lang.Integer 객체가 들어간다 
- 자바 원시타입에 해당하는 타입은 다음과 같다 
  - 정수 타입  Byte, Short, Int, Long
  - 부동소수점 수 타입 Float, Double
  - 문자 타입 Char
  - 불리언 타입 Boolean
- Int와 같은 코틀린 타입에는 널 참조가 들어갈 수 없어 쉽게 그에 상응하는 자바 원시타입으로 컴파일 가능 
- 마찬가지로 자바 원시타입은 결코 널이 될 수 없으므로 자바 원시타입을 코틀린에서 사용할 때 (플랫폼 타입이 아니라) 널이 될 수 없는 타입으로 취급 가능 

## 6.2.2 널이 될 수 있는 원시타입 : Int? Boolean? 등 
- 널이 될 수 있는 코틀린 타입은 자바 원시타입으로 표현 불가능 
- 코틀린에서 널이 될 수 있는 원시타입을 사용시 그 타입은 자바의 래퍼 타입으로 컴파일 
```kotlin
class Person4(
    val name: String,
    val age: Int? = null
) {
    fun isOrderThan(other: Person4): Boolean? {
        if (age == null || other.age == null) return null
        return age > other.age
    }
}
```
```kotlin
   println(Person4("Sam", 35).isOrderThan(Person4("Any", 42))) // false
    println(Person4("Sam", 35).isOrderThan(Person4("Any"))) // null
```
- 적용되는 널 규칙 
  - 널이 될 가능성이 있으므로 Int? 타입의 두 값을 직접 비교할 수는 없다 
  - 먼저 두 값이 모두 널이 아닌지 검사해야만 한다 
  - 컴파일러는 널 검사를 모두 마친 다음에야 두 값을 일반적인 값처럼 다루게 허용한다 
- Person클래에 선언된 age java.lang.Integer로 저장 
  - 그런 자세항 사항은 자바에서 가져온 클래스를 다룰 때만 문제가 된다 
  - 코틀린에서 적절한 타입을 찾으려면 프로퍼티에 널이 들어갈 수 있는지만 고민하면 된다 
- 제네릭 클래스의 경우 래퍼 타입을 사용 
  - 어떤 클래스의 타입 인자로 원시 타입을 넘기면 코틀린은 그 타입에 대한 박스 타입을 사용 
  - 예를 들어 다음 문장에서는 null 값이나 널이 될 수 있는 타입을 전혀 사용하지 않았지만 리스트는 래퍼인 Integer로 이뤄진 리스트 
```kotlin
val listOfInts = listOf(1, 2, 3)
```
- 이렇게 컴파일하는 이유는 자바 가상머신에서 제네릭을 구현하는 방법 때문 
  - jvm 은 타입 인자로 원시 타입을 허용 안한다 
  - 따라서 자바나 코틀린 모두에서 제네릭 클래스는 항상 박스 타입 사용해야 함 
  - 원시타입으로 이뤄진 대규모 컬렉션을 효율적으로 저장해야 한다면 원시타입으로 이뤄진 효율적인 컬렉션을 제공하는 서드파티 라이브러리(트로브 등)을 사용하거나 배열 사용
  
## 6.2.3 숫자 변환
- 코틀린은 숫자를 다른 타입의 숫자로 자동 변환하지 않는다 (자바와 다른 점)
  - 결과 타입이 허용하는 숫자의 범위가 원래 타입의 범위보다 넓은 경우조차도 자동 변환은 불가능 
```kotlin
    val i = 1
    val l: Long = i //  Type mismatch: inferred type is Int but Long was expected
```
- 대신 직접 변환한 메서드 호출해야 함 
```kotlin
    val i = 1
    val l: Long = i.toLong()
```
- 코틀린은 모든 원시 타입(Boolean 제외)에 대한 변환 함수 제공 
  - 변환 함수 이름은 toByte(), toShort(), toChar() 등과 같다 
  - 양방향 변환 함수 모두 제공 
    - 어떤 타입을 더 표현 범위가 넓은 타입으로 변환하는 함수도 있고 : (Int.toLong())
    - 타입을 범위가 더 좁은 타입으로 변환하면서 값을 벗어나면 일부를 잘라내는 함수 (Long.toInt())
- 코틀린은 개발자의 혼란을 피하기 위해 타입 변환을 명시하기로 결정 
  - 특히 박스 타입을 비교하는 경우 문제가 많다 
  - 두 반스 타입간의 equals 메서드는 그 안에 들어있는 값이 아니라 박스 타입 객체 비교 
  - 자바에서 new Integer(42).equals(new Integer(42)) 는 false
- 코틀린에서 묵시적 형 변환을 허용한다면 다음과 같이 쓸 수 있을 것 
```kotlin
    val x = 1 // int 타입 변수
    val list = listOf(1L, 2L, 3L)
    x in list // 묵시적 타입 변환으로 인해 false
```
- 대부분이 생각하는 바와 달리 이 식은 false 
- 따라서 x in list는 컴파일 되면 안된다 
- 코틀린에서는 타입을 명시적으로 변환해서 같은 타입의 값으로 만든 후 비교해야 한다. 
```kotlin
    val x = 1 // int 타입 변수
    println(x.toLong() in listOf(1L, 2L, 3L)) // true
```
- 코드에서 동시에 여러 숫자 타입을 사용하려면 예상치 못한 동작을 피하기 위해 각 변수를 명시적으로 변환해야 한다 
- 원시타입 리터럴 
  - 코틀린에서는 소스코드에서 단순한 10진수(정수 외에) 다음과 같은 숫자 리터럴 허용 
    - L 접미사가 붙은 Long 타입 리터럴 : 123L
    - 표준 부동 소수점 표기법을 사용한 Double 타입 리터럴 : 0.12, 2.0, 1.2e10, 1.2e-10
    - f나 F접미사가 붙은 Float 타입 리터럴 : 123.4f, oxbcdL
    - 0b나 0B 접두사가 붙은 2진 리터럴: 0b000000101
  - 코틀린 1.1 부터는 리터럴 중간에 밑줄(_) 넣을 수 있다 
    - 1_234, 1_0000_0000_000L, 1_000.123_456, 0b0100_0001 
  - 문자 리털럴의 경우 자바와 마찬가지 
    - 작은 따옴표 안에 문자를 넣으며 되며 
    - 이스케이프 시퀀스 사용 가능 
      - '1', '/t'(이스캐이프 시퀀스로 정의한 탭 문자) '/u00019' (유니코드 이스케이프 시퀀스로 정의한 탭 문자) 등도 모든 코틀린 문자 리터럴 
- 숫자 리터럴 사용시 보통 변환함수 호출 필요 없다 
  - 42L, 42.0f 처럼 상수 뒤에 타입을 표현하는 문자를 붙이면 변환 필요 없다 
  - 또한 여러분이 직접 변환하지 않아도 숫자 리터럴을 타입이 알려진 변수에 대입하거나 함수에게 인자로 넘기면 컴파일러가 필요한 변환을 자동으로 넣어준다 
  - 추가로 산술 연산자는 적당한 타입의 값을 잘 받아들이게 이미 오버로드 돼 있다
```kotlin

fun foo(l: Long) = println(l)
//    val b: Byte = 1 // 상수 값은 적절한 타입으로 해석된다 
//    val l = b + 1L // + 는 Byte와 Long 을 인자로 받을 수 있다 
//    foo(42) // 컴파일러는 42를 Long 으로 해석 


```
- 코틀린 산술 연산자에서도 자바오 똑같이 숫자 연산시 값 넘침 overflow 발 생 가능 
- 코틀린은 값 넘침을 검사하느라 추가 비용 들이지 않는다 
- 문자열을 숫자로 변환하기 
  - 코틀린 표준 라이브러리는 문자열을 원시타입으로 변환하는 여러 함수를 제공한다 (toInt, toByte, toBoolean)
  - 이런 함수는 문자열의 내용을 각 원시타입을 표기하는 문자열로 파싱 파싱에 실패시  NumberFormatException 발생 
  - `println("42".toIng())`  // 42

## 6.2.4 Any, Any? 최상위 타입 
- 자바에서 Object가 최상위 타입 코틀린에서는 Any가 모든 널이 될 수 없는 타입의 조상 
- 하지만 자바에서는 참조 타입만 Object 를 정점으로 하는 타입 계층에 포함 원시타입은 그런 게층에 들어가 있지 않다 
- 이는 자바에서 Object 타입의 객체가 필요할 경우 int 와 같은 원시타입을 java.lang.Integer와 같은 래퍼 타입으로 감싸야 한다는 뜻 
- 하지만 코틀린에서는 Any가 Int 등의 원시 타입을 포함한 모든 타입의 조상 타입이다 
- 자바에서 마찬가지로 코틀린에서도 원시타입값을 Any 타입의 변수에 대입하면 자동으로 값을 객체로 감싼다 
```kotlin
val answer: Any = 42 // Any가 참조타입이라 42가 박싱된다 
```
- Any가 널이 될 수 없는 타입임에 유리 
  - Any 타입의 변수에는 널이 들어갈 수 없다 
  - 코틀린에서는 널을 포함하는 모든 값을 대입할 변수를 선언하려면 Any? 타입을 사용해야 함 
- 내부에서 Any java.lang.Object 에 대응 
  - 자바 메서드에서 object를 인자로 받거나 반환하면 코틀린에서는 Any로 그 값을 취급 
    - 더 정확히 말하면 널이 될 수 있는지 여부를 알 수 없어 플랫폼 타입인 Any!로 취급 
  - 코틀린 함수가 Any를 사용하면 자바 코드의 Object로 컴파일 된다 
- 코틀린 모든 클래스에는 toString, equals, toHashCode라는 세 메서드가 들어 있다 
  - 이 세 메서드는 Any에 정의된 메서드 상속한 것 
  - 하지만 java.lang.Object 에 있는 다른 메서드는 (wait, notify .. ) Any 에서 사용할 수 없다
  - 따라서 그런 메서드를 호하고 싶다면 java.lang.Object 타입으로 값을 캐스트 해야 한다 


## 6.2.5 Unit 타입 : 코틀린의 void 
- 코틀린 Unit 타입은 자바의 void 와 같은 기능 
- 관심을 가질만한 내용을 전혀 반환하지 않는 함수의 반환 타입으로 Unit 쓸 수 있다 
```kotlin
fun f(): Unit {
    
}
```
- 이는 반환 타입 선언 없이 정의한 블록이 본문인 함수와 같다. 
```kotlin
fun f(){ // 반환 타입 명시 안함 
    
}
```
- 대부분의 경우 void Unit 차이 알기 어렵다 
  - 코틀린 함수의 반환 타입이 Unit 이고 그 함수가 제네릭 함수를 오버라이드하지 않는다면 그 함수는 내부에서 void 함수로 컴파일 
  - 그런 코틀린 함수를 자바에서 오버라이드 하는 경우 void 를 반환 타입으로 해야 한다
-  void Unit 차이는?
  - Unit 은 모든 기능을 갖는 일반적인 타입 
  - void와 달리 Unit 을 타입 인자로 쓸 수 있다 
  - Unit 타입에 속한 값은 단 하나뿐이며 그 이름도 Unit
  - Unit 타입의 함수는 Unit 값을 묵시적으로 반환 
  - 이 두 특성은 제네릭 파라미터를 반환하는 함수를 오버라이드 하면서 반환타입으로 Unit 을 쓸 때 유용 
```kotlin
interface Processor<T> {
    fun process(): T
}

class NoResultProcessor: Processor<Unit> {
    override fun process() { // Unit 타입을 반환하지만 타입을 지정할 필요 없다 
       // 여기서 return 을 명시할 필요가 없다 컴파일러가 묵시적으로 return Unit 을 넣어준다
    }
}
```
- 타입 인자로 값 없음을 표현하는 문제를 자바에서 어떻게 코틀린과 같이 깔끔하게 해결할 수 있을지 생각햅보라 
  - 별도의 인터페이스를 사용해 값을 반환하는 경우와 값을 반환하지 않는 경우를 분리하는 방법도 있다. 
  - 다른 방법으로는 타입 파라미터로 특별히 java.lang.Void를 사용하는 방법도 있다 
  - 후자를 택한다 해도 여전히 Void 타입에 대응할 수 있는 유일한 값인 null을 반환하기 위해 return null을 명시해야 한다
  - 이런 경우 반환 타입이 void 가 아니므로 함수를 반환할 때 return 을 사용할 수 없고 항상 return null을 사용해야 한다
- 왜 코틀린에서 void 가 아니라 unit 이라는 다른 이름 골랐을까?
- 함수형 프로그래밍에서 전통적으로 unit은 단 하나의 인스턴스만 갖는 타입을 의미해왔고 바로 그 유리한 인스턴스의 유무가 자바 void 와 코틀린 unit을 구분하는 가장 큰 차이다 
- 어쩌면 자바 등의 명령형 프로그래밍 언에어서 관례적으로 사용해온 void 라는 이름을 사용할 수 있겠지만 
- 코틀린에는 nothing 이라는 전혀 다른 기능을 하는 타입이 하나 존재한다. void, nothing 두 이름은 의미가 비슷해 혼란을 야기 하기 쉽다 

## 6.2.6 Nothing 타입: 이 함수는 결코 정상적으로 끝나지 않는다 
- 코틀린에는 결코 성공적으로 값을 돌려주는 일이 없으므로 반환 값이라는 개념 자체가 의미 없는 함수가 일부 존재한다 
  - 예를 들어 테스트 라이브러리들은 fail 이라는 함수를 제공하는 경우가 많다 
  - fail은 특별한 메시지가 들어있는 예외를 던져서 현재 테스트를 실패시킨다 
  - 다른 예로 무한 루프를 도는 함수도 결코 값을 반환하지 않으며, 정상적으로 끝나지 않는다 
- 그런 함수를 호출하는 코드를 분석하는 경우 함수가 정상적으로 끝나지 않는다는 사실을 알면 유용하다 
- 그런 경우를 표현하기 위해 코틀린에는 Nothing 이라는 특별한 반환 타입이 있다 
```kotlin

 import java.lang.IllegalStateException
 
 fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}

// fail("Error occurred")
```
- Nothing 값은 아무 값도 포함하지 않는다 
- 따라서 Nothing 은 함수의 반환 타입이나 반환타입으로 쓰일 파라미터로만 쓸 수 있다 
- 그 외 다른 용도로 사용하는 경우 Nothing 타입의 변수를 선언하더라도 그 변수에 아무 값도 저장할 수 없으므로 아무 의미도 없다 
- Nothing 을 반환하는 함수를 엘비스 연잔자 우항에 사용해서 전제 조건을 검사할 수 있다 
```kotlin
val address = company.address ?: fail("No address")
println(address.city)
```
- 이 예제는 Nothing 이 얼마나 유용한지 보여준다 
  - 컴파일러는 Nothing 이 반환 타입인 함수가 결코 정상 종료되지 않음을 알고 
  - 그 함수를 호출하는 코드를 분석할 때 사용한다 

# 6.3 컬렉션과 배열 

## 6.3.1 널 가능성과 컬렉션 
- 컬렉션 안에 널 값을 넣을 수 있는지 여부는 어떤 변수의 값이 널이 될 수 있는지 여부와 마찬가지로 중요하다 
```kotlin
fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>() // 널이 될 수 있는 int 값으로 이뤄진 리스트 만들기
    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number) // (널이 아닌 정수) 를 리스트에 추가한다 
        } catch (e: NumberFormatException) {
            result.add(null) // 현재 줄을 파싱할 수 없으므로 리스트에 널을 추가한다 
        }
    }
    return result
}
```
- List<Int?>는 Int? 타입의 값을 저장할 수 있다 (Int, null 저장 가능)
- 코틀린 1.1 부터는 파싱에 실패하면 null 을 반환하는 String.toIntOrNull 을 사용해 예제를 더 줄일 수 있다 
- 어떤 변수 타입의 널 가능성과 타입 파라미터로 쓰이는 타입의 널 가능성의 차이를 살표보자 
  - List<Int?>, List<Int>?
  - 첫번째 경우 리스트 자체는 항상 널이 아니다 
    - 하지만 리스트에 들어있는 각 원소는 널이 될 수도 있다 
  - 두 번째 경우 리스트를 가리키는 변수에는 널이 들어갈 수 있지만 리스트 안에는 널이 아닌 값만 들어간다
- 널이 될 수 있는 값으로 이뤄진 널이 될 수 있는 리스트 List<Int?>?
  - 이런 리스트 처리할 때는 
    - 변수에 대해 널 검사 수행한 다음
    - 그 리스트에 속한 모든 원소에 대해 다시 널 검사 수행해야 한다. 

```kotlin
fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (number in numbers) {
        if (number != null)
            sumOfValidNumbers += number
        else
            invalidNumbers++
    }
    println("Sum of ValidNumbers: $sumOfValidNumbers")
    println("Invalid Numbers: $invalidNumbers")
}
```
- 리스트의 원소에 접근하면 Int? 타입의 값을 얻고, 따라서 그 값을 산술 식에 사용하기 전에 널 여부 검사 
- 널이 될 수 있는 값으로 이뤄진 컬렉션으로 널 값을 걸러내는 경우가 자주 있어서 코틀린 표준 라이브러리는 그런 일을 하는 filterNotNull이라는 함수를 제공한다 
- filterNotNull 함수 사용해 더 단순하게 만들기 
```kotlin
fun addValidNumbers(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum of ValidNumbers: ${validNumbers.sum()}")
    println("Invalid Numbers: ${numbers.size - validNumbers.size}")
}
```
- filterNotNull 가 컬렉션 안에 널이 들어있지 않음을 보장 -> validNumbers 는 List<Int>

# 6.3.2 읽기 전용과 변경 가능한 컬렉션 
- 코틀린 컬렉션, 자바 컬렉션 중요 차이 
  - 코틀린에서는 컬렉션 안의 데이터에 접근하는 인터페이스와 
  - 컬렉션 안의 데이터를 변경하는 인터페이스를 분리했다는 점 
- kotlin.collections.Collection
  - 이 Collection 인터페이스를 사용하면 컬렉션 안의 원소에 대해 이터레이션하고, 컬렉션 크기 얻고, 어떤 값이 컬렉션 안에 들어있는지 검사하고, 
  - 컬렉션에서 데이터를 읽는 여러 다른 연산을 수행할 수 있다 
  - 하지만 Collection 에는 원소를 추가하거나 제거하는 메서드가 없다
- 컬렉션 데이터 수정하려면 kotlin.collections.MutableCollection 인터페이스 사용
  - MutableCollection는 일반 인터페이스인 kotlin.collections.Collection를 확자앟면서 우너소를 추가, 삭제, 컬렉션 안의 모든 우너소를 지우는 등의 메서드를 더 제공 
  - 코드가 가능하면 항상 읽기 전용 인터페이스를 사용하는 것을 일반적인 규칙으로 삼아라 
    - 코드가 컬렉션을 변경할 필요가 있을 때면 변경 가능한 버전을 사용하라 
- 콜렉션의 읽기 전용 인터페이스와 변경 가능 인터페이스를 구별한 이유는 
  - 프로그램에서 데이터에 어떤 일이 벌어지는지를 더 쉽게 이해하기 우ㅣ함 
  - 어떤 함수가 MutableCollection이 아닌 Collection 타입의 인자를 받으면 그 함수는 컬렉션을 읽기만 하고 변경하지 않는다 
  - 반면 어떤 함수가 MutableCollection 을 인자로 받으면 그 함수가 컬렉션의 데이터 바꾸리라 가정할 수 있다 
  - 어떤 컴포넌트의 내부 상태에 컬렉션이 포함된다면 그 컬렉션을 MutableCollection을 인자로 받는 함수에 전달할 때는 어쩌면 원본의 변경을 막기 위해 컬렉션을 복사해야 할 수도 있다 
    - 이런 패턴을 방어적 복사 defensive copy 라 부른다 
- 리스트 6.24 읽기 전용과 변경 가능한 컬렉션 인터페이스 
  - 다음 에서 copyElements 함수가 source 를 변경하지 않지만 target을 변경하리라는 사실을 알 수 있다 
```kotlin
fun <T> copyElements(source: Collection<T>, 
target: MutableCollection<T>) {
    for (item in source) { // source 컬렉션의 모든 원소에 대해 루프를 돈다 
        target.add(item) // 변경 가능한 target 컬렉션에 원소를 추가한다 
    }
}
```
- target 에 해당하는 인자로 읽기 전용 컬렉션을 넘길 수 없다
  - 실제 그 값(컬렉션)이 변경 가능한 컬렉션인지 여부와 관계없이 선언된 타입이 읽기 전용이라면 
  - target 에 넘기면 컴파일 오류가 난다 
```kotlin
 val source: Collection<Int> = arrayListOf(3, 5, 7)
    val target: Collection<Int> = arrayListOf(1)
    copyElements(source, target) // target 에서 컴파일 오류 발생 
```
- 컬렉션 인터페이스를 사용할 때 항상 염두에 둬야 할 핵심은 읽기 전용 컬렉션이라고 해서 꼭 변경 불가능한 컬렉션일 필요는 없다는 것 
  - 읽기 전용 인터페이스 타입인 변수를 사용할 때 그 인터페이스는 실제로 어떤 컬렉션 인스턴스를 가리키는 수 많은 참조 중 하나일 수 있다 
- 같은 인스턴스를 가리키는 참조를 2개 두고 하나는 변경 불가능 타입 참조, 하나는 변경 가능한 인터페이스 타입의 참조도 있을 수 있다 
- 이런 상황에서 이 컬렉션을 참조하는 다른 코드를 호출하거나 병렬 실행하면 
  - 컬렉션을 사용 도중 다른 컬렉션이 이 컬렉션의 내용을 변경하는 상황이 생길 수 있고 
  - 이런 상황에서는 CounccerentModificationException 이나 다른 오류 발생 가능 
  - 따라서 읽기 전용 컬렉션이 항상 스레드 안전 하지 않다는 점을 명시 
  - 다중 스레드 환경에서 데이터를 다루는 경우 그 데이터를 적절히 동기화 하거나 동시 접근을 허용하는 데이터 구조를 활용해야 한다 
