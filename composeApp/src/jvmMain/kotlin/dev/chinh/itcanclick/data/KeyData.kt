package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.action.key.KeyAction
import dev.chinh.itcanclick.task.action.key.KeyInfo
import dev.chinh.itcanclick.task.type.KeyType

data class KeyData(
    var keyCode: Int,
    var delay: Int = 0,
    var type: KeyType
) : ActionData<KeyInfo>(type) {

    override fun getTaskData(): TaskData<KeyInfo> {
        TODO("Not yet implemented")
    }

    override fun getTaskInfo(): TaskInfo<KeyInfo> {
        return KeyInfo(
            keyCode, delay, TaskRegistry.getTask(type) as KeyAction
        )
    }
}