4. 클래스, 객체, 인터페이스 

- 코틀린 클래스와 인터페이스는 자바와는 약간 다르다 
- 인터페이스에 프로퍼티 선언이 들어갈 수 있다 
- 코틀린 선언은 기본적으로 final, public 이다 
- 중첩 클래스는 기본적으로 내부 클래스가 아니다 
  - 중첩 클래스에는 외부 클래스에 대한 참조가 없다 

# 4.1 클래스의 계층 정의 
- 인터페이스를 구현할 때 `override` 키워드 꼭 써줘야 함 
  - 자바에서는 @override 붙이면 체크해 줬잖아
  - 코틀린은 필수로 가져가서 실수로 상위 메서드를 오버라이드하는 방지해준다
  - 상위 클래스에 있는 메서드와 우연히 시그니처가 같은 메서드를 하위 클래스에서 선언하는 경우 컴파일이 안 되기 때문에 override를 붙이거나 메서드 이름을 바꿔야 한다
- 인터페이스메서드도 디폴트 구현을 제공할 수 있다 
  - 자바에서는 default를 붙여야 했는데 코틀린은 그럴 필요 없
```kotlin
package ch04

interface Clickable {
    fun click()
    fun showoff() = println("I'm clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")

    fun showoff() = println("I'm Focusable!")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("I'm clicked!")
    }

    override fun showoff() {
        /*** 이름과 시그니처가 같은 멤버 메서드에 둘 이상의 디폴트 구현이 있는 경우
        인터페이스를 구현하는 하위 클래스를 명시적으로 새로운 구현을 제공해야 한다***/
        super<Clickable>.showoff()
        /*** 상위 타입의 이름을 꺽쇠 괄호 <> 사이에 넣어서 super를 지정하면
        어떤 상위타입의 멤버 메서드를 호출할지 알 수 있다 ***/
        super<Focusable>.showoff()
    }
}
```
## 4.1.2 open, final, abstract 변경자: 기본적으로 final
- 왜 기본으로 상속을 막아 뒀을까?
  - 취약한 기반 클래스 라는 문제 fragile base class 
  - 어떤 클래스가 자신을 상속하는 방법에 대해 정확한 규칙을 제공하지 않으면 그 클래스 클라이언트는 기반 클래스를 작성한 사람의 의도와 다르게 오버라이드 할 수 있다
  - 모든 하위 클래스를 분석하는 것은 불가능하니까 기반 클래스를 변경하는 경우 하위 클래스 동작이 예기치 않게 바뀔 수 있고 그래서 기반 클래스는 '취약' 하다
  - 이펙티브자바에서는 상속을 위한 설계 문서를 갖추거나 그렇지 않으면 상속 금지하라고 한다 
    - 하위 클래스에서 오버라이드하게 의도된 클래스, 메서드 아니라면 final 로 모두 만들라는 뜻 
- 그 철학에서 코틀린은 클래스와 메서드가 기본적으로 final 
- 어떤 클래스 상속을 허용하려면 open 붙여야 한다 (메서드도, 프로퍼티)
```kotlin
package ch04

open class RichButton : Clickable{ // 이 클래스는 열려 있다. 다른 클래스가 이를 상속 가능 
    fun disable() {} // 이 함수는 파이널. 하위 클래스가 오버라이드 불가 
    open fun animate() {} // 이 함수는 열려 있다.
    override fun click() {} // 이 함수는 열려 있는 메서드를 오버라이드. 오버라이드한 메서드는 기본적으로 열려 있다 
}

```
- 오버라이드하는 메서드 앞에 final 붙이면 하위에서 오버라이드 못₩
```kotlin
package ch04

open class RichButton : Clickable{ // 이 클래스는 열려 있다. 다른 클래스가 이를 상속 가능 
    fun disable() {} // 이 함수는 파이널. 하위 클래스가 오버라이드 불가 
    open fun animate() {} // 이 함수는 열려 있다.
    override fun click() {} // 이 함수는 열려 있는 메서드를 오버라이드. 오버라이드한 메서드는 기본적으로 열려 있다 (아까 인터페이스도 마찬가지)
    open fun stopAnimating() {} // 추상클래스에 속해도 비추상 함수는 기본적으로 final 이지만 원한다면 open 으로 할 수 있다
    fun animateTwice() {}
}

```

## 4.1.3 가시성 변경자: 기본적으로 공개 
- 아무 변경자도 없는 경우 모두 public (디폴트가 public)
- 자바의 패키지 전용 package-private 은 코틀린에는 없다
- 대신 코틀린에는 internal : 모듈 내부에서만 볼 수 있다 
  - 모듈 = 한 번에 컴파일 되는 코틀린 파일 
- 자바에서는 패키지가 같은 클래스를 선언하기만 하면 어떤 프로젝트 외부에 있는 코드라도 패키지 내부에 있는 패키지 전용 선언에 쉽게 접근 가능해 모듈의 캡슐화가 쉽게 깨진다
- 다른 차이는 코틀린에서는 최상위 선언에 대해 private 허용 (클래스, 함수, 프로퍼티)
- public : 클래스 멤버) 모든 곳에서 볼 수 있다 / 최상위 선언) 모든 곳에서 볼 수 있다 
- internal : 클래스 멤버) 같은 모듈에서만 볼 수 있다 / 최상위 선언) 같은 모듈 안에서만 볼 수 있다 
- protected : 클래스 멤버) 하위 클래스 안에서만 볼 수 있다 / 최상위 선언 적용 불가
- private : 클래스 멤버) 같은 클래스 안에서만 볼 수 있다 / 최상위 선언) 같은 파일 안에서만 볼 수 있다 
```kotlin
TODO() // 위에 최상위 선언 이라는 말이 이해가 안갑니다
```

