package ch04

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
