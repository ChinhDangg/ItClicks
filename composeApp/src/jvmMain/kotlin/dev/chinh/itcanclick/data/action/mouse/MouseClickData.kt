package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.task.action.mouse.MouseClickInfo
import dev.chinh.itcanclick.task.type.MouseType

data class MouseClickData(
    var numClicks: Int = 1,
    var delay: Int = 50,
    override val id: String,
    override val name: String
) : MouseData<MouseClickInfo> {

    override var taskType: MouseType = MouseType.MOUSE_CLICK

    override fun getMinimalTaskInfo(): MouseClickInfo {
        return MouseClickInfo(
            numClicks, delay, name, id
        )
    }

}
