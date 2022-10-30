package ch03

open class View {
    open fun click() = println("view clicked")
}

fun View.showOff() = println("im a view")
