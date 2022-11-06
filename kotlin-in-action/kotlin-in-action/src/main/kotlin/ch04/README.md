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




