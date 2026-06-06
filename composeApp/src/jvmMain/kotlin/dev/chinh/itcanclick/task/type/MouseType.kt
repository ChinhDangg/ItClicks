package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.action.mouse.MouseClick
import dev.chinh.itcanclick.task.action.mouse.MouseMove
import dev.chinh.itcanclick.task.action.mouse.MousePress
import dev.chinh.itcanclick.task.action.mouse.MouseRelease
import kotlinx.serialization.Serializable

@Serializable
enum class MouseType(
    override val typeName: String,
    override val displayName: String
) : TaskType {
    MOUSE_MOVE(MouseMove.BEAN_NAME, "Mouse Move"),
    MOUSE_CLICK(MouseClick.BEAN_NAME, "Mouse Click"),
    MOUSE_PRESS(MousePress.BEAN_NAME, "Mouse Press"),
    MOUSE_RELEASE(MouseRelease.BEAN_NAME, "Mouse Release")
}