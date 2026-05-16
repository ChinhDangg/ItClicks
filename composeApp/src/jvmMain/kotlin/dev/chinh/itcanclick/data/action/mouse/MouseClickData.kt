package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.task.action.mouse.MouseClickInfo
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseClickData(
    var numClicks: Int = 1,
    var rect: Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    override val id: String,
    override val name: String
) : MouseData<MouseClickInfo> {

    override var taskType: MouseType = MouseType.MOUSE_CLICK

    override fun getMinimalTaskInfo(): MouseClickInfo {
        return MouseClickInfo(
            numClicks, delay, rect, isExact,
            id, name,
            null
        )
    }

}
