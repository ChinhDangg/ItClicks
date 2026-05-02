package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage

interface Condition : Task<ConditionInfo> {

    override suspend fun execute(taskInfo: ConditionInfo): Result {
        return check(taskInfo)
    }

    fun check(conditionInfo : ConditionInfo): ConditionResult

    fun captureCurrentScreen(robot : Robot, rect : Rectangle) : BufferedImage {
        return robot.createScreenCapture(rect)
    }

    fun determineResultStatus(passed : Boolean, conditionInfo : ConditionInfo): ResultStatus {
        val resultStatus = if (passed) {
            if (conditionInfo.isCore)
                ResultStatus.SKIPPABLE
            if (conditionInfo.passingResult)
                ResultStatus.PASS_RESULT
            ResultStatus.PASS
        }
        else {
            ResultStatus.FAIL
        }
        return resultStatus
    }
}