# 2.1 기본 요소: 함수와 변수

## 2.1.1 Hello, World!
```kotlin
fun main(args: Array<String>) {
    println("Hello World!")
}
```
- 함수를 사용할 때 fun 키워드 사용 
- 파라미터 타입을 쓸 때 이름 뒤에 그 파라미터 타입을 쓴다. 변수를 선언할 때도 마찬가지
- 함수를 최상위 수준에 정의 가능 
  - 자바와 달리 꼭 클래스 안에 함수를 넣어야 할 필요가 없다 
- 배열로 일반적인 클래스와 마찬가지
  - 배열 처리를 위한 문법이 따로 존재하지 않는다 
- System.out.println 대신에 println이라고 쓴다 
  - 코틀린 표준 라이브러리는 여러가지 표준 자바 라이브러리 함수를 간결하게 사용할 수 있게 감싼 래퍼 wrapper를 제공
  - println도 그 중 하나 
- 최신 프로그래밍 언어 경향과 마찬가지로 줄 끝에 세미콜론; 을 붙이지 않아도 좋다

## 2.1.2 함수 
```kotlin
fun max(a: Int, b: Int) : Int {
     return if (a > b) a else b
 }
println(max(1,2))
```
- 문 statement와 식 expression의 구분 
  - 코틀린에서 if 는 식이지 문이 아니다 
  - 식은 값을 만들어내며 다른 식의 하위 요소로 계산에 참여할 수 있는 반면
  - 문은 자신을 둘러싼 가장 안쪽 블록의 최상위 요소로 존재하며 아무런 값을 만들어내지 않는다
  - 자바에서는 모든 제어 구조가 문인 반면
  - 코틀린에서는 루프를 제외한 대부분 제어 구조가 식이다
  - 제어 구조를 다른 식으로 엮어 낼 수 있으면 여러 일반적 패턴을 아주 간결하게 표현 가능
  - 대입문은 자바에서는 식이었으나 코틀린에서는 문
    - 그로인해 자바와 달리 대입식과 비교식을 잘못 바꿔 써서 버그 생기는 경우 많다 

### 식이 본문인 함수 

- 위의 식을 더 간결하게 표현 가능 
```kotlin
fun max(a: Int, b: Int) : Int = if (a > b) a else b
```
- 식/블록 본문
  - 본문이 중괄호로 둘러싸인 함수를 **블록 본문인 함수**  라고  부르고 
  - 등호와 식으로 이뤄진 함수를 **식이 본문이 함수**라고 부른다 
  - 인텔리제이는 이 두방식의 함수를 서로 변환하는 메뉴가 있다 
    - convert to expression body 식 본문으로 변환
    - convert to block body 블록 본문으로 변환 
  - 코틀린에서는 식이 본문인 함수가 자주 쓰인다

- 반환 타입 생략
```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
```
- 식이 본문일 경우 굳이 사용자가 반환 타입 적지 ㅇ낳아도 컴파일러가 함수 본문 식을 분석해서 식의 결과 타입을 함수 반환 타입으로 정해준다 
- 식이 본문인 함수의 반환 타입만 생략 가능하다는 
점에 유의 
  - 블록이 본무인 함수가 값을 반환한다면 반드시 반환 타입을 지정하고 return 사용해 반환 값을 명시해야 한다
  - 실전 프로그램에서는 아주 긴 함수에 return 문이 여럿 들어 있는 경우가 자주 있다 그런 경우 반환 타입을 꼭 명시하고 return을 반드시 사용한다면 함수가 어떤 타입의 값을 반환하고 어디서 그런 갓을 변환하는지 더 쉽게 알아볼 수 있다  

