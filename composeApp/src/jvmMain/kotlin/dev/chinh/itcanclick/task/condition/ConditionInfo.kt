package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.data.condition.ConditionData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.saveImage
import dev.chinh.itcanclick.task.type.ConditionType

data class ConditionInfo(
    var originalImage: java.awt.image.BufferedImage?,
    var rect: java.awt.Rectangle,
    val similarity: Float,
    val isCore: Boolean,
    val globalSearch: Boolean,
    val passingResult: Boolean,
    override val id: String,
    override val name: String,
    override val taskType: ConditionType,
    override var result: Result?
) : TaskInfo<ConditionInfo> {

    override fun getSelf(): ConditionInfo = this

    override fun getTaskData(): ConditionData {
        val savedImage = originalImage?.let { saveImage(it, getImageName()) }
        return ConditionData(
            savedImage,
            rect, similarity, isCore, globalSearch, passingResult,
            id, name, taskType,
        )
    }

    fun getImageName(): String {
        return "condition_${id}_$name.jpg"
    }
}