```kotlin
internal class TalkativeButton : Focusable {
  private fun yell() = println("HEY!")
  protected fun whisper() = println("Lets' talk!") // 'protected' visibility is effectively 'private' in a final class 
}

fun TalkativeButton.giveSpeech() {
  yell() // Cannot access 'yell': it is private in 'TalkativeButton'
  whisper() // Cannot access 'whisper': it is protected in 'TalkativeButton'
}
```
- public 함수인 giveSpeech() 안에서 그 보다 가시성이 더 낮은 (이 경우 internal) TalkativeButton 을 참조하지 못하게 한다
- 어떤 클래스의 기반 타입 목록에 들어있는 타입이나 제네릭 클래스의 타입 파라미터에 들어있는 ㅌ아ㅣㅂ의 가시성은 그 클래스 자신의 가시성과 같거나 더 높아야 하고
- 메서드의 시그니처에 사용된 모든 타입의 가시성은 그 메서드의 가시성과 같거나 높어야 하는 일반적인 규칙에 해당한다
- giveSpeech 의 가시성을 internal 로 낮추거나 TalkativeButton 을 open으로 해야 함 
- protected 멤버는 오직 어떤 클래스나 그 클래스를 상속한 클래스 안에서만 보인다. 
- 클래스를 확장한 함수는 해당 클래스의 private이나 protected 멤버에 접근할 수 없다 

## 4.1.4 내부 클래스와 중첩된 클래스 : 기본적으로 중첩된 클래스
- 코틀린에서도 클래스 안에 다른 클래스 선언 가능
- 도우미 클래스 캡슐화 가능 
- 코드 정의를 코드 사용하는 곳에 가까이 두는 것 가능 
- 자바와 차이는 코틀린의 중첩 클래스 nested class 는 명시적으로 요청하지 않는 한 바깥 클래스 인스턴스에 대한 접근 권하니 없다 
- 코틀린 중첩 클래스에 아무 변경자가 붙지 않으면 자바 static 중첩 클래스와 같다 
- 이를 바깥쪽 클래스에 대한 참조를 포함하게 하고 싶으면 inner 변경자를 붙여야 한₩
```kotlin
package ch04

class Outer {
  inner class Inner {
    fun getOuterReference(): Outer = this@Outer
  }
}

```
- this@Outer 를 써야 바깥 클래스 참조에 접근 가능
- 유용성 : 클래스 계층을 만들 되 그 계층에 속한 클래스의 수를 제한하고 싶을 때 

## 4.1.4 봉인된 클래스 : 클래스 계층 정의시 계층 확장 제한 
- 상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스를 제한할 수 있다 
- when 쓸 때 else 안써도 되서 when 이 모든 경우를 고려 못할 가능성 (else로 퉁침) 을 방지 할 수 있다
```kotlin
package ch04

sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(e: Expr): Int =
    when (e) {
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left)
    }
```

# 4.2 뻔하지 않은 생성자와 프로퍼티를 갖는 클래스 선언
- 코틀린은 주 생성자와, 부 생성자를 구분 
- 주 생성자 primary : 보통 주 생성자는 클래스를 초기화할 때 사용하는 간략한 생성자, 클래스 본문 밖에서 정의
- 부 생성자 secondary : 클래스 본문 안에서 정의 

## 4.2.1 클래스 초기화 : 주 생성자와 초기화 블록 
```kotlin
class User(val nickname: String)
```
- 클래스 이름 뒤에 오는 괄호로 둘러싸인 코드를 주 생성자라고 부른다 
- 주 생성자는 생성자 파라미터를 지정하고 그 생성자 파라미터에 의해 초기화되는 프로퍼티를 정의하는 두 가지 목적에 쓰인다
```kotlin
class User constructor(_nickname: String){
    val nickname: String
    init {
        nickname = _nickname
    }
}
```
- constructor 키워드는 주 생성자나 부 생성자를 정의를 시작할 때 사용한다 
- init키워드는 초기화 블록을 시작한다
- 초기화 블록에는 클래스의 객체가 만들어 질 때 실행될 초기화 코드가 들어간다 
- 초기화 블록은 주 생성자와 함께 사용된다. 주 생성자는 제한적이라 별도의 코드를 포함할 수 없어 초기화 블록이 필요하다 
- 생성자 파라미터 _nickname에서 맽 앞의 밑줄 _ 은 프로퍼티와 생성자 파라미터를 구분해준다 
- 이 예제에서는 닉네임 프로퍼티를 초기화하하는 코드를 nickname 프로퍼티 선언에 포함시킬 수 있어 코도를 초기화 블록에 넣을 필요 없다 
- 주 생성자 앞에 별다른 애노테이션이나 가시성 변경자가 없다면 constructor 생략 가능 
```kotlin
class User(_nickname: String) {
    val nickname = _nickname
}
```
- 프로퍼티를 초기화하는 식이나 초기화 블록 안에서만 주 생성자의 파라미터를 참조할 수 있다 
- 주 생성자의 파라미터로 프로퍼티를 초기화 하고 싶다면 주 생성자의 파라미터 이름 앞에 val을 추가하면 된다. 
```kotlin
class User(val nickname: String)
```
- 디폴트 값 정의 가능 
```kotlin
class User(val nickname: String,
val isSubscribed: Boolean = true)
```
- 클래스의 인스턴스를 만들려면 new 키워드 없이 생성자 직접 호출하면 된다 
- 모든 생성자 파라미터에 디폴트 값을 지정하면 컴파일러가 자동으로 파라미터가 없는 생성자를 만들어 준다 
- 그렇게 자동으로 만들어진 파라미터 없는 생성자는 디폴트 값을 사용해 클래스를 초기화한다
- 의존관계 주입 di 프레임워크 등 자바 라이브러리 중에는 파라미터가 없는 생성자 통해 객체를 생성해야만 라이브러리 사용이 가능한 경우가 있는데 
- 이 때 코틀린이 제공하는 파라미터 없는 생성자가 그런 라이브러리와 통합을 쉽게 해준다
- 
- 클래스에 기반 생성자가 있다면 주 생성자에서 기반 클래스의 생성자를 호출해야 할 필요가 있다. 
- 기반 클래스를 초기화하려면 기반 클래스 이름 뒤에 괄호를 치고 생성자 인자를 넘긴다 
```kotlin
open class User(val nickname: String) {}

class TwitterUser(nickname: String) : User(nickname) {}
```
- 클래스를 정의할 때 별도로 생성자를 정의하지 않으면 컴파일러가 자동으로 아무 일도 하지 않는 인자가 없는 디폴트 생성자를 생성
```kotlin
open class Button // 인자가 없는 디폴트 생성자 만들어짐
```
- Button 생성자는 아무 인자도 받지 않지만 
- Button 클래스를 상속한 하위 클래스는 반드시 Button 클래스의 생성자를 호출해야 한다 
```kotlin
class RadioButton: Button() {}
```
- 이 규칙으로 기반 클래스의 이름 뒤에는 꼭 빈 괄호가 들어간다 
  - 물론 생성자 인자가 있다면 괄호 안에 인자가 들어가고 