## 2.1.3 변수 
- 코틀린에서 타입 지정을 생략하는 경우가 흔해 타입으로 변수 선언을 시작하면 타입을 생략할 경우 식과 변수 선언을 구별할 수가 없다.
- 그래서 코틀린에서는 키워드로 변수 선언을 시작하는 대신 변수 이름 뒤에 타입을 명시하거나 생략하게 허용 
```kotlin
val question = "질문"
val answer = 42 
val answer: Int = 42 // 타입 명시 가능
val yearsToCompute = 7.5e6 // 부동 소수점 상수를 사용하면 변수 타입은 double

val answer: Int
answer = 42 // 초기화 식을 사용하지 않고 변수를 선언하려면 반드시 변수 타입 명시
```

### 변경 가능한 변수와 불가능한 변수 
- val, var
  - val(값을 뜻하는 value에서 따옴)
    - 변경 불가능한 immutable 참조를 저장하는 변수 
    - val로 선언된 변수는 초기화하고 나면 재대입 불가능 
    - 자바로치면 final
  - var (변수를 뜻하는 variable에서 따옴)
    - 변경 가능한 참조 mutable
- 기본적으로 모든 변수를 val키워드 사용해 불변 변수로 선언하고 나중에 꼭 필요할 때만 var로 변경
  - 변경 불가능한 참조와 변경 불가능한 객체를 부수 효과가 없는 함수와 조합해 사용하면 코드가 함수형에 가까워진다
- val 변수는 블록을 실행할 때 정확히 한 번만 초기화 되어야 한다 
  - 하지만 어떤 블록이 실행될 때 오직 한 초기화 문장만 실행됨을 컴파일러가 확이할 수 있으면 조건에 따라 val 값을 다른 여러 값으로 초기화 가능 
  ```kotlin
  val message: String
  if (canPerformOperation()) {
      message = "Success"
  }
  else {
   message = "Failed"
  }
  ```
- val 참조 자체는 불변이라도 그 참조가 가리키는 객체의 내부 값은 변경될 수 있다 
- var 키워드를 사용해도 변수 타입은 고정돼 바뀌지 않는다 
  - 컴파일러는 변수 선언 시점의 초기화 식으로부터 변수 ㅌ입을 추론하며, 변수 선언 이후 변수 재대입이 이뤄질 때는 이미 추론한 변수의 타입을 염두에 두고 대입문의 타입을 검사한다
```kotlin
var answer = 42 
answer = "no answer" // type mismatch 컴파일 오류 발생
```
- 어떤 타입의 변수에 다른 타입에 값을 저장하고 싶다면 변환 함수를 써서 값을 변수의 타입으로 변환하거나 값을 변수에 대입할 수 있는 ㅌ아ㅣㅂ으로 강제 형변화 해야 한다

## 2.1.4 더 쉽게 문자열 형식 지정: 문자열 템플릿 
```kotlin
fun main(args: Array<String>) {
  val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello $name!")
}
```
- 변수를 문자열 안에 사용하는 법
  - 문자열의 리터럴의 필요한 곳에 변수를 넣되 변수 앞에 $추가 
- $ 문자를 문자열에 넣고 싶으면 
  - println("\$x")
- 복잡한 식은 중괄호({}) 로 둘러싸서 넣을 수 있다 
```kotlin
fun main(args: Array<String>) {
    println("Hello ${args[0]}!")
}
```
- 한글을 문자열 템플릿에서 주의할 점 
  - 변수 이름은 영어고, 또 안에 문자는 한글이기 때문에 영문자와 한글을 한번에 식별자로 인식해 오류 날 수 있다 
  - 중괄호 {} 로 감싸서 넣어준다 
    - "${name}님 반가와요!"
- 중괄호로 둘러싼 식 안에서 큰 따옴표를 사용 가능 
```kotlin
fun main(args: Array<String>) {
    println("Hello ${if (args.size > 0) args[0] else "someone"}!")
}
```

# 2.2 zmffotmdhk vmfhvjxl 
```java

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

```
- 1.5.6에서의 자바 코틀린-변환기 이용하면 
```kotlin
class Person(val name: String)
```
- 이런 유형의 클래스(코드가 없이 데이터만 저장하는 클래스)를 값 객체 valu object 라 부른다 
- public 이 사라졌다 코트린 기본 가시성이 public이라 생략 나으 

