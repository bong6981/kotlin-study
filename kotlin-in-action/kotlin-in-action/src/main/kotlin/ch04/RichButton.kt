package ch04

open class RichButton : Clickable{ // 이 클래스는 열려 있다. 다른 클래스가 이를 상속 가능
    fun disable() {} // 이 함수는 파이널. 하위 클래스가 오버라이드 불가
    open fun animate() {} // 이 함수는 열려 있다.
    override fun click() {} // 이 함수는 열려 있는 메서드를 오버라이드. 오버라이드한 메서드는 기본적으로 열려 있다 (아까 인터페이스도 마찬가지)
    open fun stopAnimating() {} // 추상클래스에 속해도 비추상 함수는 기본적으로 final 이지만 원한다면 open 으로 할 수 있다
    fun animateTwice() {}
}
