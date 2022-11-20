5. 람다로 프로그래밍
- 람다식, 또는 람다는 기본적으로 다른 함수에 넘길 수 있는 작은 코드 조각을 뜻한다 
- 람다를 사용하면 쉽게 공통 코드 구조를 라이브러리 함수로 뽑아낼 수 있다 
- 코틀린 표준 라이브러리는 람다를 아주 많이 사용한다 
- 람다를 자주 사용하는 경우로 컬렉션 처리를 들 수 있다 

# 5.1 람다 식과 멤버 참조 
- 자바 8의 람다는 기다려온 람다의 도입 
- 왜 람다가 그렇게 중요할까?

## 5.1.1 람다 소개: 코드 블록을 함수 인자로 넘기기 
- "이벤트가 발생하면 이 핸들러를 실행하자"나 "데이터 구조의 모든 원소에 이 연산을 적용하자"와 같은 생각을 표현하기 위해 일련의 동작을 변수에 저장하거나 다른 함수에 넘겨야 하는 경우가 자주 있다 
- 예전에 자바에서는 무명 내부 클래스를 통해 이런 목적을 달성했다 
- 무명 내부 클래스를 사용하면 코드를 함수에 넘기거나 변수에 저장할 수 있기는 하지만 상당히 번거롭다 
- 함수형 프로그래밍에서는 함수를 값처럼 다루는 접근 방법을 택해서 이 문제를 해결 
- 클래스 선언, 클래스의 인스턴스를 함수에 넘기는 대신 
- 함수형 언어에서는 함수를 직접 다른 함수에 전달할 수 있다 
- 람다 식을 사용하면 코드가 더 간결해진다 
  - 람다식을 사용하면 함수를 선언할 필요가 없고, 코드 블록을 직접 함수의 인자로 전달할 수 있다 
- 예제
  - 버튼 클릭에 따른 동작을 정의하고 싶을 때 
  - 클릭 이벤트를 처리하는 리스너 추가 
  - 버튼 클릭 리스너는 onClick이라는 메서드가 들어있는 onClickListener를 구현해야 한다 
```java
button.setOnClickListener(new onClickListener() {
    @Override
    public void onClick(View view) {
        ///...
    }
})
```
- 무명 내부 클래스를 선언하느라 코드가 번잡스럽다 
- 람다로 리스너 구현하기 
```kotlin
button.setOnClickListenr {/* 클릭시 수행할 동작 */}
```
- 이 코드는 자바 무명 내부 클래스와 같은 역할을 하지만 훨씬 더 간결하고 읽기 쉽다 
- 이 예제는 람다를 메서드가 하나뿐인 무명 객체 대신 사용할 수 있다는 사실을 보여준다 
- 이제 함수형 언어에서 전통적으로 람다를 많이 활용해온 컬렉션에 대해 살펴보자 

## 5.1.2 람다와 컬렉션 
- 코드에서 중복을 제거하는 것은 프로그래밍 스타일을 개선하는 중요한 방법 중 하나다. 
- 컬렉션을 다룰 때 수행하는 대부분의 작업은 몇 가지 일반적인 패턴에 속한다 
- 따라서 그 패턴은 라이브러리 안에 있어야 한다 
- 하지만 람다가 없다면 컬렉션을 편리하게 처리할 수 있는 좋은 라이브러리를 제공하기 힘들다 
- 그런 이유로 (자바 8 이전에는) 자바에서 쓰기 편한 컬렉션 라이브러리가 적었으며 
- 그에 따라 자바 개발자들은 필요한 컬렉션 기능을 직접 작성하곤 했다 
- 코틀린에서는 이런 습관을 버려야 한다 
```kotlin
data class Person (val name: String, val age:Int)
```
- 사람들로 이루어진 리스트가 있고, 그 중에 가장 연장자를 찾고 싶다. 
- 람다를 사용해본 경험이 없는 개발자라면 루프를 써서 직접 검색을 구현할 것 
- 리스트 5.3 컬렉션을 직접 검색하기 
```kotlin
fun findTheOldest(people: List<Person>) {
    var maxAge = 0 // 가장 많은 나이 저장 
    var theOldest: Person? = null 
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}
```
- 이런 로직을 매번 직접 짜다 보면 실수를 하기가 쉽다 
- 코틀린에서는 더 좋은 방법 : 라이브러리 함수 쓰기 
- 리스트 5.4 람다를 사용해 컬렉션 검색하기 
```kotlin
fun main(args: Array<String>) {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    println(people.maxBy { it.age })  // Person(name=Bob, age=31)
}
```
- 모든 컬렉션에 대해 maxBy를 호출할 수 있다 
- maxBy는 가장 큰 우너소를 찾기 위해 비교에 사용할 값을 돌려주는 '함수'를 인자로 받는다
- 중괄호로 둘러싸인 코드 { it.age }는 바로 비교에 사용할 값을 돌려주는 함수다 
- 이 코드의 컬렉션의 원소를 인자로 받아서 (it가 그 인자를 가리킨다) 비교에 사용할 값을 반환한다 
- 이 예제에서는 컬렉션의 우너소가 Peerson 객체였으므로 이 함수가 반환하는 값은 Person 객체의 age 필드에 저장된 나이 정보다 
- 이런식으로 단지 함수나 프로퍼티를 반환하는 역할을 수행하는 함수를 람다는 멤버 참조로 대치할 수 있다 
```kotlin
@Deprecated("Use maxByOrNull instead.", ReplaceWith("this.maxByOrNull(selector)"))
@DeprecatedSinceKotlin(warningSince = "1.4", errorSince = "1.5", hiddenSince = "1.6")
@Suppress("CONFLICTING_OVERLOADS")
public inline fun <T, R : Comparable<R>> Iterable<T>.maxBy(selector: (T) -> R): T? {
    return maxByOrNull(selector)
}
```
- 리스트 5.5 멤버 참조를 사용해 컬렉션 검색하기 
```kotlin
people.maxBy(Person::age)
```
- 이 코드는 리스트 5.4와 같은 역할을 한다. 
- 자바 컬렉션에 대해(자바 8이전에) 수행하던 대부분의 작업은 람다나 멤버 참조를 인자로 취하는 라이브러리 함수를 통해 개선할 수 있다 
- 그렇게 람다나 멤버 참조를 인자로 받는 함수를 통해 개선한 코드는 더 짧고 더 이해하기 쉽다 

