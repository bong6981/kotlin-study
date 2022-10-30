package ch03

class Button : View() {
    override fun click() = println("button clicked")
}

fun Button.showOff() = println("im a button")
