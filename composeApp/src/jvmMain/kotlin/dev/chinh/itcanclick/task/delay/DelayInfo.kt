package dev.chinh.itcanclick.task.delay

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskType

data class DelayInfo(
    val delay: Long,
    val taskExecutor: Delay
) : TaskInfo<DelayInfo>(TaskType.DELAY, taskExecutor) {

    override fun getSelf(): DelayInfo = this
}
