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