## 5.1.3 람다 식의 문법 
- 람다는 값처럼 여기저기 전달할 수 있는 동작의 모음이다 
- 람다를 따로 선언해서 변수에 저장할 수도 있다 
- 하지만 함수에 인자로 넘기면서 바로 람다를 정의하는 경우가 대부분 
- 람다식을 선언하기 위한 문법 (아래)
```kotlin
{ x : Int, y : Int -> x + y } 
```
- 코틀린 람다식은 항사 중괄호로 둘러싸여 있다 
- 인자 목록 주변에 괄호가 없다는 사실을 꼭 기억하라 
- 화살표(->)가 인자 목록과 람다 본문을 구분해준다 
- 
- 람다 식을 변수에 저장할 수 있다 
- 람다가 저장된 변수를 다른 일반 함수와 마찬가지로 다룰 수 있다 
  - 변수 이름 뒤에 괄호를 놓고 그 안에 필요한 인자를 넣어서 람다를 호출할 수 있다 
```kotlin
val sum = {x: Int, y: Int -> x + y}
println(sum(3, 5))
```
- 원한다면 람다식을 직접 호출해도 된다 
```kotlin
{ println(42) }() //42
```
- 하지만 이는 읽기 어렵고 쓸모도 없다 
- 굳이 람다를 만들자마자 호출하느니 람다 본문을 직접 실행하는 편이 낫다 
- 이렇게 코드의 일부분을 블록으로 둘러싸 실행할 필요가 있다면 run을 사용한다 
- run은 인자로 받은 람다를 실행해주는 라이브러리 함수다 
```kotlin
 run{ println(42) } // 람다 본문에 있는 코드를 실행한다
```
- 실행 시점에 코틀린 람다 호출에는 아무 부가 비용이 들지 않으며, 프로그램의 기본 구성 요소와  비슷한 성능을 낸다. 
- 8.2 절에서 그 이유 설명 
```kotlin
val people = listOf(Person("Alice", 29), Person("Bob", 31))
println(people.maxBy { it.age })
```
- 이 예제에서 코틀린이 코드를 줄여 쓸 수 있게 제공했던 기능을 제거하고 정식으로 람다를 작성하면 다음과 가탇 
```kotlin
    println(people.maxBy({p: Person -> p.age}))
```
- 중괄호 안에 코드는 람다식이고 그 람다식을 maxBy한테 넘긴다. 
- 람다식은 Person 타입의 값을 인자로 받아서 인자의 age를 반환한다 
- 하지만 이 코드는 번잡
  - 구분자가 너무 많이 쓰여서 가독성이 떨어짐 
  - 컴파일러가 문맥으로부터 유추할 수 있는 인자타입을 굳이 적을 필요가 없다 
  - 마지막으로 인자가 단 하나뿐인 경우 굳이 인자에 이름을 붙이지 않아도 된다 
- 이 개선을 적용해 보자 
- 중괄호 부터 시작 : 코틀린에는 함수 호출시 맨 뒤에 있는 인자가 람다 식이라면 그 람다를 괄호 밖으로 빼낼 수 있는 문법 관습이 있다 
  - 이 예제에서는 람다가 유일한 인자, and 마지막 인자 
  - 따라서 괄호 뒤에 람다를 둘 수 있다 
```kotlin
    println(people.maxBy() {p: Person -> p.age})
```
- 이 코드처럼 람다가 어떤 함수의 유일한 인자고, 괄호 뒤에 람다를 썼다면 호출시 괄호를 생략 가능 
```kotlin
   println(people.maxBy {p: Person -> p.age})
```
- 이 세가지 형태는 모두 같은 뜻이지만 마지막 문장이 가장 읽기 쉽다 
- 람다가 함수의 유일한 인자라면 여러분은 분명 괄호 없이 람다를 바로 쓰기를 원하게 될 것이다 
- 인자가 여럿 있는 경우에는 람다를 밖으로 빼낼 수도 있고 람다를 괄호 안에 유지해서 함수의 인자임을 분명히 할 수 도 있다
- 둘 이상의 람다를 인자로 받는 함수라고 해도 인자 목록의 맨 마지막 람다만 밖으로 뺄 수 있다 
- 따라서 그런 경우에는 괄호를 사용하는 일반적인 함수 호출 구문을 사용하는 편이 낫다 
- 표준 라이브러리의 joinToString은 매 마지막 인자로 함수를 받는다 
  - 리스트의 원소를 toString이 아닌 다른 방식으로 문자열로 변환하고 싶은 경우 이 인자를 활용한다 
