package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.data.action.mouse.MouseBaseData
import dev.chinh.itcanclick.perform.TaskRegisterService
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.type.MouseType

data class MouseBaseInfo(
    val delay: Int,
    override val name: String,
    override val taskType: MouseType,
    override val id: String = TaskRegisterService.generateId()
) : MouseInfo<MouseBaseInfo> {

    override var result: Result? = null

    override fun getSelf(): MouseBaseInfo = this

    override fun getTaskData(): MouseBaseData {
        return MouseBaseData(
            delay, id, name, taskType
        )
    }
}