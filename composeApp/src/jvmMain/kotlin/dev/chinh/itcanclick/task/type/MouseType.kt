package dev.chinh.itcanclick.task.type

import kotlinx.serialization.Serializable

@Serializable
enum class MouseType : TaskType {
    MOUSE_MOVE,
    MOUSE_CLICK,
    MOUSE_PRESS,
    MOUSE_RELEASE
}