- 리스트 5.6 이름 붙인 인자를 사용해 람다를 넘기기 
```kotlin
    val people = listOf(Person("이몽룡", 29), Person("성춘향", 31))
    val names = people.joinToString(separator = " ",
        transform = { p: Person -> p.name })
    println(names) // 이몽룡 성춘향
```
- 이 함수 호출에서 함수를 괄호 밖으로 뺀 모습은 다음과 같다 
```kotlin
people.joinToString(separator = " ") {
            p: Person -> p.name
    }
```
- 리스트 5.6에서는 이름 붙인 인자를 사용해 람다를 넘겨서 람다를 어떤 용도로 쓰는지 더 명확히 했다 
- 리스트 5.7은 더 간결하지만 람다의 용도를 분명히 알 수는 없다 5.7이 더 어려울 것
- 리스트 5.8 람다 파라미터 타입 제거하기 
```kotlin
 println(people.maxBy {p: Person -> p.age}) // 파라미터 타입 명시 
 println(people.maxBy {p -> p.age}) // 파라미터 타입 생략 (컴파일러 추론)
```
- 로컬 변수처럼 컴파일러는 람다 파라미터의 타입도 추론할 수 있다 
- 따라서 파라미터 타입을 명시할 필요가 없다 
- maxBy 함수의 경우 파라미터의 타입은 항상 컬렉션 원소 타입과 같다 
- 컴파일러는 여러분이 Person 타입의 객체가 들어있는 컬렉션에 대해 maxyby 를 호출할 수 있다는 사실을 알고 있어 람다의 파라미터도 Person이라는 사실을 이해할 수 있다 
- 컴파일러가 타입 추론을 못할 때도 있다 그 때만 타입을 명시해라 
- 파라미터 중 일부만 타입을 지정하고 나머지는 타입 지정 없이 이름만 남겨둬도 된다 
- 컴파일러가 파라미터 타입 중 일부를 추론하지 못하거나 타입 정보가 코드를 읽을 때 도움이 된다면 그렇게 일부 타입을 명시해도 ok 
- 람다의 파라미터 이름을 디폴트 이름인 it로 바꾸면 람다 식이 더 간결해진다
- 람다의 파라미터가 하나뿐이고 그 타입을 컴파일러가 추론할 수 있는 경우 it을 바로 쓸 수 있다 
```kotlin
 println(people.maxBy {it.age})
```
- 람다의 파라미터 이름을 따로 지정하지 않은 경우메나 it라는 이름이 자동으로 만들어진다 
- 노트
  - it는 코드를 간결하게 만들어주지만 남발하면 안된다 
  - 람다 안에 람다가 중첩되는 경우 각 람다의 파라미터를 명시하는 것이 난다
- 람다를 변수에 저장할 때는 파라미터 타입을 추론할 문맥이 존재하지 않는다. 따라서 파라미터 타입을 명시해야 한다 
```kotlin
val getAge = {p: Person -> p.age}
people.maxBy(getAge)
```
- 여러 줄로 이뤄진 람다도 있다 
```kotlin
val sum = { x: Int, y: Int ->
  println("$x and $y ...")
  x + y
}
```
- 본문이 여러 줄로 이뤄진 경우 본문의 맨 마지막에 있는 식리 람다의 결과 

## 5.1.4 현재 영역에 있는 변수에 접근 
- 자바 메서드 안에서 무명 내부 클래스를 정의할 때 메서드의 로컬 변수를 무명 내부 클래스에서 사용할 수 있다 
- 람다 안에서도 같은 일을 할 수 있다 
- 람다를 함수 안에서 정의하면 함수의 파라미터 뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용할 수 있다 
- forEach는 컬렉션의 모든 원소에 대해 람다를 호출해준다 
- 리스트 5.10 함수 파라미터를 람다 안에서 사용하기 
```kotlin
fun printMessageWithPrefix(messages: Collection<String>, prefix:String) {
    messages.forEach{
        println("$prefix $it")
    }
}
```
```kotlin
val errors = listOf("403 Forbidden", "404 Not Found")
printMessageWithPrefix(errors, "Error : ")
/** Error :  403 Forbidden
Error :  404 Not Found **/
```
- 자바와 다른점 : 코틀린은 람다 안에서는 파이널 변수가 아닌 변수에 접근할 수 있다 
  - 또한 람다 안에서 바깥의 변수를 변경해도 된다 