## 2.2.1 프로퍼티 
- 자바에서는 필드와 접근자를 하데 묶어 프로퍼티라 부른다 
- 코틀린은 프로퍼티를 언어 기본으로 제공하고 코틀린 프로퍼티는 자바의 필드와 접근자 메서드를 완전히 대신 
```kotlin
class Person(
  val name: String,
  var isMarried: Boolean
)
```
- 코틀린에서 프로퍼티를 선언하는 방식은 프로퍼티과 관련 있는 접근자를 선언
  - val(읽기전용), var(게터+세터)
```java
Person person = new Person("Bob", true);
System.out.println(person.getName());
System.out.println(person.isMarried());
```
```kotlin
val person = Person("Bob", true)
println(person.name)
println(person.isMarried)
```
- 게터를 사용하는 대신 프로퍼티를 직접 사용 

## 2.2.2 커스텀 접근자 
- 대부분의 플퍼티에는 그 프로퍼티 값을 저장하기 위한 필드가 있다 
- 이를 프로퍼티를 뒷받침하는 필드 backing field라 부른다 
- 하지만 원한다면 프로퍼티 값을 그 때 그 때 계산할 수도 있다 커스텀 게터를 작성하면 그런 프로퍼티를 만들 수 있다
```kotlin
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() { //프로퍼티 게터 선언
            return height == width
        }
} 
```
- isSquare 프로퍼티에는 자체 값을 저장하는 필드가 필요 없다
- `get() = height == width` 로 간결하게 해도 된다 
```kotlin
fun main(args: Array<String>) {
    println("Hello World!")

    val rectangle = Rectangle(41, 43)
    println(rectangle.isSquare)
}
```
- 파라미터 없는 함수 vs 커스텀 게터 
  - 차이는 가독성 뿐
  - 일반적으로 클래스의 특성을 정의하고 싶다면 프로퍼티로 그 특성을 정의해야 한다 


## 2.2.3 코틀린 소스코드 구조: 디렉터리와 패키지 
- 자바의 경우 모든 클래스를 패키지 단위로 관리 
- 코틀린도 package문을 넣을 수 있다 
  - 같은 패키지에 속했다면 다른 파일에서 정의한 선언일지라도 직접 사용 
  - 다른 패키지에 정의한 선언을 사용하려여 임포트 필요 
- 코틀린에서 함수 임포트와 클래스 임포트 차이가 없으며 모든 선언을 import키워드로 가져올 수 있다. 최상위 함수는 그 이름을 써서 임포트 가능 
- 패키지 이름 뒤에 .* 를 추가하면 패키지 안의 모든 선언을 임포트 가능 
  - star import
  - 모든 클래스 뿐 아니라 최상위에 저장된 함수나 프로퍼티 까지도 모두 불러온다 
- 코틀린에서는 여러 클래스를 한 파일에 넣을 수 있고 파일의 이름도 마음대로 정할 수 있다 
- 코틀린에서는 어느 디렉터리에 소스코드 파일을 위치시키든 관계 없다 
  - 패키지 구조와 디렉토리 구조가 맞아 떨어질 필요는 없다 
- 하지만 대부분 자바와 같이 패키지별로 디렉터리를 구성하는 편이 낫다 
- 하지만 여러 클래스를 한 파일에 넣는 것을 주저하지 마라. 

# 2.3 선택 표현과 처리: enum과 when 

## 2.3.1 enum 클래스 정의 
```kotlin
//프로퍼티와 메서드가 있는 enum클래스 선언하기
enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0,0), ORANGE(255, 165, 0), YELLOW(255, 255, 0), 
    GREEN(0, 255, 0), BLUE(0, 0, 255), 
    INDIGO(75, 0, 130), VIOLET(238, 130, 238); // 여긴 반드시 세미콜론 ; 을 사용해야 한다 
    
    fun rgb() = (r * 256 + g) * 256 + b
}

```
- enum안에 메서드를 정의하는 경우 반드시 enum 상수 목록과 메서드 정의 사이에 세미콜론을 너허야 한다. 