- 반면 인터페이스는 생성자가 없으니까 어떤 클래스가 인터페이스를 구현하는 경우 그 클래스의 상위 클래스 목록에 있는 인터페이스 이름 뒤에는 괄호가 없다
- 
- 어떤 클래스를 외부에서 인스턴스화 하지 못하게 막고 싶다면 모든 생성자를 private으로 만들면 된다 
```kotlin
class Secretive private constructor() {}
```
### 4.2.2 부 생성자: 상위 클래스를 다른 방식으로 초기화 
```kotlin
open class View{
    constructor(ctx : Context) {}
    constructor(ctx : Context, attr : AttributeSet) {}
}
```
- 주 생성자 없이 부 생성자만 있는 경우 
```kotlin
class MyButton : View {
  constructor(ctx : Context) : super(ctx) {}
  constructor(ctx : Context, attr : AttributeSet) : super(ctx, attr) {}
}
```
- 두 부 생성자가 super() 키워드 통해 자신에 대응하는 상위 클래스 생성자 호출 
```kotlin
class MyButton : View {
  constructor(ctx : Context) : this(ctx, MY_STYLE) {}
  constructor(ctx : Context, attr : AttributeSet) : super(ctx, attr) {}
}
```
- this() 를 통해서 클래스 자신의 다른 생성자 호출 가능 

## 4.2.3 인터페이스에 선언된 프로퍼티 구현 
- 추상 프로퍼티 선언이 들어 있는 인터페이스 선언 예

```kotlin
interface User {
  val nickname: String
}
```
- 이는 User 라는 인터페이스를 구현하는 클래스가 nickname의 값을 얻을 수 있는 방법을 제공해야 한다는 뜻이다 
- privateUser : 별명을 저장하게 하자
```kotlin
class PrivateUser(override val nickName: String) : User 

println(PrivateUser("test@kotlinlang.org").nickName) // "test@kotlinlang.org"
```
- SubscribingUser는 이메일을 함께 저장 
```kotlin
class SubscribingUser(val email: String) : User {
  override val nickName: String
    get() = email.substringBefore('@') // custom getter로 nickname 프로퍼티 설정
  // 뒷받침하는 필드에 값을 저장하지 않고 매번 이메일 주소에서 별명을 계산해 반환
  
}

println(SubscribingUser("test@kotlinlang.org").nickName) // test
```
- FacebookUser에서는 초기화 식으로 nickname 값을 초기화한다 
```kotlin
class FacebookUser(): User {
  override val nickName = getFacebookName(accountId)
  /** getFacebookName 는 외부에서 가져오는 것이기 때문에 비용이 많이 들 수 있어
   * 위에 SubscribingUser 처럼 매번 가져오는 식으로 하지 않고 
   * 초기화 하는 단계에 한 번만 가져오도록 했다. 
   */
}
```
- 
- 인터페이스에는 추상 프로퍼티 뿐 아니라 게터와 세터가 있는 프로퍼티를 선언할 수 있다 
- 물론 그런 게터와 세터는 뒷받침하는 필드를 참조할 수 없다. 
  - ( 뒷받침하는 필드가 있다면 인터페이스에 상태를 추가하는 셈인데 인터페이스는 상태를 저장할 수 없다)
```kotlin
interface User {
    val email: String
    val nickname: String
      get() = email.substringBefore('@')
}
```
- 이 인터페이스에는 추상 프로퍼티가 2개.
  - 하위 클래스는 추상 프로퍼티인 email을 반드시 오버라이드해서 구현해야 한다 
  - 반면 nickname은 오버라이드하지 않고 상속할 수 있다 
- 클래스에 구현된 프로퍼티는 뒷받침 필드를 가질 수 있다 

## 4.2.4 게터와 세터에서 뒷받침하는 필드에 접근 

- 이제 어떤 값을 저장은 하되, 그 값을 변경하거나 읽을 때마다 정해진 로직을 실행하도록 만들어보자 
- 값을 저장하는 동시에 로직을 실행하기 위해서는 접근자 안에서 프로퍼티를 뒷받침하는 필드에 접근할 수 있어야 한다 
- 프로퍼티에 저장된 값의 변경 이력의 로그를 남기려는 경우 
  - 변경 가능한 프로퍼티를 정의하되 세터에서 프로퍼티 값을 바꿀 때마다 약간의 코드를 추가로 실행해야 한다 

```kotlin
class User (val name: String){
  var address: String = "unspecified"
    set(value: String) {
      println("""
                Address was changed for $name:
                "$field" -> "$value"
            """.trimIndent())
      field = value
    }
}
/**
 * Address was changed for Alice:
 * "unspecified" -> "Elsenhemierstrases 47, 80687 Muenchen"
 */
```
- 컴파일러는 디폴트 접근자 구현을 사용한건 직접 게터나 세터를 사용한건 상관 없이 게터나 세터에서 field를 사용하는 프로퍼티에 대해 뒷받침하는 필드를 생성해준다 
- 다만 field 를 사용하지 않는 커스텀 접근자 구현을 정의한다면 뒷받침하는 필드는 존재하지 않는다 
  - 프러퍼티가 val인 경우에는 게터에 field가 없으면 되지만, var인 경우에는 게터나 세터 모두에 field가 없어야 한다 
