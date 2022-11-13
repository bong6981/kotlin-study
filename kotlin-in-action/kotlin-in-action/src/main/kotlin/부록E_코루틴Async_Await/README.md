E. 코루틴과 Async/ Await
# E.1 코루틴이란?
서브루틴 : 여러 명령어 모아서 이름을 부여해 반복 호출할 수 있게 정의한 프로그램 구성요소로, 다른 말로 함수라고 부른다 
- 객체지향 언어에서는 메서드도 서브루틴 
- 어떤 서브루틴에 진입하는 방법은 오직 한 가지(해당 함수 호출하면 서브루틴의 맨 처음부터 시작)
- 그 때마다 활성 레코드 activation record 라는 것이 스택에 할당되면서 서부루틴 내부의 로컬 변수 등이 초기화 된다 
- 반면 서브루틴 안에서는 여러번 return을 사용할 수 있기 때문에 서브루틴이 실행을 중단하고 제어를 호출한 쪽 caller에게 돌려주는 지점은 여럿 있을 수 있다 
- 다만 일단 서브루틴에서 반환되고 나면 -> 활성레코드가 스택에서 사라짐 -> 실행 중이던 모든 상태를 잃어버린다 
- 그래서 서브루틴을 여러 반 반복 실행해도 ( 전역 변수가 다른 부수 효과가 있지 않는 한) 항상 결과를 반복해서 얻게 된다 

멀티태스킹: 여러 작업을 (적어도 사용자가 볼 때는)동시에 수행하는 것처럼 보이거나 실제로 동시에 수행하는 것

비선점형: 멀티태스킹의 각 작업을 수행하는 참여자들의 실행을 운영체제가 강제로 일시 중단시키고 다른 참여자를 실행하게 만들 수 없다는 뜻 

-> 따라서 각 참여자들이 서로 자발적으로 협력해야만 비선점형 멀티태스킹 제대로 작동할 수 있다 (운영체제가 강제로 뺏을 수 없어서)

코루틴이란 서로 협력해서 실행을 주고 받으면서 작동하는 여러 서브루틴을 말한다 
- 코루틴의 대표격인 제너레이터 generator
    - 어떤 함수 A가 실행되다가 제너레이터인 코루틴 B를 호출하면 A가 실행되던 스레드 안에서 코루틴 B의 실행이 시작된다 
    - 코루틴 B는 실행을 진행하다가 실행을 A에 양보한다 (yield 명령 사용 경우가 많음)
    - A는 다시 코루틴을 호출했던 바로 다음 부분부터 실행을 계속 
    - 또 A는 다시 코루틴 B 호출 
    - 이때 B가 일반함수라면 로컬 변수를 초기화 하면서 처음부터 실행을 다시 시작하겠지만 
    - B가 코루틴이라면 이전에 yield 실행을 양보했던 지점부터 실행을 계속 
  
코루틴을 사용하는 경우 장점은 일반적인 프로그램 로직을 기술하듯 코드를 작성하고 상대편 코루틴에 데이터를 넘겨야 하는 부분에서만 yield를 사용하면 된다는 점 
- 예를 들어 제너레이터를 사용해 카운트 다운을 구현하고 이를 이터레이터 iterator 처럼 불러와 사용하는 의사코드를 살펴보자 
```
generator countdonw(n) {
    while(n > 0) {
        yeild n 
        n -= 1        
    }
}

for i in countdown(10) {
    println(i)
}
```

# E.2 코틀린의 코루틴 지원: 일반적인 코루틴 
- 언어에 따라 코루틴을 지원하는 방식이 각자 다르다 
- 코틀린은 코루틴을 구현할 수 있는 기본 도구를 언어가 제공하는 형태다 
- 코틀린의 코루틴 지원 기본 기능들은 kotlin.coroutine 패키지 밑에 있고 코틀린 1.3 부터는 코틀린을 설치하면 별도의 설정 없이도 모든 기능을 사용할 수 있다
- 하지만 코틀린이 지원하는 기본 기능을 활용해 만든 다양한 형태의 코루틴들은 kotlinx.coroutines 패키지 밑에 있고 
  - https://github.com/kotlin/kotlinx.coroutines 여기에서 소스코드를 볼 수 있다 
```kotlin
plugins {
  kotlin("jvm") version "1.3.0"
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1")
}
```
- 난 더 높은 버전 설치

