package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.delay.Delay
import kotlinx.serialization.Serializable

@Serializable
enum class OtherType(
    override val typeName: String
) : TaskType{
    DELAY(Delay.BEAN_NAME)
}