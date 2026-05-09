package dev.chinh.itcanclick.data.mouse

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.action.mouse.MouseAction
import dev.chinh.itcanclick.task.action.mouse.MouseClickInfo
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseClickData(
    var numClicks: Int = 1,
    var rect: Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    override var taskType: MouseType
) : MouseData<MouseClickInfo> {

    override fun getTaskInfo(): TaskInfo<MouseClickInfo> {
        return MouseClickInfo(
            numClicks, delay, rect, isExact,
            TaskRegistry.getTask(taskType) as MouseAction<MouseClickInfo>,
            null
        )
    }

}