# E.2.1 여러가진 코루틴 
- 다음은 kotlinx.coroutines.core 모듈에 있는 코루틴 
- 정확히 이야기하면 각각은 코루틴을 만들어주는 코루틴 빌더 coroutine builder 라고 부른다 
- 코틀린에서는 코루틴 빌더에 원하는 동작을 람다로 넘겨서 코루틴을 만들어 실행하는 방식으로 코루틴을 활용한다 

## kotlinx.coroutines.CoroutineScope.launch 

> GlobalScope : 생성과 동시에 제어권을 메인에게 넘겨주고, 메인이 살아 있는 동안만 실행 보장


- launch는 코루틴을 잡 job으로 반환하며 만들어진 코루틴은 기본적으로 즉시 실행된다 
- 원하면 launch가 반환한 job의 cancel() 을 호출해 코루틴 실행을 중단시킬 수 있다 
- launch가 작동하려면 CoroutineScope 객체가 this 로 지정돼야 하는데 
- 다른 suspend함수 내부라면 해당함수가 사용중인 CoroutineScope 가 있겠지만 그렇지 않은 경우에는 GlobalScope를 사용하면 된다 
```kotlin
package 부록E_코루틴Async_Await

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)

fun log(msg: String) = println("${now()}:${Thread.currentThread()}:${msg}")

fun launchInGlobalScope() {
    GlobalScope.launch {
        log("coroutine started.")
    }
}

fun main(args: Array<String>) {
    log("main() started")
    launchInGlobalScope()
    log("launchInGlobalScope() started")
    Thread.sleep(5000L)
    log("main() terminated")
}
```
- 이 함수의 실행 결과 
```kotlin
//15:26:39.751:Thread[main,5,main]:main() started
//15:26:39.777:Thread[main,5,main]:launchInGlobalScope() started
//15:26:39.778:Thread[DefaultDispatcher-worker-1,5,main]:coroutine started.
//15:26:44.780:Thread[main,5,main]:main() terminated
```
- 유의할 점은 메인 함수와 GlobalScope.launch 가 만들어낸 코루틴이 서로 다른 스레드에서 실행된다는 점이다 
- GlobalScope는 메인 스레드가 실행 중인 동안만 코루틴 동작을 보장해준다 
- 앞 코드에서 main() 의 끝에서 두번째 줄에 있는 `Thread.sleep(5000L)` 를 없애면 코루틴이 아예 실행되지 않을 것 
  - launchInGlobalScope()가 호출한 launch는 스레드가 생성되고 시작되기 전에 메인스레드의 제어를 main()에게 넘겨서 
  - 따로 sleep() 을 호출하지 않으면 main()이 바로 끝나고 메인스가 완료되면서 바로 프로그램 전체가 끝나버린다 
  - 그래서 GlobalScope를 사용할 때는 조심해야 한다 
- 이를 방지하려면 비동기적으로 launch를 실행하거나 launch가 모두 다 실행될 때까지 기다려야 한다 
- 코루틴의 실행이 끝날 때까지 현재 스레드를 블록시키는 함수 runBlocking()이 있다 
- runBlocking은 CoroutineScope의 확장함수가 아니라 일반함수라 별도의 코루튼 스코프 객체 없이 사용가능하다 
- 로빈 추가 : 실무에서 runBlocking은 잘 안쓴다 
  - 코루틴 쓰는 이유가 없다 
