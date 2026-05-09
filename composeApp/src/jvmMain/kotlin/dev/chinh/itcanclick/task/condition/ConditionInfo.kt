package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.TaskInfo

data class ConditionInfo(
    var rect: java.awt.Rectangle,
    val originalImage: java.awt.image.BufferedImage,
    val similarity: Float,
    val isCore: Boolean,
    val globalSearch: Boolean,
    val passingResult: Boolean,
    val taskExecutor: Condition
) : TaskInfo<ConditionInfo>(taskExecutor) {

    override fun getSelf(): ConditionInfo = this
}
