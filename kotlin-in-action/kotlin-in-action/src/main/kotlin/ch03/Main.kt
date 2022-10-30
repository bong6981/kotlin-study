package ch03


fun main(args: Array<String>) {
//    MakeCollectionPractice().practice()
//    MakeCollectionPractice().moreThanJava()

//    MakeEasyToCallFun3_2().basic()

//    var list = listOf(1, 2, 3)
//    println( MakeEasyToCallFun3_2().joinToString(collection = list, separator = ":", prefix = "(", postfix = ")"))
//    println(MakeEasyToCallFun3_2().joinToString(list, ",", "", ""))
//    println(MakeEasyToCallFun3_2().joinToString(list))
//    println(MakeEasyToCallFun3_2().joinToString(list, ":"))

//    println("Kotlin".lastChar())
//
//    val list = listOf(1, 2, 3)
//    println(list.joinToString(separator = ":",
//    prefix = "(",
//    postfix = ")"))

//    val list = arrayListOf(1, 2, 3)
//    println(list.joinToString(" ")) // 1 2 3

//    val view : View = Button()
//    view.click()
//    view.showOff()

//    println("kotlin".lastchar)
//    val sb = StringBuilder("kotlin?")
//    sb.lastchar = '!'
//    println(sb)

    val kotlinLogo = """|  //
                       .| //
                       .|/\"""
    println(kotlinLogo.trimMargin("."))
}

val String.lastchar: Char
    get() = get(length - 1)

var StringBuilder.lastchar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }
