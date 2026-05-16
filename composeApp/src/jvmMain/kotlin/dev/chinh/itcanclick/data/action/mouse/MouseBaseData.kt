package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.task.action.mouse.MouseBaseInfo
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseBaseData(
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
