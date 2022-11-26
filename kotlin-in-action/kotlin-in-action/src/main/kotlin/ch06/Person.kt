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
