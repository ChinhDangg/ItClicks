package dev.chinh.itcanclick.task.type

import dev.chinh.itcanclick.task.condition.PixelExactCondition
import dev.chinh.itcanclick.task.condition.PixelSimilarCondition
import dev.chinh.itcanclick.task.condition.TextCondition
import kotlinx.serialization.Serializable

@Serializable
enum class ConditionType(
    override val typeName: String,
    override val displayName: String
) : TaskType {
    PIXEL_EXACT_MATCH(PixelExactCondition.BEAN_NAME, "Pixel Exact Match"),
    PIXEL_SIMILAR_MATCH(PixelSimilarCondition.BEAN_NAME, "Pixel Similar Match"),
    TEXT_MATCH(TextCondition.BEAN_NAME, "Text Match")
}