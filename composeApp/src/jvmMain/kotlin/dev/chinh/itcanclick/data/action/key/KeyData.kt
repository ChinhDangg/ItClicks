package dev.chinh.itcanclick.data.action.key

import dev.chinh.itcanclick.data.action.ActionData
import dev.chinh.itcanclick.task.action.key.KeyInfo
import dev.chinh.itcanclick.task.type.KeyType
import kotlinx.serialization.Serializable

@Serializable
data class KeyData(
    var keyCode: Int,
    var delay: Int = 0,
    override val id: String,
    override val name: String,
    override var taskType: KeyType
) : ActionData<KeyInfo> {

    override fun getMinimalTaskInfo(): KeyInfo {
        return KeyInfo(
            keyCode, delay,
            name, taskType, id
        )
    }
}