- 리스트 5.11 람다 안에서 바깥의 로컬 변수 변경하기 
```kotlin
fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var severErrors = 0
    responses.forEach{
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            severErrors++
        }
    }
    println("$clientErrors client errors, $severErrors server errors")
}
```
```kotlin
  val responses = listOf("200 ok", "418 i'm a teapot", "500 internal server error")
    printProblemCounts(responses) // 1 client errors, 1 server errors
```
- 코틀린에서는 자바와 달리 람다에서 람다 밖 함수에 있는 파이널이 아닌 변수에 접근할 수 있고 그 변수를 변경할 수 도 있다 
- 람다 안에서 사용하는 외부 변수를 람다가 포획 capture한 변수라고 부른다 
- 기본적으로 함수 안에 정의된 로컬 변수의 생명 주기는 함수가 반환되면 끝난다 
- 하지만 어떤 함수가 자신의 로컬 변수를 포획한 람다를 반환하거나 다른 변수에 저장한다면 로컬 변수의 생명주기와 함수의 생명 주기가 달라질 수 있다 
- 포획한 변수가 있는 람다를 저장해서 함수가 끝난 뒤에 실행해도 람다의 본문 코드는 여전히 포획한 변수를 읽거나 쓸 수 있다
- 어떻게 이가 가능할까?
- 파이널 변수를 포획한 경우에는 람다 코드를 변수 값과 함께 저장 
- 파이널이 아닌 변수를 포획한 경우에는 변수를 특별한 래퍼로 감싸서 나중에 변경하거나 읽을 수 있게 한 다음, 래퍼에 대한 참조를 람다 코드와 함께 저장 
- 변경 가능한 변수 포획하기: 자세한 구현 
  - 자바에서는 파이널 변수만 포획 가능 
  - 교묘한 속임수로 변경 가능한 변수 포획 가능 
  - 그 속임수는 변경 가능한 변수를 저장하는 원소가 단 하나뿐인 배열을 선언하거나
  - 변경 가능한 변수를 필드로 하는 클래스를 선언하는 것 
  - 안에 들어있는 원소는 변경 가능할지라도 배열이나 클래스 인스턴스에 대한 참조를 final로 만들면 포획 가능 
```kotlin
class Ref<T>(var value: T) // 변경 가능한 변수를 포획하는 방법을 보여주기 위한 클래스 
// val counter = Ref(0)
// val inc = { counter.value++ } // 공식적으로는 변경 불가능 변수를 포획했지만 그 변수가 가리키는 객체의 필드 값을 바꿀 수 있다
```
- 실제 코드에서는 이런 래퍼를 만들지 않아도 된다 
- 대신 변수를 직접 바꾼다 
```kotlin
var counter = 0
val inc = { counter++ }
```
- 이 코틀린 코드가 어떻게 작동할까?
- 첫버째 예제는 두번쨰 예제가 작동하는 내부 모습을 보여준다 
- 람다가 파이널 변수 (val)를 포획하면 자바와 마찬가지로 그 변수의 값이 복사된다 
- 하지만 람다가 변경 가능한 변수(var)를 포획하면 변수를 ref 클래스 인스턴스에 넣는다
- 그 ref 인스턴스에 대한 참조를 파이널로 만들면 쉽게 람다로 포획할 수 있고, 람다 안에서는 ref 인스턴스의 필드를 변경할 수 있다 
- 한 가지 꼭 알아둬야 할 것 
  - 람다를 이벤트 핸들러나 다른 비동기적으로 실행되는 코드로 활용하는 경우 함수 호출이 끝난 다음에 로컬 변수가 변경될 수도 있다 
  - 예를 들어 다음 코드는 버튼 클릭 횟수를 제대로 셀 수 없다. 
```kotlin
fun tryToCountButtonClicks(button: Button): Int {
    var clicks = 0
    button.onClick { clicks++ }
    return clicks
}
```
- 이 함수는 항상 0을 반환한다. 
  - onClick 핸들러는 호출될 때마다 clicks의 값을 증가시키지만 그 값의 변경을 관찰할 수는 없다 
  - 핸들러는 tryToCountButtonClicks 가 clicks를 반환한 다음에 호출되기 때문이다. 
  - 이 함수를 제대로 구현하려면 클릭 횟수를 세는 카운터 변수를 함수의 내부가 아니라 
  - 클래스의 프로퍼티나 전역 프로퍼티 등의 위치로 빼내서 나중에 변수 변화를 살펴볼 수 있게 해야 한다. 
```kotlin
// todo : 위에가 무슨 말일까 
```

## 5.1.5 멤버 참조 
- 람다를 사용해 코드 블록을 다른 함수에게 인자로 넘기는 방법을 살펴봤다. 
- 하지만 넘기려는 코드가 이미 함수로 선언된 경우는 어떻게 해야 할까?
- 물론 그 함수를 호출하는 람다를 만들면 된다. 
- 하지만 이는 중복이다. 
- 함수를 직접 넘길 수는 없을까?
- 코틀린에서는 자바 8과 마찬가지로 함수를 값으로 바꿀 수 있다. 
- 이때 이중 콜론 (::) 을 사용한다 
````kotlin
val getAge = Person::age
````
- :: 를 사용하는 식을 멤버참조 member reference라고 부른다. 
- 멤버 참조는 프로퍼티나 메서드를 단 하나만 호출하는 함수 값을 만들어준다. 
- :: 는 클래스 이름과 여러분이 참조하려는 멤버 (프로퍼티나 메서드) 이름 사이에 위치한다. 
-  Person::age 는 `val getAage = { person: Person -> person.age }` 식을 더 간략하게 표현한 것 
- 참조 대상이 함수인지 프로퍼티인지와는 관계 없이 멤버 참조 뒤에는 괄호를 넣으면 안된다 
- 멤버 참조는 그 멤버를 호출하는 람다와 같은 타입이다 
- 따라서 다음 예처럼 그 둘을 자유롭게 바꿔 쓸 수 있다 
```kotlin
peopel.maxBy(Person::age)
people.maxBy { p -> p.age }
people.maxBy { it.age }
```
- 최상위에 선언된 (그리고 다른 클래스의 멤버가 아닌) 함수나 프로퍼티를 참조할 수 있다 
```kotlin
fun salute() = println("Salute!")
// run(::salute) // Salute!! (최상위 함수를 참조한다) 
```
- 클래스 이름을 생략하고 ::로 참조를 바로 시작한다 
- :: salute 라는 멤버 참조를 run 라이브러리 함수에 넘긴다 (run 은 인자로 받은 다른 함수를 호출한다)
- 람
- 람다가 인자가 여럿인 다른 함수한테 작업을 위임하는 경우 람다를 정의하지 않고 직접 위임 함수에 대한 참조를 제공하면 편리하다. 
```kotlin
val action = { person: Person, message: String -> // 이 람다는 sendEmail 함수에게 작업을 위임한다 
  sendEamil(person, message) 
}

val nextAction = ::sendEmail // 람다대신 멤버 참조를 쓸 수 있다 
```
- 생성자 참조 constructor reference 를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다 
  - :: 뒤에 클래스 이름을 넣으면 생성자 참조를 만들 수 있다 
