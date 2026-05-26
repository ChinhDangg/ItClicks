package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.task.action.mouse.MouseMoveInfo
import dev.chinh.itcanclick.task.type.MouseType
import dev.chinh.itcanclick.task.util.RectangleSerializer
import kotlinx.serialization.Serializable
import java.awt.Rectangle

@Serializable
data class MouseMoveData(
    @Serializable(with = RectangleSerializer::class)
    var rect: Rectangle,
    var isExact: Boolean = false,
    override val id: String,
    override val name: String,
) : MouseData<MouseMoveInfo> {

    override var taskType: MouseType = MouseType.MOUSE_MOVE

    override fun getMinimalTaskInfo(): MouseMoveInfo {
        return MouseMoveInfo(
            rect, isExact, id, name,
            null
        )
    }
}
