package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.TaskType
import dev.chinh.itcanclick.task.action.Action
import dev.chinh.itcanclick.task.action.ActionInfo

data class KeyInfo(
    val keyCode: Int,
    val delay: Int = 0,
    val type: TaskType,
    val taskExecutor: Action<KeyInfo>
) : ActionInfo<KeyInfo>(type, taskExecutor) {

    override fun getSelf(): KeyInfo = this
}