## 2.3.2 when 으로 enum 클래스 다루기 
- 자바의 switch에 해당하는 코틀린 요소는 when
- if와 마찬가지로 값을 만들어내는 식이다 when도 
```kotlin
 fun getMnemonic(color: Color) = 
        when(color) {
            RED -> "Richard"
            ORANGE -> "Of"
            YELLOW -> "York"
            else -> "Else"
        }
```
- 모든 enum요소를 정의했다면 else 안넣어도 된다 (나 : 아마)
- 자바와 달리 각 분기의 끝에 break를 안넣어도 된다. 
```kotlin
//한 when 분기 안에 여러 값 사용하기, 상수값 import 시에 Color 생략 가능 
    fun getWarmth(color: Color) = when(color) {
        Color.RED, Color.ORANGE, YELLOW -> "warm"
        Color.GREEN -> "natural"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
    }
```

## 2.3.3 when과 임의의 객체를 함께 사용 
- when은 switc보다 훨씬 강력 : 분기 조건에 상수만 오는 switch와 달리 코틀린의 when은 객체가 올 수 있다
```kotlin
 fun mix(c1: Color, c2: Color) =
        when(setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }
```
- 그 객체들을 포함하는 집합인 set으로 만드는 setof 함수
- 객체 비교시 동등성 equility 사용 
- 모든 조건 뒤졌을 때 못찾으면 else 


## 2.3.4 인자 없는 when 사용 
- 바로위 함수는 호출 될 때마다 함수 인자로 주어진 두 색이 when의 분기 조건에 있는 다른 두 색과 같은지 비교하기 위해 여러 set인스턴스 생성 
- 불필요한 가비지 객체 
- 인자 없는 when을 사용하면 불필요한 객체 생성 막을 수 있다 
- 코드는 약간 읽기 어려워짐
```kotlin
   fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == RED && c2 == ORANGE) || (c1 == ORANGE && c2 == RED) ->
                ORANGE
            // ... 
            else -> throw Exception("Dirty color")
        }
```

## 2.3.5 스마트 캐스트: 타입 검사와 타입 캐스트를 조합 
```kotlin
fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }
    
    throw IllegalArgumentException("Unknown expression")
}
// println(eval(Sum(Sum(Num(1), Num(2)), Num(4)))
```
- 코틀린에서는 is를 사용해 변수 타입을 검사한다. 
  - 자바의 instanceOf와 비슷하다 
  - 코틀린에서는 is로 검사하면 마치 원래 그 타입인 것처럼 자동 컴파일 해준다. 
  - 컴파일러가 캐스팅 해주는 것  
  - 이를 스마트 캐스트라고 부른다 
  - 클래스의 프로퍼티에 대해 스마트 캐스트를 사용한다면 그 프로퍼티는 반드시 val이어야 하며 커스텀 접근자를 사용한 것이어도 안 된다
  - 원하는 타입으로 명시적으로 타입 캐스팅 하려면 as키워드를 사용한다 

## 2.3.4 리팩토링: if 를 when으로 변경 
```kotlin
fun eval(e: Expr): Int =
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval(e.right) + eval(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }

```
- 코틀린에서는 if 가 값을 만들어 내기 때문에 가능한 것 
```kotlin
fun eval(e: Expr): Int =
    when (e) {
        is Num ->
            e.value
        is Sum ->
            eval(e.right) + eval(e.left)
        else ->
            throw IllegalArgumentException("Unknown expression")
    }

```
- when 식을 값 동등성 말고도 이것처럼 분기로 쓸 수 있다
- is Num 하면 스마트 캐스트가 이뤄진다 

## 2.3.7 if와 when의 분기에서 블록 사용
