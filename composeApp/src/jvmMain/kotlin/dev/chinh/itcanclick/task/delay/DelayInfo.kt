package dev.chinh.itcanclick.task.delay

import dev.chinh.itcanclick.data.delay.DelayData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.OtherType
import dev.chinh.itcanclick.task.type.TaskType

data class DelayInfo(
    val delay: Long,
    override var result: Result?
) : TaskInfo<DelayInfo> {

    override val id: String = "delay-$delay"
    override val name: String = "Delay"
    override val taskType: TaskType = OtherType.DELAY

    override fun getSelf(): DelayInfo = this

    override fun getTaskData(): DelayData {
        return DelayData(delay)
    }
}
