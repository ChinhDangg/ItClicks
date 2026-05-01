package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.TaskType

data class ScheduledTaskInfo(
    val delay: Long,
    val taskExecutor: TaskWrapper<ScheduledTaskInfo>,
) : TaskWrapperInfo<ScheduledTaskInfo>(TaskType.SCHEDULED_TASK, taskExecutor) {

    override fun getSelf(): ScheduledTaskInfo = this
}
