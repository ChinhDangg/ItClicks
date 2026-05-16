package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.data.action.key.KeyData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.type.KeyType

data class KeyInfo(
    val keyCode: Int,
    val delay: Int,
    override val id: String,
    override val name: String,
    override val taskType: KeyType,
    override var result: Result?
) : ActionInfo<KeyInfo> {

    override fun getSelf(): KeyInfo = this

    override fun getTaskData(): KeyData {
        return KeyData(keyCode, delay, id, name, taskType)
    }
}