```kotlin
data class Person(val name: String, val age: Int)
// val createPerson = ::Person // "Person" 의 인스턴스를 만드는 동작을 값으로 저장한다
// val p = createPerson("Alice", 29)
// print(p) // Person(name=Alice, age=29)
```
- 확장함수도 멤버 함수와 똑같은 방식으로 참조할 수 있다는 점을 기억해라 
```kotlin
fun Person.isAdult() = age >= 21
val predicate = Person::isAdult
```
- isAdult는 클래스의 멤버가 아니고 확장함수다
  - 그렇지만 isAdult를 호출할 때 person.isAult()로 인스턴스 멤버 호출 구문을 쓸 수 있는 것처럼 
  - Person::isAudult로 멤버 참조 구문을 사용해 이 확장 함수에 대한 참조를 얻을 수 있다 
- 바운드 멤버 참조 
  - 코틀린 1.0에서는 클래스의 메서드나 프로퍼티에 대한 참조를 얻은 다음에 그 참조를 호출할 때 항상 인스턴스 객체를 제공해야 했다 
  - 코틀린 1.1 부터는 바운드 멤버 참조 bound member reference를 지원한다 
  - 바운드 멤버 참조를 사용하면 멤버 참조를 생성할 때 클래스 인스턴스를 함께 저장한 다음에 나중에 그 인스턴스에 대해 멤버를 호출해준다 
  - 따라서 호출시 수신 대상 객체를 별도로 지정해줄 필요가 없다 
```kotlin
val p = Person("Alice", 34)
val personsAgeFunction = Person::age
println(personsAgeFunction(p)) // 34

val dmitryAgeFunction = p.age // 코틀린 1.1 부터 사용할 수 있는 바운드 멤버 참조
println(dmitryAgeFunction()) // 34
```
- 여기서 personsAgeFunction 은 인자가 하나(인자로 받은 사람이 나이를 반환)이지만 
- dmitryAgeFunction는 인자가 없는 (참조를 만들 때 p가 가리키던 사람의 나이를 반환) 함수라는 점에 유의하라 
- 코틀린 1.0에서는 p::age 대신에 { p.age } 라고 직접 객체의 프로퍼티를 돌려주는 람다를 만들어야 한다 

# 5.2 컬렉션 함수형 API 
- 함수형 프로그래밍 스타일을 사용하면 컬렉션을 다룰 때 편리하다 
- 이번 절에서는 컬렉션을 다루는 코틀린 표준 라이브러리 몇가지를 살펴보자

## 5.2.1 필수적인 함수 : filter와 map 
- filter와 map은 컬렉션을 활용할 때 기반이 되는 함수 
  - 대부분의 컬렉션 연산을 이 두 함수를 통해 표현 가능 
```kotlin
data class Person(val name: String, val age: Int)
```
- filter 함수 (필터 함수 또는 걸러내는 함수라고 부름)는 컬렉션을 이터레이션하면서 주어진 람다에 각 원소를 넘겨서 람다가 true를 반환하는 원소만 모은다
```kotlin
val list = listOf(1, 2, 3, 4)
println(list.filter { it % 2 == 0 }) // [2, 4]
```
- 결과는 입력 컬렉션의 원소 중에서 술어 (참/거짓을 반환하는 함수를 술어 predicate라고 한다)를 만족하는 원소만으로 이뤄진 새로운 컬렉션이다. 
- 30살 이상인 사람만 필요하다면 filter를 사용한다 
```kotlin
    val people = listOf(Person("Alice", 45), Person("Bob", 31))
    println(people.filter { it.age > 30 }) // [Person(name=Alice, age=45), Person(name=Bob, age=31)]
```
- filter 함수는 컬렉션에서 원치 않는 원소를 제거한다 
- 하지만 filter는 원소를 변환할 수는 없다. 원소를 변환하려면 map을 사용한다 
- map
- map함수는 주어진 람다를 컬렉션의 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다 
- 다음과 같이 하면 숫자로 이뤄진 리스트를 각 숫자의 제곱이 모인 리스트로 바꿀 수 있다
```kotlin
    val list = listOf(1, 2, 3, 4)
    println(list.map{ it * it }) // [1, 4, 9, 16]
```
- 이름의 리스트 출력 : map으로 사람의 리스트 -> 이름의 리스트 
```kotlin
    val people = listOf(Person("Alice", 45), Person("Bob", 31))
    println(people.map { it.name }) // [Alice, Bob]
```
- 더 간결하게 
```kotlin
people.map(Person::name)
```
- 이런 호출을 쉽게 연쇄 시킬 수 있다 (30살 이상의 사람 이름)
```kotlin
 println(people.filter { it.age > 30 }.map(Person::name))
```
- 이제 이 목록에서 가장 나이가 많은 사람의 이름을 알고 싶을 때 
  - 먼저 목록에 있는 사람들의 나이의 최대값을 구하고 나이가 그 최댓값과 같은 모든 사람 반환하기 
  ```kotlin
  people.filter { it.age == people.maxBy(Person::age)!!.age }
  ```
  - 하지만 이 코드는 목록에서 최대값을 구하는 작업을 계속 반복한다는 단점
    - 100명의 사람이 있다면 100번 최댓값 연산을 수행한다 
  - 다음은 이를 좀 더 개선한 코드
  ```kotlin
    val maxAge = people.maxBy(Person::age)!!.age
    people.filter { it.age == maxAge }
  ```
  - 꼭 필요하지 않는 경우 굳이 계산을 반복하지 말라 
  - 람다를 인자로 받는 함수에 람다를 넘기면 겉으로 볼 대는 단순해 보이는 식이 내부 로직의 복잡도로 인해 실제로는 엄청나게 불합리한 계산식이 될 때가 있다 