```kotlin
fun runBlockingExample() {
    runBlocking {
        launch {
            log("coroutine started.")
        }
    }
}

fun main(args: Array<String>) {
    log("main() started")
//    launchInGlobalScope()
    runBlockingExample()
    log("runBlockingExample() started")
//    log("launchInGlobalScope() started")
    Thread.sleep(5000L)
    log("main() terminated")
}

```
```kotlin
//15:41:51.660:Thread[main,5,main]:main() started
//15:41:51.683:Thread[main,5,main]:coroutine started.
//15:41:51.683:Thread[main,5,main]:runBlockingExample() started
//15:41:56.684:Thread[main,5,main]:main() terminated
```
- 한가지 흥미로운 점 : 스레드가 모두 main 스레드라는 점 
- 이 코드만 봐서는 딱히 스레드나 다른 비동기 도구와 다른 장점을 찾아볼 수 없을 것 
- 하지만 코루틴들은 서로 yield()를 해주면서 협력할 수 있다 
```kotlin
fun main(args: Array<String>) {
  log("main() started")
  yieldExample()
  log("yieldExample() executed")
  log("main() terminated")
}

fun yieldExample() {
  runBlocking {
    launch {
      log("1")
      yield()
      log("3")
      yield()
      log("5")
    }
    log("after first launch")
    launch {
      log("2")
      delay(1000L)
      log("4")
      delay(1000L)
      log("6")
    }
    log("after second launch")
  }
  log("after runBlocking")
}

```
```
16:04:28.827:Thread[main,5,main]:main() started
16:04:28.849:Thread[main,5,main]:after first launch
16:04:28.850:Thread[main,5,main]:after second launch
16:04:28.851:Thread[main,5,main]:1
16:04:28.851:Thread[main,5,main]:2
16:04:28.854:Thread[main,5,main]:3
16:04:28.854:Thread[main,5,main]:5
16:04:29.860:Thread[main,5,main]:4
16:04:30.865:Thread[main,5,main]:6
16:04:30.866:Thread[main,5,main]:after runBlocking
16:04:30.866:Thread[main,5,main]:yieldExample() executed
16:04:30.866:Thread[main,5,main]:main() terminated
```
- 로그를 보면 다음 특징을 알 수 있다 
  - launch는 즉시 반환된다 
  - runBlocking은 내부 코루틴이 모두 끝난 다음에 반환된다 
  - delay()를 사용한 코루틴은 그 시간이 끝날 때까지 다른 코루틴에게 실행을 양보한다 
    - 앞 코드에서 delay(1000L) 대신 yield()를 쓰면 차례대로 1,2,3,4,5,6 표시 
    - 한가지 흥미로운 사실은 첫 번째 코루틴이 두 번이나 yield() 했지만 두 번째 코루틴이 delay() 상태라 다시 제어가 첫 번째 코루틴에게 돌아옴 

## kotlinx.coroutines.CoroutineScope.async 

- async는 launch랑 같은 역할 
- 유일한 차이는 launch가 job을 반환하는 반면 async는 deffered를 반환한다는 점 분 
- 심지어 Deffered는 Job을 상속한 클래스라 launch 대신 async를 사용해도 아무 문제가 없다 
- Deffered와 Job의 차이는 
  - Job은 아무런 파라미터가 없는 반면 Deffered 는 파라미터가 있는 제네릭 타입이라는 점과 
  - Deffered 안에는 await 함수가 정의돼 있다는 점 
  - Deffered 의 타입 파라미터는 바로 Deffered 코루틴이 계산을 하고 돌려주는 값의 타입이다 
  - Job 은 Unit을 돌려주는 Deffered<Unit> 이라고 생가할 수도 있을 것 
- async는 코드 블록을 비동기로 실행할 수도 있고
  - (제공하는 코루틴 컨텍스트에 따라 여러 스레드 사용 / 한 스레드 안에서 제어만 왔다갔다)
- async 가 반환하는 Deffered 의 await를 사용해서 코루틴이 결과값을 내놓을 때까지 기다렸다가 값을 얻어낼 수 있다 
```kotlin
fun sumAll() {
    runBlocking {
        val d1 = async { delay(1000L); 1 }
        log("after async(d1)")
        val d2 = async { delay(2000L); 2 }
        log("after async(d2)")
        val d3 = async { delay(3000L); 3 }
        log("after async(d3)")

        log("1+2+3 = ${d1.await() + d2.await() + d3.await()}")
        log("after await all & add")

    }
}
```
이 코드를 실행한 결과 
```
16:17:11.771:Thread[main,5,main]:after async(d1)
16:17:11.773:Thread[main,5,main]:after async(d2)
16:17:11.773:Thread[main,5,main]:after async(d3)
16:17:14.780:Thread[main,5,main]:1+2+3 = 6
16:17:14.780:Thread[main,5,main]:after await all & add
```
- 잘 살펴보면 d1, d2, d3를 하나하나 순서대로 (병렬 처리에서 이런 경우를 직렬화해 실행한다고 한다) 실행하면 
- 총 6초 이상이 걸려야 하지만 6(6000밀리초)이라는 결과를 얻을 때까지 총 3초가 걸렸음을 알 수 있다
- 또한 async로 코드를 실행하는데 시간이 거의 걸리지 않았음을 알 수 있다 
- 그럼에도 불구하고 스레드를 여럿 사용하는 병렬 처리와 달리 모든 async 함수들이 **메인 스레드 안에서 실행됨을 알 수 있다** 
- 이부분이 async/await와 스레드를 사용한 병렬처리의 큰 차이다 (쓰레드를 따로 만들지 않음)
- 비동기 코드가 늘어나면 빛을 발한다 
- 실행하려는 작업 시간이 얼마 걸리지 않거나 I/O에 의한 대기 시간이 크고 CPU 코어 수가 작아 동시에 실행할 수 있는 스레드 개수가 한정된 경우에는 특히 코루틴과 일반 스레드를 사용한 비동기 처리 사이에 차이가 커진다 

