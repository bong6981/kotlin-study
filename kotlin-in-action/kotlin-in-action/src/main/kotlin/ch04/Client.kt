package ch04

//class Client(val name: String, val postalCode: Int) {
//    override fun toString(): String = "Client (name=$name, postalCode=$postalCode)"
//    override fun equals(other: Any?): Boolean { /***
//                                                Any는 java.lang.Object에 대응하는 클래스.
//                                                코틀린의 모든 클래스의 최상위 클래스 ***/
//        if (other == null || other !is Client)
//            return false
//        return name == other.name &&
//                postalCode == other.postalCode
//    }
//
//    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
//}

data class Client(val name: String, val postalCode: Int)

