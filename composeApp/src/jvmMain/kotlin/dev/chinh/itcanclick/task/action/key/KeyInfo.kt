package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.data.action.key.KeyData
import dev.chinh.itcanclick.perform.TaskRegisterService
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.type.KeyType

data class KeyInfo(
    val keyCode: Int,
    val delay: Int,
    override val name: String,
    override val taskType: KeyType,
) : ActionInfo<KeyInfo> {

    override val id: String = TaskRegisterService.generateId()
    override var result: Result? = null

    override fun getSelf(): KeyInfo = this

    override fun getTaskData(): KeyData {
        return KeyData(keyCode, delay, id, name, taskType)
    }
}