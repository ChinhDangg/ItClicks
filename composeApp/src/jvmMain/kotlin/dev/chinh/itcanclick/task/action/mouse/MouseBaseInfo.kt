package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.data.action.mouse.MouseBaseData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.type.MouseType

data class MouseBaseInfo(
    val delay: Int,
    override val id: String,
    override val name: String,
    override val taskType: MouseType,
    override var result: Result?
) : MouseInfo<MouseBaseInfo> {

    override fun getSelf(): MouseBaseInfo = this

    override fun getTaskData(): MouseBaseData {
        return MouseBaseData(
            delay, id, name, taskType
        )
    }
}