package dev.chinh.itcanclick.task.delay

import dev.chinh.itcanclick.task.TaskInfo

data class DelayInfo(
    val delay: Long,
    val taskExecutor: Delay
) : TaskInfo<DelayInfo>(taskExecutor) {

    override fun getSelf(): DelayInfo = this
}
