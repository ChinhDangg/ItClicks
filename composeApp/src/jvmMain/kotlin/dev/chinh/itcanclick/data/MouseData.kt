package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.action.mouse.MouseAction
import dev.chinh.itcanclick.task.action.mouse.MouseInfo
import dev.chinh.itcanclick.task.type.MouseType

data class MouseData(
    var rect: java.awt.Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    var numClicks: Int = 1,
    var type: MouseType
) : ActionData<MouseInfo>(type) {

    override fun getTaskData(): MouseData {
        TODO("Not yet implemented")
    }

    override fun getTaskInfo(): MouseInfo {
        return MouseInfo(
            rect, isExact, delay, numClicks,
            TaskRegistry.getTask(type) as MouseAction
        )
    }

}