```kotlin
// TODO: 위에도 무슨 말인지 모르겠다  
```
- 이제 접근자의 가시성을 어떻게 바꾸는지 알아보자 

## 4.2.4 접근자의 가시성 변경 
- 접근자의 가시성은 기본적으로 프로퍼티의 가시성과 같다 
- 하지만 원한다면 get, set 앞에 가시성 변경자를 추가해서 접근자의 가시성을 변경할 수 있다 
```kotlin
class LengthCounter {
    var counter: Int = 0
        private set // 이 클래스 밖에 이 프로퍼티의 값을 바꿀 수 없다 
    fun addWord(word: String) {
        counter += word.length
    }
}
```
- counter 프로퍼티는 pulbic, setter는 private 
```kotlin
    val lengthCounter = LengthCounter()
    lengthCounter.addWord("Hi!") 
    println(lengthCounter.counter) // 3
```

# 4.3 컴파일러가 생성한 메서드 : 데이터 클래스와 클래스 위임 
- 자바 플랫폼에서는 클래스가 equals, hashCode, toString 등의 메서드를 구현해햐 한다 
- 그리고 이런 메서드들은 보통 비슷한 방식으로 기계적으로 구현할 수 있다 
- IDE 도움을 받으면 쉽게 생성 
- 하지만 자동으로 equals, hashCode, toString 등의 메서드 생성한다고 해도 코드가 번잡스럽다
- 코틀린 컴파일러는 이 기계적으로 생성하는 작업을 보이지 않는 곳에서 해줘 코드를 깔끔하게 구현할 수 있다
- 그런 원칙이 잘 드러났던 예 : 클래스 생성자나 프로퍼티 접근자를 컴파일러가 자동으로 만들어 줌 

## 4.3.1 모든 클래스가 정의해야 하는 메서드 

- 코틀린도 equals, hashCode, toString 등을 오버라이드 할 수 있다 
- 각각이 어떤 메서드이고 어떻게 그런 메서드를 정의해야 하는지 살펴보자 
- 코틀린은 이런 메서드 구현을 자동으로 생성 

```kotlin
package ch04
class Client(val name: String, val postalCode: Int)

```
```kotlin
val client = Client("오현석", 4122)
println(client) // ch04.Client@4a574795
```

### 문자열 표현 : toString() 
```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client (name=$name, postalCode=$postalCode)"
}
```
```kotlin
// Client (name=오현석, postalCode=4122)
```

### 객체의 동등성: equals()

```kotlin
    val client1 = Client("오현석", 4122)

    val client2 = Client("오현석", 4122)
    println(client1 == client2) // false
```

- 번외 : 동등성 연산에 == 사용 
  - 자바에서는 == 를 원시타입과 참조타입을 비교할 때 사용한다
    - 원시타입의 경우 == 는 피연산자의 값이 같은지 비교한다 (동등성 equality)
    - 참조타입의 경우 == 는 두 피연산자의 주소가 같은지 비교한다. (참조 비교(reference comparison))
  - 따라서 자바에서는 두 객체의 동등성을 알려면 equals 사용해야 한다
  - 코틀린에서는 == 연산자가 두 객체를 비교하는 방법 
    - == 는 내부적으로 equals를 호출 
    - 참조 비교를 위해서는 === 연산자를 사용할 수 있다 
    - === 는 자바에서 객체의 참조를 비교할 때 사용하는 == 와 같다 
```kotlin
package ch04

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client (name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean { /***
                                                Any는 java.lang.Object에 대응하는 클래스. 
                                                코틀린의 모든 클래스의 최상위 클래스 ***/
        if (other == null || other !is Client) 
            return false
        return name == other.name &&
                postalCode == other.postalCode 
    }
}

```
- 코틀린의 is 연산자는 자바의 instanceOf와 같다. 
- is 는 어떤 값의 타입을 검사한다 
- 코틀린에서는 override 변경자가 필수라 `override fun equals(other: Any?):`를 `override fun equals(other: Client?):`로 작성할 수 없다
- 면접에서 Client가 제대로 작동하지 않는 경우 물으면?
  - hashCode 정의를 빠트려서 
  - 이 경우에는 hashCode 가 없다는 점이 원인이다. 

### 해시 컨테이너 : hashCode() 
- 자바에서는 equals를 오버라이드 할 때는 반드시 hashCode 도 오버라이드 해야 한다 
```kotlin
  val processed = hashSetOf(Client("오현석", 4122))
    println(processed.contains(Client("오현석", 4122))) // false 
```
- 이는 Client 클래스가 hashCode 를 정의하지 않아서 
- JVM언어에서는 hashCode가 지켜야 하는 " equals() 가 true를 반환하는 두 객체는 반드시 같은 hashCode를 반환해야 한다"는 제약 
```kotlin
package ch04

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client (name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean { /***
                                                Any는 java.lang.Object에 대응하는 클래스.
                                                코틀린의 모든 클래스의 최상위 클래스 ***/
        if (other == null || other !is Client)
            return false
        return name == other.name &&
                postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
}

```
```kotlin
 // true
```
- 코틀린은 다행히 이 모든 메서드를 자동으로 생성 가능 

## 4.3.2 데이터 클래스 : 모든 클래스가 정의해야 하는 메서드 자동 생성
- data 변경자가 붙은 클래스를 데이터 클래스 라고 부른다 
```kotlin
data class Client(val name: String, val postalCode: Int)
```
```kotlin
 val client1 = Client("오현석", 4122)
    println(client1) //Client(name=오현석, postalCode=4122)

val client2 = Client("오현석", 4122)
    println(client1 == client2) // true

    val processed = hashSetOf(Client("오현석", 4122))
    println(processed.contains(Client("오현석", 4122))) // true
```
- 데이터 클래스는 equals, hashcode, toString 제공 
- 추가로 유용한 메서드 몇가지 더 7.4 절에서 더 얘기 할게 

