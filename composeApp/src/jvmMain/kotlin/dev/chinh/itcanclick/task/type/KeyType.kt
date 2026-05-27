package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.action.key.KeyClick
import dev.chinh.itcanclick.task.action.key.KeyPress
import dev.chinh.itcanclick.task.action.key.KeyRelease
import kotlinx.serialization.Serializable

@Serializable
enum class KeyType(
    override val typeName: String
) : TaskType {
    KEY_CLICK(KeyClick.BEAN_NAME),
    KEY_PRESS(KeyPress.BEAN_NAME),
    KEY_RELEASE(KeyRelease.BEAN_NAME)
}