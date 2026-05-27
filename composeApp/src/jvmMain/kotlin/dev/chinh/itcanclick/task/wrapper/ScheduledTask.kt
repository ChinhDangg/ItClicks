package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskRegistry
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component(ScheduledTask.BEAN_NAME)
class ScheduledTask(
    @Lazy override val taskRegistry: TaskRegistry
) : TaskWrapper<ScheduledTaskInfo> {

    companion object { const val BEAN_NAME = "SCHEDULED" }

    override suspend fun execute(taskInfo: ScheduledTaskInfo): Result {
        return runScheduled()
    }

    fun runScheduled(): Result {
        TODO()
    }
}