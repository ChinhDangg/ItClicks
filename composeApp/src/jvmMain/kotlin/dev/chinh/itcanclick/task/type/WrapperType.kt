package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.wrapper.LoopTask
import dev.chinh.itcanclick.task.wrapper.NormalWrapper
import dev.chinh.itcanclick.task.wrapper.ScheduledTask
import kotlinx.serialization.Serializable

@Serializable
enum class WrapperType(
    override val typeName: String
) : TaskType{
    LOOPED_TASK(LoopTask.BEAN_NAME),
    NORMAL_WRAPPER(NormalWrapper.BEAN_NAME),
    SCHEDULED_TASK(ScheduledTask.BEAN_NAME)
}