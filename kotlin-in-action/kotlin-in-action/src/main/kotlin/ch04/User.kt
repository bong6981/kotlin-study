package ch04

//class User (val name: String){
//    var address: String = "unspecified"
//        set(value: String) {
//            println("""
//                Address was changed for $name:
//                "$field" -> "$value"
//            """.trimIndent())
//            field = value
//        }
//}

//class User {
//    val nickname: String
//    constructor(email: String) {
//        nickname = email.substringBefore('@')
//    }
//
//    constructor(facbookAccountId: Int) {
//        nickname = getFaceBookName(facbookAccountId)
//    }
//}

class User(val nickname: String) {

    companion object {
        fun newSubscribingUser(email: String) =
            User(email.substringBefore('@'))

//        fun newFacebookUser(facbookAccountId: Int) {
//            User(getFaceBookName(facbookAccountId))
//        }
    }
}