- 필터와 변환 함수를 맵에 적용할 수 있다 
```kotlin
val numbers = mapOf(0 to "zero", 1 to "one")
println(numbers.mapValues { it.value.toUpperCase() }) // {0=ZERO, 1=ONE}
println(numbers.mapValues { it.value.uppercase(Locale.getDefault()) }) // {0=ZERO, 1=ONE} toUpperCase() deprecated 되었다
```
- 맵의 경우 키와 값을 처리하는 함수가 따로 존재 
- filterKeys와 mapKeys는 키를 걸러 내거나 변환하고, filterValues와 mapValues는 값을 걸러 내거나 변환한다 

## 5.2.2 all, any, count, find: 컬렉션에 술어 적용
- 컬렉션에 대해 자주 수행하는 연산으로 컬렉션의 모든 원소가 어떤 조건을 만족하는지 판단하는
  - 또는 그 변종으로 컬렉션 안에 어떤 조건을 만족하는 어떤 원소가 있는지 판단하는 연산이 있다 
- 코틀린에서는 all, any 가 이런 연산이다 
- count 함수는 조건을 만족하는 원소의 개수 반환
- find 함수는 조건을 만족하는 첫 번째 원소를 반환
- 나이가 27살 이하인지 판단하는 술어 함수 canBeInClub27
```kotlin
    val canBeInClub27 = {p: Person -> p.age > 27}
```
- 모든 원소가 이 술어를 만족하는지 궁금하다면 all 함수를 쓴다 
```kotlin
    val canBeInClub27 = {p: Person -> p.age > 27}
    val people = listOf(Person("Alice", 27), Person("Bob", 31))
    println(people.all(canBeInClub27)) // false
```
- 술어를 만족하는 원소가 하나라도 있는지 궁금하면 any
```kotlin
  println(people.any(canBeInClub27)) //true
```
- 어떤 조건에 대해 !all을 수행한 결과와 그 조건의 부정에 대해 any를 수행한 결과는 같다 ( 드 모르강의 법칙)
- 어떤 조건에 대해 !any를 수행한 결과와 그 조건의 부정에 대해 all을 수행한 결과도 같다 
- 가독성을 높이려면 any와 all 앞에 !를 붙이지 않는 편이 낫다 
```kotlin
    val list = listOf(1, 2, 3)
    println(!list.all{it == 3})  // true
    /**
     * !를 눈치 채지 못하는 경우가 자주 있다 이럴 땐 any가 더 낫다
     */
    println(list.any{it == 3}) // ture any를 사용하려면 술어를 부정해야 한다 
```
- 술어를 만족하는 원소의 개수를 구하려면 count 사용 
```kotlin
    val people = listOf(Person("Alice", 27), Person("Bob", 31))
    println(people.count(canBeInClub27))
```
- 함수를 적재 적소에 사용하라 count, size 
  - count가 있다는 사실을 잊어버리고 컬렉셜 필터링한 결과의 크기를 가져오는 경우가 있다 
  ```kotlin
      println( people.filter(canBeInClub27).size)
  ```
  - 하지만 이렇게 처리하면 조건을 만족하는 모든 원소가 들어가는 중간 컬렉션이 생긴다 
  - 반면 count는 조건을 만족하는 원소의 개만을 추적하지 조건을 만족하는 원소를 따로 저장하지 x 
  - 따라서 count가 훨씬 효율적 
- 술어를 만족하는 원소를 하나 찾고 싶으면 find 함수를 사용한다 
```kotlin
println(people.find(canBeInClub27)) // Person(name=Bob, age=31)
```
- 이 식은 조건을 만족하는 우너소가 하나라도 있는 경우 가장 먼저 조건을 만족한다고 확인한 원소 반환 
  - 만족하는 원소가 하나도 없는 경우 null을 반환 
  - find는 firstOrNull과 같다 
  - 조건을 만족하는 원소가 없으면 null이 나온다는 사실을 더 명확히 하고 싶다면 firstOrNull을 쓸 수 있다 

## 5.2.3 groupBy 리스트를 여러 그룹으로 이뤄진 맵으로 변경 
- 컬렉션의 모든 원소를 어떤 특성에 따라 여러 그룹으로 나누고 싶을 때 
  - 예를 들어 사람을 나이에 따라 분류 
  - 특성을 파라미터로 전달하면 컬렉션을 자동으로 구분 해주는 함수 -> groupBy
