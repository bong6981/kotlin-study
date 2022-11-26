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
