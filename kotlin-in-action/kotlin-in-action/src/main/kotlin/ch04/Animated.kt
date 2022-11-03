package ch04

abstract class Animated { // 추상클래스. 인스턴스 만들지 못해
    abstract fun animate() // 추상함수. 구현이 없다. 하위 클래스에서 반드시 오버라이드 해야 한다. 추상 멤버는 항상 열려 있다 (따로 open 안붙여도)

}
