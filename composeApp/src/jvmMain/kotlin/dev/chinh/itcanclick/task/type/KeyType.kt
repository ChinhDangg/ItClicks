package dev.chinh.itcanclick.task.type

import kotlinx.serialization.Serializable

@Serializable
enum class KeyType : TaskType {
    KEY_CLICK,
    KEY_PRESS,
    KEY_RELEASE
}