package com.lzy.demo.kotlin.coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalTime
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis


/**
 * 线程是由系统调度的,线程切换或线程阻塞的开销都比较大
 * 协程就像非常轻量级的线程,它依赖于线程,但是协程挂起时不需要阻塞线程
 * 协程是由开发者控制的,所以协程也像用户态的线程,非常轻量级,一个线程中可以创建任意个协程
 *
 * 协程感觉类似java的Future
 * 比较不一样的就是delay函数,因为它只是挂起协程不会阻塞线程
 * 但是delay函数应该不会在生产上使用,所以目前还不知道协程的使用场景,可能是安卓上比较多(用来等待)
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoroutineTest {

    @BeforeAll
    fun init() {
        // 添加-Dkotlinx.coroutines.debug打开线程的时候会带协程名
        System.setProperty("kotlinx.coroutines.debug", "")
    }

    /**
     * 协程的基本使用
     */
    @Test
    fun testBase() {
        var sum = 0
        val jobs = mutableListOf<Deferred<Int>>()
        // runBlocking用来执行协程,然后阻塞当前线程
        runBlocking {
            // 50个任务交给50个协程去执行,每个协程需要花费500毫秒,全部完成也差不多是500毫秒(这边就跟线程完全不一样了,虽然协程也是交给线程执行)
            repeat(50) {
                // launch启动一个协程,默认使用当前线程执行,返回kotlinx.coroutines.Job,不能拿到协程的返回值
                launch {
                    // 这边的协程都使用同一个线程执行,所以没有线程安全问题
                    sum++
                    log("launch coroutine start")
                    // delay不会阻塞线程,只是会挂起协程,这边使用delay来表示耗时操作
                    // 当协程被挂起时,协程所在的线程会去执行该线程中的其它协程
                    delay(500L)
                    log("launch coroutine end")
                }
                // async启动一个协程,默认使用当前线程执行,返回kotlinx.coroutines.Deferred,可以拿到协程的返回值
                val job = async {
                    log("async coroutine start")
                    delay(500L)
                    log("async coroutine end")
                    // async的最后一行相当于返回值
                    it
                }
                jobs.add(job)
            }
            // 在runBlocking使用launch/async启动的协程都需要完全执行结束,否则会挂起当前协程,相当于使用了join
        }
        log("main exec")
        println("sum:$sum")
        // 获取async的结果
        jobs.map { it.getCompleted() }.forEach { print(it) }
    }

    /**
     * 使用线程,用来跟协程比较
     */
    @Test
    fun testThread() {
        // 这边使用线程,availableProcessors获取可用的核心线程数(开启超线程可能大于cpu核心数)
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        var sum = 0
        // 有50个任务交给线程池去执行,每个线程需要花费500毫秒,全部完成大概需要processors/500毫秒
        repeat(50) {
            executor.submit {
                // 线程有线程安全问题
                sum++
                log("thread start")
                Thread.sleep(500L)
                log("thread end")
            }
        }
        log("main exec")
        Thread.sleep(3000L)
        println("sum:$sum")
    }

    /**
     * 在协程下执行挂起函数
     */
    @Test
    fun testSuspendFunc() {
        // 直接在协程下执行挂起函数
        runBlocking {
            val time = measureTimeMillis {
                // 这边是直接在当前协程下执行挂起函数的,所以是同步执行的
                val ret1 = suspendWithReturn("func1")
                val ret2 = suspendWithReturn("func2")
                suspendWithoutReturn("func3")
                log("ret1:$ret1,ret2:$ret2")
            }
            log("time:$time")
        }
        // 在协程下使用launch/async执行挂起函数
        runBlocking {
            val time = measureTimeMillis {
                // 这边是使用async和launch创建协程来执行挂起函数的,因此这么三个协程是同时执行的
                val ret1 = async { suspendWithReturn("func async1") }
                val ret2 = async { suspendWithReturn("func async2") }
                launch { suspendWithoutReturn("func launch") }
                // 这边使用await可以挂起直到协程执行完成
                log("ret1:${ret1.await()},ret2:${ret2.await()}")
            }
            log("time:$time")
        }
    }


    /**
     * 协程调试器
     * 协程总是运⾏在某个上下文中(CoroutineContext)(上下文是一些元素的集合,主要元素是协程的Job和调试器(CoroutineDispatcher))
     * 协程上下⽂包含⼀个协程调度器,它确定了协程在哪个线程执行
     * launch/async可以接收⼀个CoroutineContext,可以被⽤来显式地为⼀个新协程指定⼀个调度器
     */
    @Test
    fun testDispatchers() {
        runBlocking {
            // 可以执行协程的名称,方便调试
            launch(CoroutineName("launch")) {
                // 它从启动了它的CoroutineScope中继承了上下⽂(以及调度器)
                log("launch")
            }
            // 需要为上下文定义多个元素,可以使用+
            launch(Dispatchers.Unconfined + CoroutineName("launch(Dispatchers.Unconfined)")) {
                // 非受限调试器
                // 先使用当前线程执行到挂起点
                log("launch(Dispatchers.Unconfined) before")
                delay(500)
                // 恢复后,使用某个线程继续执行,该线程由调用的挂起函数确定
                log("launch(Dispatchers.Unconfined) after")
            }
            launch(Dispatchers.Default) {
                // 使用默认的调试器,相当于GlobalScope.launch
                // 使用共享的线程池(默认cpu核心线程)
                log("launch(Dispatchers.Default)")
            }
            launch(Dispatchers.IO) {
                // 使用共享的线程池,主要用于IO操作(默认64个线程)
                log("launch(Dispatchers.IO)")
            }
            launch(newSingleThreadContext("newSingleThread")) {
                // 在创建的新线程中执行
                log(" launch(newSingleThreadContext())")
            }
        }
    }

    /**
     * 延迟启动
     */
    @Test
    fun testLazy() = runBlocking {
        // runBlocking可以直接包装函数体
        // 把async/launch置为延迟启动
        val ret1 = async(start = CoroutineStart.LAZY) { suspendWithReturn("func async") }
        val ret2 = launch(start = CoroutineStart.LAZY) { suspendWithoutReturn("func launch") }
        log("testLazy before delay")
        delay(500L)
        log("testLazy before after")
        // 只有在主动调用await()或者start()方法时才会启动协程
        ret1.await()
        ret2.start()
    }


    /**
     * 取消协程
     */
    @Test
    fun testCancel() = runBlocking {
        val job = launch {
            repeat(1000) {
                log("coroutine exec")
                delay(500L)
            }
        }
        delay(2000L)
        log("main cancel coroutine")
        // 使用cancel可以取消协程
        job.cancel()
        job.join()
        log("main end")
    }


    /**
     * 使用GlobalScope
     */
    @Test
    fun testGlobalScope() {
        // GlobalScope是在全局作用域内启动了一个协程,它的生命周期只受整个应用程序的生命周期的限制,只要主线程没有退出,协程任务还没执行完,就会一直执行下去
        var sum = 0
        // 50个任务交给50个协程去执行,每个协程需要花费500毫秒,全部完成也差不多是500毫秒
        repeat(50) {
            // GlobalScope是交给名为DefaultDispatcher的线程池(线程数默认为Runtime.getRuntime().availableProcessors())去执行的
            GlobalScope.launch {
                // 交给其它线程池执行的协程也有线程安全问题
                sum++
                log("coroutine start")
                delay(500L)
                log("coroutine end")
            }
        }
        log("main exec")
        Thread.sleep(3000L)
        println("sum:$sum")
    }

    /**
     * join会挂起当前协程直到子协程执行结束
     */
    @Test
    fun testJoin() = runBlocking {
        log("runBlocking start")
        val job = GlobalScope.launch {
            // 使用线程池来执行此协程
            log("coroutine start")
            delay(1000L)
            log("coroutine end")
        }

        log("runBlocking last")
        // 会挂起直到协程执行结束
        job.join()
    }


    /**
     * 简单的挂起函数,只能在协程中被调用
     */
    suspend fun suspendWithReturn(name: String): String {
        log("suspendWithReturn:$name start")
        delay(1000L)
        log("suspendWithReturn:$name end")
        return "$name success"
    }

    suspend fun suspendWithoutReturn(name: String) {
        log("suspendWithoutReturn:$name start")
        delay(1000L)
        log("suspendWithoutReturn:$name end")
    }

    fun log(msg: String) {
        val threadName = Thread.currentThread().name
        val time = LocalTime.now()
        println("$time   [$threadName]   $msg")
    }
}