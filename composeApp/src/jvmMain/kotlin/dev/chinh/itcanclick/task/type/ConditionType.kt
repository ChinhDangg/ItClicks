package dev.chinh.itcanclick.task.type

import kotlinx.serialization.Serializable

@Serializable
enum class ConditionType : TaskType{
    PIXEL_EXACT_MATCH,
    PIXEL_SIMILAR_MATCH,
    TEXT_MATCH
}