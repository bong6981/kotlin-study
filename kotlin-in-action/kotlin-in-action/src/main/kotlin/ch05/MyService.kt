//package ch05
//
//class MyService {
//    fun performAction(): String = "foo"
//}
//
//class MyTest {
//    private lateinit var myService: MyService // 초기화하지 않고 널이 될 수 없는 프로퍼티 선언
//
//    @Before fun setUp() {
//        myService = MyService() // setUp 에서 진짜 초깃값을 지정
//    }
//
//    @Test fun testAction() {
//        Assert.assertEquals("foo",
//        myService.performAction()) // 널 검사 수행하지 않고 프로퍼티 사용
//    }
//}
