package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.condition.Condition
import dev.chinh.itcanclick.task.condition.ConditionInfo
import dev.chinh.itcanclick.task.type.ConditionType
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

data class ConditionData(
    var originalImagePath: String,
    var rect: java.awt.Rectangle,
    var similarity: Float = 0.85f,
    var isCore: Boolean = false,
    var globalSearch: Boolean = false,
    var passingResult: Boolean = false,
    override var taskType: ConditionType
) : TaskData<ConditionInfo> {

    override fun getTaskInfo(): TaskInfo<ConditionInfo> {
        return ConditionInfo(
            loadImage(originalImagePath),
            rect, similarity, isCore, globalSearch, passingResult,
            TaskRegistry.getTask(taskType) as Condition,
            null
        )
    }

    fun loadImage(pathname: String) : BufferedImage {
        try {
            val filePath = File(pathname)
            ImageIO.read(filePath)
        } catch (e: IOException) {
            System.err.println("Error loading image: $e")
        }
        throw NotImplementedError()
    }

}
