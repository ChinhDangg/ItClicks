package dev.chinh.itcanclick.data.mouse

import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.action.mouse.MouseAction
import dev.chinh.itcanclick.task.action.mouse.MouseBaseInfo
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseBaseData(
    var rect: Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    override var taskType: MouseType
) : MouseData<MouseBaseInfo> {

    override fun getTaskInfo(): MouseBaseInfo {
        return MouseBaseInfo(
            delay, rect, isExact,
            TaskRegistry.getTask(taskType) as MouseAction<MouseBaseInfo>,
            null
        )
    }
}
