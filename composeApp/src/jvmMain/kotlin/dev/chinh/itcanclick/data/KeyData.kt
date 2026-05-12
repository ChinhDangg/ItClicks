package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.action.key.KeyAction
import dev.chinh.itcanclick.task.action.key.KeyInfo
import dev.chinh.itcanclick.task.type.KeyType

data class KeyData(
    var keyCode: Int,
    var delay: Int = 0,
    override var taskType: KeyType
) : ActionData<KeyInfo> {

    override fun getTaskInfo(): KeyInfo {
        return getMinimalTaskInfo()
    }

    override fun getMinimalTaskInfo(): KeyInfo {
        return KeyInfo(
            keyCode, delay,
            TaskRegistry.getTask(taskType) as KeyAction,
            null
        )
    }
}