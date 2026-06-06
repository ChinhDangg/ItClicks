package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.action.key.KeyClick
import dev.chinh.itcanclick.task.action.key.KeyPress
import dev.chinh.itcanclick.task.action.key.KeyRelease
import kotlinx.serialization.Serializable

@Serializable
enum class KeyType(
    override val typeName: String,
    override val displayName: String
) : TaskType {
    KEY_CLICK(KeyClick.BEAN_NAME, "Key Click"),
    KEY_PRESS(KeyPress.BEAN_NAME, "Key Press"),
    KEY_RELEASE(KeyRelease.BEAN_NAME, "Key Release")
}