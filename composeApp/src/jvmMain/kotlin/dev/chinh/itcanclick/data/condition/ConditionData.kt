package dev.chinh.itcanclick.data.condition

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.condition.ConditionInfo
import dev.chinh.itcanclick.task.type.ConditionType
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

data class ConditionData(
    var originalImagePath: String?,
    var rect: Rectangle,
    var similarity: Float = 0.85f,
    var isCore: Boolean = false,
    var globalSearch: Boolean = false,
    var passingResult: Boolean = false,
    override val id: String,
    override val name: String,
    override var taskType: ConditionType
) : TaskData<ConditionInfo> {

    override fun getTaskInfo(): ConditionInfo {
        val info = getMinimalTaskInfo()
        if (originalImagePath == null)
            return info
        info.originalImage = loadImage(originalImagePath!!)
        return info
    }

    override fun getMinimalTaskInfo(): ConditionInfo {
        return ConditionInfo(
            null,
            rect, similarity, isCore, globalSearch, passingResult,
            id, name, taskType,
            null
        )
    }

    private fun loadImage(pathname: String) : BufferedImage {
        try {
            val filePath = File(pathname)
            ImageIO.read(filePath)
        } catch (e: IOException) {
            System.err.println("Error loading image: $e")
        }
        throw NotImplementedError()
    }

}