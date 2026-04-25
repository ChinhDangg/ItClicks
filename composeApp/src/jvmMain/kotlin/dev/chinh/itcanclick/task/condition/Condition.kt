package dev.chinh.itcanclick.task.condition

import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage

interface Condition {

    data class Result(val result: ConditionResult,
                      val percent: Double,
                      val rect: Rectangle)

    fun check(conditionInfo : ConditionInfo): Result

    fun captureCurrentScreen(robot : Robot, rect : Rectangle) : BufferedImage {
        return robot.createScreenCapture(rect)
    }
}