## E.2.2 코루틴 컨택스트와 디스패처 
- launch, asnyc 등은 모두 CoroutineScope의 확장 함수다 
- 그런데 CoroutineScope 는 CoroutineContext 타입의 필드 하나만 들어간다 
- 사실 CoroutineScope는 CoroutineContext 필드를 launch 등의 확장 함수 내부에서 사용하기 위한 매개체 역할만을 담당 
- 원한다면 launch 등에 CoroutineContext를 넘길 수도 있다는 점에서 실제로 CoroutineScope 보다 CoroutineContext가 더 중요한 의미가 있음을 유추 
- 
- CoroutineContext는 실제로 코루틴이 실행 중인 여러 작업(Job 타입)과 디스패처를 저장하는 일종의 맵이라 할 수 있다 
- 코틀린 런타임이 이 CoroutineContext를 사용해서 다음에 실행할 작업을 선정하고 어떻게 스레드에 배정할지에 대한 방법을 결정 
```kotlin
 runBlocking {
        launch {
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) { // 특정 스레드에 종속되지 않음 : 메인 스레드 사용
            println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {// 기본 디스패처 사용
            println("Default : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(newSingleThreadContext("MyOwnThread")) {// 새 스레드 사용
            println("newSingleThreadContext : I'm working in thread ${Thread.currentThread().name}")
        }
    }
```
```
Unconfined : I'm working in thread main
Default : I'm working in thread DefaultDispatcher-worker-1
newSingleThreadContext : I'm working in thread MyOwnThread
main runBlocking: I'm working in thread main
```
- 같은 launch를 사용하더라도 전달하는 컨텍스트에 따라 서로 다른 스레드 상에서 코루틴이 실행됨을 알 수 있다 
```kotlin
TODO() 디스패처 뭔지 잘 모르겠음 
```

## E.2.3 코루틴 빌더와 일시 중단 함수 
- launch, async, runBlocking은 모두 코루틴 빌더 
- 코루틴을 만들어주는 함수 
- kotlinx-coroutines-core 모듈이 제공하는 코루틴 빌더는 아래 2가지가 더 있음 
- produce
  - 정해진 채널로 데이터를 스트림으로 보내는 코루틴을 만든다 
  - 이 함수는 ReceiveChannel<>을 반환한다 
  - 그 채널로부터 메시지를 전달 받아 사용할 수 있다 
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html
- actor
  - 정해진 채널로 메시지를 받아 처리하는 액터를 코루틴으로 만든다 
  - 이함수가 반환하는 SendChannel<> 채널의 send() 메서드를 통해 액터에게 메시지를 보낼 수 있다 
  - https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/actor.html
```kotlin
// TODO() produce, actor 모르겠음 
```

일시중단 함수 
- delay() 와 yield()는 코루틴 안에서 특별한 의미를 갖는 함수 
- 이런 함수를 일시중단 함수라고 한다 suspending 
- delay() 와 yield() 외에 아래 함수들이 kotlinx-coroutines-core 모듈의 최상위에 정의된 일시 중단 함수
  - withContext : 다른 컨텍스트로 코루틴을 전환한다 
  - withTimeout : 코루틴이 정해진 시간 안에 실행되지 않으면 예외를 발생시키게 한다 
  - withTimeoutOrNull : 코루틴이 정해진 시간 안에 실행되지 않으면 null 반환 
  - awaitAll : 모든 작업의 성공을 기다린다. 작업 중 어느 하나가 예외로 실패하면 awaitAll도 그 예외로 실패한다 
  - joinAll : 모든 작업이 끝날 때까지 현재 작업을 일시 중단한다 

# E.3 suspend 키워드와 코틀린의 일시 중단 함수 컴파일 방법
- 코루틴이 아닌 일반 함수 속에서 delay()나 yield를 쓰면 어떤 일이 벌어질까?
- 일시 중단 함수를 코루틴이나 일시 중단 함수가 아닌 함수에서 호출하는 것은 컴파일러 수준에서 금지된다 
  - Suspend function 'yieldThreeTimes' should be called only from a coroutine or another suspend function