```kotlin
val people = listOf(Person("Alice", 27), Person("Bob", 29), Person("Carol", 31))
println(people.groupBy { it.age }) //{27=[Person(name=Alice, age=27)], 29=[Person(name=Bob, age=29)], 31=[Person(name=Carol, age=31)]}

```
- 연산의 결과는 컬렉션의 원소를 구분하는 특성 (이 예제에서는 age)이 키이고, 키 값에 따른 각 그룹이 값인 맵이다 
- 각 그룹은 리스트다. 따라서 groupBy의 결과 타입은 Map<Int, List<Person>>이다 
- 필요하면 이 맵을 mapKeys나 mapValues 등을 사용해 변경할 수 있다 
- 다른 예로 멤버 참조를 활용해 문자열을 첫 글자에 따라 분류하는 코드를 보자 
```kotlin
val list = listOf("a", "ab", "b")
println(list.groupBy(String::first)) //{a=[a, ab], b=[b]}
```
- first는 string의 멤버가 아니라 확장함수지만 여전히 멤버 참조를 사용해 first에 접근할 수 있다 

## 5.2.4 flatMap과 flatten: 중첩된 컬렉션 안의 원소 처리 
```kotlin
class Book(val title: String, val authors: List<String>)
```
- 도서관에 있는 책의 저자를 모두 모은 집합 
```kotlin
    books.flatMap { it.authors }.toSet() // books 컬렉션에 있는 책을 쓴 모든 저자의 집합 
```
- flatMap 함수는 먼저 인자로 주어진 람다를 컬렉션 객체에 적용하고 (또는 매핑 map)
  - 람다를 적용한 결과 얻어지는 여러 리스트를 한 리스트로 한데 모은다 (또는 펼치기 flatten)
```kotlin
  val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() }) // [a, b, c, d, e, f]

```
- map을 해서 [ a, b, c, ], [c, d, e] -> flatten 해서 [a, b, c, d, e, f]
  - toList 함수를 문자열에 적용하면 그 문자열에 속한 모든 문자로 이뤄진 리스트가 만들어진다 
  - flatMap함수는 다음 단계로 리스트의 리스트에 들어있던 모든 우너소로 이뤄진 단일 리스트를 반환 
```kotlin
  val books = listOf(
        Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort", listOf("Terry Pratchett")),
        Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman"))
    )
    println(books.flatMap { it.authors }.toSet()) // [Jasper Fforde, Terry Pratchett, Neil Gaiman]
    println(books.flatMap { it.authors }) // [Jasper Fforde, Terry Pratchett, Terry Pratchett, Neil Gaiman]
```
- 리스트의 리스트가 있는데 모든 중첩된 리스트의 원소를 한 리스트로 모아야 한다면 flayMap을 떠올릴 수 있을 것이다 
- 하지만 특별히 변환해야 할 내용이 없다면 리스트의 리스트를 평평하게 펼치기만 하면 된다 
- 그런경우 listOfLists.flatten() 처럼 flatten 함수를 사용할 수 있다 
- 자
- 컬렉션을 다루는 코드를 작성할 경우에는 원하는 바를 어떻게 일반적인 변환을 사용해 표현할 수 있는지 생각해보고 그런 변환을 제공하는 라이브러리 함수가 있는지 살펴보라 
- 대부분 찾을 수 있고 직접 코드 구현보다 빨리 문제 해결할 수 있다 

# 5.3 지연 계산 (lazy) 컬렉션 연산 
- 앞 절에서 살펴본 filter, map은 연산 결과 컬렉션을 즉시 eagerly 생성한다 
- 이는 컬렉션 함수를 연쇄하면 매 단계마다 계산 중간 결과를 새로운 컬렉션에 임시로 담는 다는 말 
- 시퀀스 sequence 를 사용하면 중간 임시 컬렉션을 사용하지 않고도 컬렉션 연산을 할 수 있다 
```kotlin
    println(people.map(Person::name).filter { it.startsWith("A") })
```
- 코틀린 표준 라이브러리 참조 문서에서는 filter와 map이 리스트를 반한한다고 써있다 
- 이는 이 연쇄 호출이 리스트를 2개 만든다는 뜻 
  - 한 개는 filter의 결과를 
  - 다른 하나는 map의 결과를 담는다 
- 원소가 수백만개가 되면 효율 떨어짐 
- 이를 더 효율적으로 만들기 위해 각 연산이 컬렉션을 직접 사용하는 대신 시퀀스를 사용하게 만들어야 한다 
```kotlin
 people.asSequence() // 원본 컬렉션을 시퀀스로 변환 
        .map(Person::name) // 시퀀스도 컬렉션과 똑같은 API 제공
        .filter { it.startsWith("A") }
        .toList() // 시퀀스 결과를 다시 리스트로 변환 
```
- 중간 결과를 저장하는 컬렉셔닝 생기지 않아 원소가 많으면 성능이 눈에 띄게 좋아진다 
- 코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다 
  - 이 인터페이스는 단지 한 번에 하나씩 열거될 수 이쓴ㄴ 원소의 시퀀스를 표현할 뿐 
  - Sequence 안에는 iterator라는 단 하나의 메서드가 있다 
  - 그 메서드를 통해 시퀀스로부터 원소 값을 얻을 수 있다 
