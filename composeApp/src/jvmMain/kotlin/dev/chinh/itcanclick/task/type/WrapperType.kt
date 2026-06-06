package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.wrapper.LoopTask
import dev.chinh.itcanclick.task.wrapper.NormalWrapper
import dev.chinh.itcanclick.task.wrapper.ScheduledTask
import kotlinx.serialization.Serializable

@Serializable
enum class WrapperType(
    override val typeName: String,
    override val displayName: String
) : TaskType{
    LOOPED_TASK(LoopTask.BEAN_NAME, "Loop"),
    NORMAL_WRAPPER(NormalWrapper.BEAN_NAME, "Normal Wrapper"),
    SCHEDULED_TASK(ScheduledTask.BEAN_NAME, "Schedule")
}