### 데이터 클래스와 불변성 : copy() 메서드 
- 하나만 여기서 얘기하자면 copy() 메서드 
- 데이트 클래스의 프로퍼티가 꼭 val 일 필요는 없다. 
- 하지만 데이터 클래스의 모든 프로퍼티를 읽기 전용으로 만들어서 데이터 클래스를 불변 immutable로 만들기를 권장 
- 코틀린에서는 객체를 복사하면서 일부 프로퍼티를 변경하게 해주는 copy() 메서드 제공
- Client의 copy() 메서드를 구현한다면 아래와 같을 것 
```kotlin
fun copy(name: String = this.name,
            postalCode: Int = this.int) {
    Client(name, postalCode)
}       
```
```kotlin
    val lee = Client("이계명", 4223)
    lee.copy(postalCode = 4000)
    println(lee) // Client(name=이계명, postalCode=4223)
```

## 4.3.3 클래스 위임 : by 키워드 사용 
- 대규모 객체지향 시스템을 취약하게 만드는 문제는 보통 구현 상속에 의해 발생한다. implementation inheritance 
- 하위 클래스가 상위 클래스의 메서드 중 일부를 오버라이드하면 하위 클래스는 상위 클래스 세부 구현 사항에 의존하게 된다 
- 시스템이 변하면서 상위 클래스이 구현이 바뀌거나 상위 클래스에 새로운 메서드가 추가된다. 
  - 그 과정에서 하위 클래스가 상위 클래스에 대해 가졌던 가정이 깨지면서 코드가 정상적으로 작동할 수 없을 수 있다 
- 코틀린을 설계하면서 기본으로 final 로 모든 클래스를 취급하면서 이 문제를 해결하고자 했다 
  - 상속을 염두에 두고 open 변경자로 열어둔 클래스만 확장할 수 있다 
  - 열린 상위 클래스의 소스코드를 변경할 때는 open 키워드를 보고 해당 클래스를 다른 클래스가 상속했다고 예상할 수 있다 변경시 하위 클래스를 깨지 않게 조심할 수 있다 
- 하지만 종종 상속을 허용하지 않는 클래스에 대해 새로운 동작을 추가해야 할 때가 있다. 이 때 사용하는 패턴이 데코레이터 패턴
  - 이 패턴의 핵심은 상속을 허용하지 않는 클래스를 대신 사용할 수 있는 새로운 클래스(데코레이터)를 만들되 
  - 기존 클래스와 같은 인터페이스를 데코레이터가 제공하게 하고 
  - 기존 클래스를 데코레이터 내부에 필드로 유지 
  - 이 때 새로 정의해야 하는 기능은 데이코레이의 메서드에 새로 정의 
    - 물론 이 때 기존 클래스의 메서드나 필드 활용 가능 
  - 기존 기능이 그대로 필요한 부분은 데코레이터 메서드가 기존 클래스의 메서드에게 요청을 전달forward 
- 이런 접근법의 단점은 준비 코드가 상당히 많이 필요하다 
```kotlin
package ch04

class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()
    
    override val size: Int
        get() = innerList.size
    override fun isEmpty(): Boolean = innerList.isEmpty()
    override fun iterator(): Iterator<T> = innerList.iterator()
    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)
    override fun contains(element: T): Boolean = innerList.contains(element)
}
```
- 복잡한데, by 로 간편하게 
```kotlin
class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}
```
- 이 기법을 이용해 원소를 추가하려고 시도한 횟수를 기록하는 컬렉션을 구현할 수 있다 
```kotlin

class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}
```
- 이 때 CountingSet 에  MutableCollection<T> 의 구현 방식에 대한 의존관계가 생기지 않는다는 점이 중요 

# 4.4 object 키워드 : 클래스 선언과 인스턴스 생성 
- 코틀린에서는 object 키워드를 다양한 상황에서 사용, 근데 모든 경우 클래스를 정의하면서 동시에 인스턴스(객체)를 생성한다는 공통점
- object를 사용하는 여러 상황들 
  - 객체 선언 object declaration 은 싱글턴을 정의하는 방법 중 하나 
  - 동반 객체 companion object 는 인스턴스 메서드는 아니지만, 어떤 클래스와 관련 있는 메서드와 팩토리 메서드를 담을 때 쓰인다 
    - 동반 객체 메서드에 접근할 때는 동반 객체가 포함된 클래스의 이름을 사용할 수 있다 
  - 객체 식은 자바의 무명 내부 클래스 anonymous inner class 대신 쓰인다

## 4.4.1 객체 선언 : 싱글턴을 쉽게 만들기 
- 자바에서는 싱글턴을 만들 때 보통 클래스의 생성자를 private으로 제한하고 정적인 필드에 그 클래스의 유일한 객체를 저장하는 싱글턴 패턴 singleton pattern 을 통해 이를 구현한다 
- 코틀린은 객체 선언 기능을 통해 싱글턴을 언어에서 기본 지원
- 객체 선언은 클래스 선언와 그 클래스에 속한 단일 인스턴스의 선언을 합친 것 
```kotlin
object Payroll {
    val allEmployees = arrayListOf<Person>()
    
    fun calculateSalary() {
        for ( person in allEmployees) {
            ...
        }
    }
}
```
- 객체 선언은 object 키워드로 시작 
  - 클래스를 정의하고 그 클래스의 인스턴스를 만들어서 변수에 저장하는 작업을 단 한 문장으로 처리
