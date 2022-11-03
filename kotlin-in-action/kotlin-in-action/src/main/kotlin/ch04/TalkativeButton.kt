//package ch04
//
//internal class TalkativeButton : Focusable {
//    private fun yell() = println("HEY!")
//    protected fun whisper() = println("Lets' talk!") // 'protected' visibility is effectively 'private' in a final class
//}
//
//fun TalkativeButton.giveSpeech() { // public' member exposes its 'internal' receiver type TalkativeButton
//    yell() // Cannot access 'yell': it is private in 'TalkativeButton'
//    whisper() // Cannot access 'whisper': it is protected in 'TalkativeButton'
//}
