package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.data.action.mouse.MouseClickData
import dev.chinh.itcanclick.perform.TaskRegisterService
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.type.MouseType

data class MouseClickInfo(
    val numClicks: Int,
    val delay: Int,
    override val name: String
) : MouseInfo<MouseClickInfo> {

    override val id: String = TaskRegisterService.generateId()
    override val taskType: MouseType = MouseType.MOUSE_CLICK
    override var result: Result? = null

    override fun getSelf(): MouseClickInfo = this

    override fun getTaskData(): MouseClickData {
        return MouseClickData(numClicks, delay, id, name)
    }
}