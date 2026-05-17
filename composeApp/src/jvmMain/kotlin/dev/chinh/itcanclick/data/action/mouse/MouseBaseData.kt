package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.task.action.mouse.MouseBaseInfo
import dev.chinh.itcanclick.task.type.MouseType
import dev.chinh.itcanclick.task.util.RectangleSerializer
import kotlinx.serialization.Serializable
import java.awt.Rectangle

@Serializable
data class MouseBaseData(
    @Serializable(with = RectangleSerializer::class)
    var rect: Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    override val id: String,
    override val name: String,
    override var taskType: MouseType
) : MouseData<MouseBaseInfo> {

    override fun getMinimalTaskInfo(): MouseBaseInfo {
        return MouseBaseInfo(
            delay, rect, isExact,
            id, name, taskType,
            null
        )
    }
}
