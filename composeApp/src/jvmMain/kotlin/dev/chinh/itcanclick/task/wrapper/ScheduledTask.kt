package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result

class ScheduledTask : TaskWrapper<ScheduledTaskInfo>() {

    override suspend fun execute(taskInfo: ScheduledTaskInfo): Result {
        return runScheduled()
    }

    fun runScheduled(): Result {
        TODO()
    }
}