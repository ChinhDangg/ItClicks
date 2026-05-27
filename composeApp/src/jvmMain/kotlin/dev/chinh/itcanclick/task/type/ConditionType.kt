package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.condition.PixelExactCondition
import dev.chinh.itcanclick.task.condition.PixelSimilarCondition
import dev.chinh.itcanclick.task.condition.TextCondition
import kotlinx.serialization.Serializable

@Serializable
enum class ConditionType(
    override val typeName: String
) : TaskType {
    PIXEL_EXACT_MATCH(PixelExactCondition.BEAN_NAME),
    PIXEL_SIMILAR_MATCH(PixelSimilarCondition.BEAN_NAME),
    TEXT_MATCH(TextCondition.BEAN_NAME)
}