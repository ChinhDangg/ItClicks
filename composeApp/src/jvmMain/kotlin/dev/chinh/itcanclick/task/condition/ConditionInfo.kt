package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.TaskInfo

data class ConditionInfo(
    var rect: java.awt.Rectangle,
    var originalImage: java.awt.image.BufferedImage,
    var conditionType: ConditionType,
    var similarity: Float = 0.85f,
    var isCore: Boolean = false,
    var globalSearch: Boolean = false,
    var passingResult: Boolean = false,
    val taskExecutor: Condition
) : TaskInfo<ConditionInfo>(taskExecutor) {

    override fun getSelf(): ConditionInfo = this
}
