package ch04

class LengthCounter {
    var counter: Int = 0
        private set // 이 클래스 밖에 이 프로퍼티의 값을 바꿀 수 없다
    fun addWord(word: String) {
        counter += word.length
    }
}