- Sequence 인터페이스 강점은 그 인터페이스 위해 구현된 연산이 계산을 수행하는 방법 때문에 생긴다 
- 시퀀스의 원소는 필요할 때 비로소 계산된다 
- 따라서 중간 처리결과를 저장하지 않고도 연산을 연쇄적으로 적용해서 효율적으로 계산을 수행할 수 있다
- asSequence 확장함수를 호출하면 어떤 컬렉션이든 시퀀스로 바꿀 수 있다 
- 시퀀스를 리스트로 만들때는 toList사용 
- 왜 시퀀스를 다시 컬렉션으로 되돌려야 할까?
  - 시퀀스의 원소를 차례대로 이터레이션 해야 한다면 시퀀스를 직접 써도 된다 
  - 하지만 시퀀스 원소를 인덱스를 통해 접근하는 등 다른 API 메서드가 필요하다면 시퀀스를 리스트로 변환해야 한다 
- 큰 컬렉션에 대해서 연산을 연쇄시킬 때는 시퀀스를 사용하는 것을 규칙으로 삼아라 
  - 8.2 절에서는 중간 컬렉션을 생성함에도 불구하고 코틀린에서 즉시 계산 컬렉션에 대한 연산이 더 효율적인 이유를 설명 
  - 하지만 컬렉션에 들어있는 원소가 많으면 중간 원소를 재배열하는 비용이 커지기 때문에 지연 계산이 더 낫다 

## 5.3.1 시퀀스 연산 실행: 중간 연산과 최종 연산
- 시퀀스에 대한 연산은 중간 intermediate 연산과 최종 terminal 연산으로 나뉜다 
- 중간 연산은 다른 시퀀스를 반환
- 시퀀스는 최초 시퀀스의 원소를 변환하는 방법을 안다 
- 최종 연산은 결과를 반환 
- 결과는 최초 컬렉션에 대해 변환을 적용한 시퀀스로부터 계산을 수행해 얻을 수 있는 컬렉션이나 원소, 숫자, 또는 객체다 
- 중간 연산은 항상 지연 계산된다 
```kotlin
   listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter ($it) "); it % 2 == 0 } 
```
- 이 코드를 실행하면 아무 내용도 출력 x 
  - map과 filter 변환이 늦춰져서 결과를 얻을 필요가 있을 때 (즉 최종 연산이 호출될 때) 적용된다는 뜻 
```kotlin
   listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter ($it) "); it % 2 == 0 } 
  .toList()
```
- 최종 연산을 호출하면 연기됐던 모든 계산 수행 
```
map(1) filter (1) map(2) filter (4) map(3) filter (9) map(4) filter (16) 
```
- 이 예제에서 수행 순선를 잘 알아둬야 한다 
  - 직접 연산 구현시 map 함수를 각 원소에 대해 먼저 수행해서 새 시퀀스 얻고 그 시퀀스에 다시 filter수행 
  - 하지만 그렇지 않은 결과 (위) 
  - 시퀀스의 경우 모든 연산은 각 원소에 대해 순차적 적용 
    - 첫 번째 원소가 처리되고 (변환된 다음에 걸러지고) 두번째 원소가 처리 
- 따라서 원소에 연산을 차례대로 적용하다가 결과가 얻어지면 그 이후의 원소에 대해서는 변환이 이뤄지지 않을 수도 있다 
```kotlin

    println(listOf(1, 2, 3, 4).asSequence()
        .map { it * it }.find{ it > 3}) // 4
```
- 컬렉션을 사용하면 리스트가 다른 리스트로 변환된다 
  - 그래서 map 연산은 3과 4를 포함해 모든 원소를 변환. 그후 find 술어를 만족하는 첫 번째 원소인 4를 찾는다 
- 시퀀스를 사용하면 find 호출이 원소를 하나씩 처리하기 시작 
  - 최조 시퀀스로부터 수를 하나 가져와서 map 에 지정된 변환을 수행하고 
  - 다음에 find에 지정된 술어를 만족하는지 검사 
  - 최초 시퀀스에서 2를 가져오면 제곱 값 이 3보다 커져 그를 반환 4
  - 3, 4는 처리 안함 
- 컬렉션에 대해 수행하는 연산의 순서도 성능에 영향 
  - 사람의 컬렉션이 있는데 이름이 어떤 길이보다 짧은 사람의 명단을 얻고 싶을 때 
  - 이를 위해 각 사람을 이름으로 map한 다음에 이름 중에서 길이가 긴 사람을 제외 
  - 이런 경우 map과 filter를 어떤 순서로 실행해도 된다 
  - 그러나 map 다음에 filter를 하는 경우와 filter 다음에 map을 하는 경우 결과는 같아도 수행해야 하는 변환의 전체 횟수는 다르다 
    - map을 먼저 하면 모든 원소를 변환 
    - 하지만 filter를 먼저하면 부적절한 원소를 먼저 제외하기 때문에 그런 원소는 변환되지 않는다 
- 자바 스트림과 코틀린 스퀀스 비교
  - 자바 8 스트림은 코틀린 시퀀스와 같다 
  - 코틀린에서 같은 개념 따로 구현하는 이유 안드로이드 등에서 예전 버전 자바를 사용하는 경우 자바 8의 스트림이 없어서 
  - 자바 8을 채택하면 현재 코틀린 컬렉션, 시퀀스에서 제공하지 않는 중요한 기능 사용 가능 
  - 바로 스트림연산 (map, filter 등)을 여러 CPU에서 병렬적으로 실행하는 기능 
  - 여러분이 필요와 사용할 자바 버전에 따라 시퀀스, 스트림 중 적절한 쪽 선택 




































