- 그렇다면 일시 중단 함수는 어떻게 만들 수 있을까?
- 코틀린은 코루틴 지원을 위해 suspend 라는 키워드를 제공한다. 
- 함수 정의의 fun 앞에 suspend 를 넣으면 일시 중단 함수를 만들 수 있다 
- 예를 들어 launch 시 호출할 코드가 복잡하다면 별도의 suspend 함수를 정의해 호출하는 것도 가능하다
````kotlin
suspend fun yieldThreeTimes() {
    log("1")
    delay(1000L)
    yield()
    log("2")
    delay(1000L)
    log("3")
    delay(1000L)
    yield()
    log("4")
}

fun suspendExample() {
    GlobalScope.launch { yieldExample() }
}
````
- suspend 함수는 어떻게 작동하는 것일까?
  - 예를 들어 일시 중단 함수 안에서 yield()를 해야 하는 경우 어떤 동작이 필요할지 생각해보자 
  - 코루틴에 진입할 때와 코루틴에서 나갈 때 코루틴이 실행 중이던 상태를 저장하고 복구하는 등의 작업을 할 수 있어야 한다 
  - 현재 실행 중이던 위치를 저장하고 다시 코루틴이 재개될 때 해당 위치부터 실행을 재개할 수 있어야 한다 
  - 다음에 어떤 코루틴을 실행할지 결정한다 
- 이 세가지 중 마지막 동작은 코루틴 컨텍스트에 있는 디스패처에 의해 수행된다 
```kotlin
//Todo 디스패처는 그러면 컨텍스마다 있는 것인가? 그러면 협력은 어떻게 하지? 
```
- 일시 중단 함수를 컴파일하는 컴파일러는 앞의 두 가지 작업을 할 수 있는 코드를 생성해 내야 한다 
- 이 때 코틀린은 컨티뉴에이션 패싱 스타일 CPS continuation passing style 변환과 상태기계 state machine을 활용해 코드를 생성해낸다 
- cps 변환은 프로그램 실행 중 특정 시점 이후에 진행해야 하는 내용을 별도의 함수로 뽑고 (이런 함수를 컨티뉴에이션 continuation이라고 부른다)
- 그 함수에게 현재 시점까지 실행현 결과를 넘겨서 스스로 처리하게 만드는 소스코드 기술이다 
- cps를 사용하는 경우 프로그램이 다음에 해야 할 일이 항상 컨테뉴에이션이라는 함수 형태로 전달되므로, 나중에 할 일을 명확히 알 수 있고, 그 컨티뉴에이션에 넘겨야 할 값이 무엇인지도 명확하게 알 수 있기 때문에 
- 프로그램이 실행 중이던 특정 시점의 맥락을 잘 저장했다가 필요할 때 다시 재개할 수 있다 
- 어떤 면에서 cps 는 콜백 스타일 프로그래밍과도 유사하다 
- 다음과 같이 suspend가 붙은 함수가 있다고 가정하자 
```kotlin
suspend fun example(v: Int): Int {
  return v * 2
}
```
- 코틀린 컴파일러는 이 함수를 컴파일하면서 뒤에 Continuation을 인자로 만들어 붙여 준다 
```java
public static final Object example(int v, @Notnull Continuation varl)
```
- 그리고 이 함수를 호출할 때는 함수 호출이 끝난 후 수행해야 할 작업을 varl에 Continuation 으로 전달하고 
- 함수 내부에서는 필요한 모든 일을 수행한 다음에 결과를 varl에 넘기는 코드를 추가한다 
  - 이 예제에서는 v*2를 인자로 Continuation을 호출하는 코드가 들어간다 
- cps를 사용하면 코루틴을 만들기 위해 필수적인 일시 중단 함수를 만드는 문제가 쉽게 해결될 수 있다 
- 다만 모든 코드를 전부 cps로만 변환하면 지나치게 많은 중간함수들이 생길 수 있으므로 
- 상태기계를 적당히 사용해서 코루틴이 제어를 다른 함수에 넘겨야 하는 시점에서만 컨티뉴에이션이 생기도록 만들 수 있다 
```kotlin
// todo 컨티뉴에이션과 상태기계 study. 이해 잘 안간다 
// cps 를 사용하면 해당 함수가 다 끝난 다음에 어떤 콜백 함수 같은 것으로 결과를 전달한다는 것인데 
// 함수 수행 도중에 상태를 저장하고 전달해야 하는 것 아닌가? 
```
