package dev.chinh.itcanclick.task.type

import kotlinx.serialization.Serializable

@Serializable
enum class WrapperType : TaskType{
    LOOPED_TASK,
    SCHEDULED_TASK,
    NORMAL_WRAPPER
}