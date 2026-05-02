package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result

class ScheduledTask : TaskWrapper<ScheduledTaskInfo>() {

    override suspend fun perform(taskInfo: ScheduledTaskInfo): Result {
        return runScheduled()
    }

    fun runScheduled(): Result {
        TODO()
    }
}