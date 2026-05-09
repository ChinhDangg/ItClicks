package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo

data class ConditionInfo(
    val originalImage: java.awt.image.BufferedImage,
    var rect: java.awt.Rectangle,
    val similarity: Float,
    val isCore: Boolean,
    val globalSearch: Boolean,
    val passingResult: Boolean,
    override val executor: Condition,
    override var result: Result?
) : TaskInfo<ConditionInfo> {

    override fun getSelf(): ConditionInfo = this
}
