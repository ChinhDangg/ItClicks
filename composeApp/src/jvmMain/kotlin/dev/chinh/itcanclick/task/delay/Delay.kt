package dev.chinh.itcanclick.task.delay

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component(Delay.BEAN_NAME)
class Delay : Task<DelayInfo> {

    companion object { const val BEAN_NAME = "DELAY" }

    override suspend fun execute(taskInfo: DelayInfo): Result {
        delayTask(taskInfo)
        return Result(ResultStatus.PASS, "Delayed ${taskInfo.delay} ms")
    }

    suspend fun delayTask(delayInfo: DelayInfo) {
        delay(delayInfo.delay)
        log("Delayed ${delayInfo.delay} ms")
    }
}