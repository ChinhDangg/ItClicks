//package dev.chinh.itcanclick
//
//import dev.chinh.itcanclick.perform.TaskRegisterService
//import dev.chinh.itcanclick.task.TaskRegistry
//import dev.chinh.itcanclick.task.action.key.KeyInfo
//import dev.chinh.itcanclick.task.delay.DelayInfo
//import dev.chinh.itcanclick.task.type.KeyType
//import dev.chinh.itcanclick.task.type.WrapperType
//import dev.chinh.itcanclick.task.wrapper.LoopTaskInfo
//import dev.chinh.itcanclick.task.wrapper.NormalWrapper
//import dev.chinh.itcanclick.task.wrapper.NormalWrapperInfo
//import kotlinx.coroutines.runBlocking
//import org.springframework.boot.CommandLineRunner
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.builder.SpringApplicationBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.stereotype.Component
//import java.awt.event.KeyEvent
//
//@SpringBootApplication
//class ItCanClickApplication
//
//@Component
//class MyRunner(
//    private val taskRegisterService: TaskRegisterService,
//    private val taskRegistry: TaskRegistry
//) : CommandLineRunner {
//    private suspend fun performTask() {
//        val wrapper = NormalWrapperInfo(
//            mutableListOf(
//                DelayInfo(3000, null),
//                KeyInfo(KeyEvent.VK_A, 50, taskRegisterService.generateId(), "Test", KeyType.KEY_CLICK, null)
//            ),
//            "Test",
//            "Test",
//            null
//        )
//        val loopWrapper = LoopTaskInfo(
//            3,
//            mutableListOf(
//                wrapper
//            ),
//            "Test",
//            "Test",
//            null
//        )
//        taskRegistry.getTask<LoopTaskInfo>(WrapperType.LOOPED_TASK).execute(loopWrapper)
//    }
//
//    override fun run(vararg args: String?) {
//        runBlocking {
//            println("Starting tasks...")
//            performTask()
//            println("All tasks finished.")
//        }
//    }
//}
//
//fun main(args: Array<String>) {
//    SpringApplicationBuilder(ItCanClickApplication::class.java)
//            .headless(false)
//            .run(*args)
//}