- 클래스와 마찬가지로 객체 선언 안에도 프로퍼티, 메소드, 초기화 블록 등이 들어갈 수 있다 
- 하지만 생성자는 (주생성자, 부 생성자 모두) 객체 선언에 쓸 수 없다 
- 싱글턴 객체는 객체 선언문이 있는 위치에서 생성자 호출 없이 즉시 만들어 진다 
- 따라서 객체 선언에는 생성자 정의가 필요 없다 
- 
- 변수와 마찬가지로 객체 선언에 사용한 이름 뒤에 마침표 (.) 을 붙이면 객체에 속한 메서드나 프로퍼티에 접근 가능 
- 객체 선언도 클래스나 인터페이스를 상속할 수 있다 
  - 프레임워크를 사용하기 위해 특정 인터페이스를 구현해야 하는데 그 구현 내부에 다른 상태가 필요하지 않는 경우에 이런 기능이 유용
    - 예를 들어 java.util.Comparator 인터페이스 
    - Comparator 구현은 두 객체를 인자로 받아 그 중 어느 객체가 더 큰지 알려주는 정수를 반환 
    - Comparator 안에는 데이터를 저장할 필요 없다 
    - 따라서 어떤 클래스에 속한 객체를 비교할 때 사용하는 Comparator 는 보통 클래스마다 하나만 있으면 된다 
    - 따라서 Comparator 인스턴스를 만드는 방법으로 객체 선언이 가장 좋은 방법 
    - 두 파일 경로를 대소문자 관계 없이 비교해주는 Comparator 구현 
    ```kotlin
    object CaseInsensitiveFileComparator : Comparator<File> {
        override fun compare(file1: File, file2: File): Int {
             return file1.path.compareTo(
                                    file2.path,
                                    ignoreCase = true
             )
        }
    }
    ```
    ```kotlin
       println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user"))) // 0
    ```
    - 일반 객체 (클래스 인스턴스)를 사용할 수 있는 곳에서는 항상 싱글턴 객체를 사용할 수 있다 
      - 예를 들어 이 객체를 Comparator를 인자로 받는 함수에게 인자로 넘길 수 있다 
        ```kotlin
         val files = listOf(File("/z"), File("/a"))
         println(files.sortedWith(CaseInsensitiveFileComparator)) // [/a, /z]
        ```
- 클래스 안에서 객체 선언 가능 
  - 그런 객체도 단 하나뿐 (바깥 클래스의 인스턴스 마다 생기는 것이 아님)
```kotlin
data class Person(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int =
           p1.name compareTo p2.name
    }
}
```
```kotlin

    val persons = listOf(Person("Bob"), Person("Alice"))
    println(persons.sortedWith(Person.NameComparator)) // [Person(name=Alice), Person(name=Bob)]
```
- 코틀린 객체 선언은 유일한 인스턴스에 대한 정적인 필드가 있는 자바 클래스로 컴파일된다 
- 이 때 인스턴스 이름은 항상 INSTANCE다 
- 싱글턴 패턴을 자바에서 구현해도 비슷한 필드가 필요 
- 자바 코드에서 코틀린 싱글턴 객체를 사용하려면 정적인 INSTANCE 필드를 통하면 된다 
```java
CaseInsensitiveFileComparator.INSTANCE.compare(file1, file2);
```
- 이 예제에서 INSTANCE 필드 타입은 CaseInsensitiveFileComparator 다 

## 4.4.2 동반 객체 : 팩토리 메서드와 정적 멤버가 들어갈 장소 
- 코틀린 클래스 안에는 정적인 멤버가 없다. 자바의 static 키워드 지원 안한다 
- 그대신 패키지 수준의 최상위 함수(자바의 정적 메서드 역할을 거의 대신 할 수 있다)와 객체 선언(자바의 정적 메서드 역할 중 코틀린 최상위 함수가 대신할 수 없는 역할이나 필드를 대신할 수 있다)를 활용한다 
- 대부분의 경우 최상위 함수를 활용하는 편을 더 권장한다 
- 하지만 최상위 함수는 private으로 표시된 클래스 비공개 멤버에 접근할 수 없다. 
- 그래서 클래스의 인스턴스와 관계 없이 호출해야 하지만 클래스 내부 정보에 접근해야 함수가 필요할 때는 클래스에 중첩된 객체 선언의 멤버 함수로 정의해야 한다. 
- 그런 함수의 대표적인 예로 팩토리 메서드를 들 수 있다 
- 클래스 안에 정의된 객체 중 하나에 companion이라는 특별한 표시를 붙이면 그 클래스의 동반 객체로 만들 수 있다 
- 동반 객체의 프로퍼티나 메서드에 접근하려면 그 동반 객체가 정의된 클래스 이름을 사용한다 
- 이 때 객체의 이름을 따로 지정할 필요가 없다 
- 그 결과 동반 객체의 멤버를 사용하는 구문은 자바의 정적 메서드 호출이나 정적 필드 사용 구문과 같아진다 
```kotlin
class A {
    companion object {
        fun bar() {
            println("Companin object called")
        }
    }
}
// A.bar() 
```
- 동반객체가 private 생성자를 호출하기 좋은 위치다. 
  - 동반객체는 자신을 둘러싼 클래스의 모든 private 멤버에 접근할 수 있다 
  - 따라서 동반 객체는 바깥쪽 클래스의 private 생성자도 호출할 수 있다 
  - 따라서 동반객체는 팩토리 패턴을 구현하기 가장 적합한 위치다 
