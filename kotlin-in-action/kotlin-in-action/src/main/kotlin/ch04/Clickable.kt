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
