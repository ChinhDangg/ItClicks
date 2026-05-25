package dev.chinh.itcanclick.task.delay

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class Delay : Task<DelayInfo> {

    override suspend fun execute(taskInfo: DelayInfo): Result {
        delayTask(taskInfo)
        return Result(ResultStatus.PASS, "Delayed ${taskInfo.delay} ms")
    }

    suspend fun delayTask(delayInfo: DelayInfo) {
        delay(delayInfo.delay)
    }
}