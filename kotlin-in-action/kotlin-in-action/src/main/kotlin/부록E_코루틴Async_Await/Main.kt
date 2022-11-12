package 부록E_코루틴Async_Await

import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)

fun log(msg: String) = println("${now()}:${Thread.currentThread()}:${msg}")

fun launchInGlobalScope() {
    GlobalScope.launch {
        log("coroutine started.")
    }
}

fun runBlockingExample() {
    runBlocking {
        launch {
            log("coroutine started.")
        }
    }
}

fun main(args: Array<String>) {
//    log("main() started")
//    launchInGlobalScope()
//    runBlockingExample()
//    yieldExample()
//    log("runBlockingExample() started")
//    log("launchInGlobalScope() started")
//    log("yieldExample() executed")
//    Thread.sleep(5000L)

//    log("main() terminated")
//    sumAll()

//    runBlocking {
//        launch {
//            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
//        }
//
//        launch(Dispatchers.Unconfined) { // 특정 스레드에 종속되지 않음 : 메인 스레드 사용
//            println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
//        }
//
//        launch(Dispatchers.Default) {// 기본 디스패처 사용
//            println("Default : I'm working in thread ${Thread.currentThread().name}")
//        }
//
//        launch(newSingleThreadContext("MyOwnThread")) {// 새 스레드 사용
//            println("newSingleThreadContext : I'm working in thread ${Thread.currentThread().name}")
//        }
//    }
//    suspendExample()
//    yieldThreeTimes()
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