- 객체를 생성하는 여러 방법이 있는 클래스가 있다고 했을 때 
```kotlin
class User {
    val nickname: String
    constructor(email: String) {
        nickname = email.substringBefore('@')
    }
    
    constructor(facbookAccountId: Int) {
        nickname = getFaceBookName(facbookAccountId)
    }
}
```
- 이를 팩토리 메서드를 통해 구현할 수 있다 
```kotlin
class User(val nickname: String) {
    
    companion object {
        fun newSubscribingUser(email: String) =
            User(email.substringBefore('@'))
        
        fun newFacebookUser(facbookAccountId: Int) {
            User(getFaceBookName(facbookAccountId))
        }
    }
}

```
- 클래스 이름 사용해 클래스에 속한 동반 객체의 메서드 호출 가능 
```kotlin
    val subscribingUser = User.newSubscribingUser("bog@gmail.com")
    val facebookUser = User.newFacebookUser(4)
```
- 팩토리 메서드는 유용
  - 목적에 따라 팩토리 메서드 이름을 정할 수 있다 
  - 팩토리 메서드가 선언된 클래스의 하위 클래스 객체를 반환할 수도 있다 
    - 예를 들어 subscribingUser와 facebookUser 클래스가 따로 존재한다면 그 때 그 때 필요에 따라 적당한 클래스의 객체를 반환할 수 있다
    - 또한 팩토리 메서드는 생성할 필요가 없는 객체를 생성하지 않을 수도 있다 
      - 예를 들어 이메일 주소별로 유일한 User 인스턴스를 만드는 경우 팩토리 메서드가 이미 존재하는 인스턴스에 해당하는 이메일 주소를 전달받으면 새 인스턴스를 만들지 않고 캐시에 있는 기존 인스턴스를 반환할 수 있다. 
- 하지만 클래스를 확장해야만 하는 경우에는 동반 객체 멤버를 하위 클래스에서 오버라이드 할 수 없으므로 여러 생성자를 사용하는 편이 더 나은 해법이다 

## 4.4.3 동반 객체를 일반 객체처럼 사용 
- 동반 객체는 클래스 안에 정의된 일반 객체다. 
- 동반 객체에 이름을 붙이거나, 동반 객체가 인터페이스를 상소갛거나, 동반 객체 안에 확장함수와 프로퍼티를 정의할 수 있다.
- 회사 급여 명부를 제공하는 웹 서비스가 있다고 하자 
  - 서비스에서 사용하기 위해 객체를 JSON으로 직렬화/역직렬화 해야 한다 
- 직렬화 로직을 동반 객체 안에 넣을 수 있다 
```kotlin
class Person(val name: String) {
    companion object Loader { // 동반객체에 이름을 붙인다 
        fun fromJSON(jsonText: String) : Person = ...
    }
}
```
```kotlin
>>> person = Person.Loader.fromJSON("{name: 'Dirty'}")
>>> person.name // Dirty
>>> person2 = Person.fromJSON("{name: 'Brent'}")
>>> person2.name // Brent
```
- 두 방법 모두 fromJSON 호출 가능 
- 특별히 이름 지정하지 않으면 동반 객체 이름은 자동으로 Companion 이 된다. 

### 동반 객체에서 인터페이스 구현 
- 동반 객체도 인터페이스를 구현할 수 있다 
- 인터페이스를 구현하는 동반 객체를 참조할 때 객체를 둘러싼 클래스의 이름을 바로 사용할 수 있다
- 시스템에 Person 을 포함한 다양한 타입의 객체가 있다고 가정하자
- 이 시스템에서는 모든 객체를 역직렬화를 통해 만들어야 하기 때문에 
- 모든 타입의 객체를 생성하는 일반적인 방법이 필요하다 
- 이를 위해 JSON을 역질렬화 하는 JSONFactory 인터페이스가 존재한다
- Person은 다음와 같이 JSONFactory 구현을  제공할 수 있다 
```kotlin
interface JSONFactory<T> {
    fun fromJSON(jsonText: String) : T
}
class Person(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String) : Person = ... // 동반객체가 인터페이스 구현
    }
}
```
- 이제 JSON으로부터 각 원소를 다시 만들어내는 추상 팩토리가 있다면 Person 객체를 그 팩토리에게 넘길 수 있다 
```kotlin
fun loadFromJSON(factory: JsonFactory<T>): T {
    ...
}
loadFromJSON(Person) // 동반객체의 인스턴스를 함수에 넘긴다
```
```kotlin
// TODO: 이게 무슨말일까?  Person이 어떻게 JSONFactory<Person> 로 들어가지? 
// 그럼 동반객체가 여럿 일 수도 있나? 구현 타입이? 
```
- 여기서 동반객체가 구현한 JsonFactory 의 인스턴스를 넘길 때 Person 클래스의 이름을 사용했다는 점에 유의해라 
 
### 동반 객체 확장 
- 3.3 절에서 확장 함수를 사용하면 코드 기반의 다른 곳에서 정의된 클래스의 인스턴스에 대해 새로운 메서드를 정의할 수 있음을 보였다. 
- 그렇다면 자바의 정적 메서드나 코틀린의 동반 객체 메서드처럼 기존 클래스에 대해 호출할 수 있는 새로운 함수를 정의하고 싶다면?
- 클래스에 동반객체가 있으면 그 객체 안에 함수를 정의함으로써 클래스에 대해 호출할 수 있는 확장 함수를 만들 수 있다 
- C라는 클래스 안에 동반 객체가 있고 그 동반객체 (C.Companion) 안에 func를 정의하면 외부에서는 func()를 C.func()로 호출할 수 있다 
- 
- 예를 들어 Person 은 비즈니스 모듈
- 그 비즈니스 모듈이 특정 데이터 타입에 의존하길 원하지 않는다 
- 역직렬화 함수를 비즈니스 모듈이 아니라 클라이언트/서버 통신을 담당하는 모듈 안에 포함시키고 싶다 
- 확장함수를 사용하면 이렇게 구조를 잡을 수 있다 
- 다음 예제에서는 이름 없이 정의된 동반 객체를 가리키기 위해 동반 객체의 기본 이름인 Companion사용 
```kotlin
//비즈니스 로직 모듈 
class Person(val firstName: String, val lastName: String) {
    companion object {
        // 비어있는 동반 객체 선언
    }
}

// 클라이언트/서버 통신 모듈 
fun Person.Companion.fromJSON(json: String): Person {
    // 확장 함수 선언 
}

val p = Person.fromJSON(json)
```
- 마치 동반 객체 안에서 fromJSON 를 정의한 것처럼 fromJSON 을 호출할 수 있다 
- 하지만 실제로 fromJSON 는 클래스 밖에서 정의한 확장 함수 
- 다른 보통 확장함수 처럼 fromJSON 도 클래스 멤버 함수처럼 보이지만, 실제로는 멤버 함수가 아니다. 
- 여기서 동반 객체에 대한 확장함수를 작성할 수 있으려면 원래 클래스에 동반객체를 꼭 선언해야 함 
- 설령 빈 객체라도 동반 객체가 꼭 있어야 한다 

