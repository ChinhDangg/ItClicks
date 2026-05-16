package dev.chinh.itcanclick.data.delay

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.delay.DelayInfo
import dev.chinh.itcanclick.task.type.OtherType
import dev.chinh.itcanclick.task.type.TaskType

data class DelayData(
    val delay: Long,
) : TaskData<DelayInfo> {

    override val id: String = "delay-$delay"
    override val name: String = "Delay"
    override val taskType: TaskType = OtherType.DELAY

    override fun getTaskInfo(): DelayInfo {
        return getMinimalTaskInfo()
    }

    override fun getMinimalTaskInfo(): DelayInfo {
        return DelayInfo(delay, null)
    }
}