## 4.4.4 객체 식: 무명 내부 클래스를 다른 방식으로 작성 
- object 키워드를 싱글턴과 같은 객체를 정으하고 그 객체에 이름을 붙일 때만 사용하는 것은 아니다 
- 무명 객체 anonymous object 를 정의할 때도 object 키워드를 쓴다 
- 무명 객체는 자바의 무명 내부 클래스를 대신 
- 무명 객체로 이벤트 리스터 구현하기

```kotlin

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

window.addMouseListener(
  object : MouseAdapter() {
      override fun mouseClicked(e: MouseEvent?){
        // ... 
      }
    
      override fun mouseEntered(e: MouseEvent?){
          // ...
      }
  }
)
```
- 사용한 구문은 객체 선언에서와 같다. 
- 유일한 차이는 객체 이름이 빠졌다는 점 
- 객체 식은 클래스를 정의하고 그 클래스에 속한 인스턴스를 생성하지만 
- 클래스나 인스턴스에 이름을 붙이지는 않는다 
- 이런 경우 보통 함수를 호출하면서 인자로 무명 객체를 넘기기 때문에 클래스와 인스턴스 모두 이름이 필요 없다
- 하지만 객체에 이름을 붙여야 한다면 변수에 무명 객체를 대입하면 된다. 
```kotlin
val listener = object : MouseAdapter() {
      override fun mouseClicked(e: MouseEvent?){
        // ... 
      }
    
      override fun mouseEntered(e: MouseEvent?){
          // ...
      }
  }
```
- 한 인터페이스만 구현하거나 한 클래스만 확장할 수 있는 자바의 무명 내부 클래스와는 달리 
- 코틀린의 무명 클래스는 여러 인터페이스를 구현하거나 클래스를 확장하면서 인터페이스를 구현할 수 있다 
- 객체 선언과 달리 무명 객체는 싱글턴이 아니다. 객체 식이 쓰일 때마다 새로운 인스턴스가 생성 된다. 
- 무명 객체 안에서 로컬 변수 사용하기 
  - 자바의 무명 클래스와 같이 객체 식 안의 코드는 그 식이 포함된 함수의 변수에 접근할 수 있다 
  - 하지만 자바와 달리 final이 아닌 변수도 객체 식 안에서 사용할 수 있다 
  - 따라서 객체 식 안에서 그 값을 변경할 수 있다
  ```kotlin

   import java.awt.event.MouseAdapter
   fun countClicks(window: Window) {
    var clickCount = 0 // 로컬 변수 정의
    
    window.addMouseLisener(object: MouseAdapter() {
      override fun mouseClicked(e: MouseEvent) {
        clickCount++ // 로컬 변수 값 변경
      }
    })
      
  }
  ```
- 객체 식은 무명 객체 안에서 여러 메서드를 오버라이드 할 경우에 훨씬 더 유용
- 메서드가 하나뿐인 인터페이스를 구현해야 한다면 코틀린의 SAM 지원을 활용하는 편이 더 낫다
- SAM 변환을 사용하려면 무명 객체 대신 함수 리터럴(람다)를 활용해야 함
- 자세한 건 5장 
```kotlin
// TODO: 여기도 이해 안가. 무명 객체 는 한 메서드를 오버라이드 하기 위한 것 아닌가? 
```

# 4.5 요약 
- 코틀린의 인스터페이스는 자바 인터페이스와 비슷하지만 디폴트 구현을 포함할 수 있고(자바 8부터 가능), 프로퍼티도 포함할 수 있다 (자바 불가능)
- 모든 코틀린 선언은 기본으로 final, public 
- 선인이 final이 되지 않게 하려면(상속/오버라이딩이 가능케 하려면) 앞에 open을 붙여야 한다
- internal 선언은 같은 모듈 안에서만 볼 수 있다 
- 중첩 클래스는 기본적으로 내부 클래스가 아니다 
  - 바깥쪽 클래스에 대한 참조를 중첩 클래스 안에 포함시키려면 inner 키워드를 중첩 클래스 선언 앞에 붙여야 
- sealed 클래스를 상속하는 클래스를 정의하려면 반드시 부모 클래스 정의 안에 중첩(또는 내부) 클래스로 정의해야 한다 
  - 코틀린 1.1 부터는 같은 파일 안에 만 있으면 된다 
- 초기화 블록과 부 생성자를 활용해 클래스 인스턴스를 더 유연하게 초기화할 수 있다 
- field 식별자를 통해 프로퍼티 접근자(게터, 세터)안에서 프로퍼티 데이터를 저장하는 데 쓰이는 뒷밤침하는 필드를 참조할 수 있다 
- 데이터 클래스를 사용하면 컴파일러가 equals, tostring, hashCode copy 등의 메서드를 자동으로 생성해준다 
- 클래스 위임을 사용하면 위임 패턴을 구현할 때 수많은 성가신 준비 코드 줄일 수 있다 
- 객체 선언을 사용하면 코틀린 답게 싱글턴 클래스를 정의할 수 있다 
- 패키지 수준 함수와 프로퍼티 및 동반 객체와 더불어) 동반 객체의 자바의 정적 메서드와 필드 정의를 대신 
- 동반 객체도 다른 (싱글톤) 객체와 마찬가지로 인터페이스를 구현할 수 있다. 외부에서 동반 객체에 대한 확장함수와 프로퍼티 정의 가능 
- 코틀린의 객체 식은 여러 인스턴스를 구현하거나 객체가 포함된 영역에 있는 변수의 값을 변경할 수 있는 등 자바 무명 내부 클래스보다 더 많은 기능을 제공한다 
