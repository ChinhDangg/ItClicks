package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

@Component(PixelSimilarCondition.BEAN_NAME)
class PixelSimilarCondition : PixelCondition{

    companion object { const val BEAN_NAME = "PIXEL_SIMILAR" }

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun check(conditionInfo: ConditionInfo): ConditionResult {
        if (conditionInfo.originalImage == null)
            return ConditionResult(ResultStatus.PASS, "No image to compare", 1.0, conditionInfo.rect)

        val matchResult = if (conditionInfo.globalSearch)
            checkMatchInEntireScreen(conditionInfo.originalImage!!)
        else
            checkMatchInRect(conditionInfo.rect, conditionInfo.originalImage!!)

        val passed = matchResult.matchScore >= conditionInfo.similarity
        val resultStatus = determineResultStatus(passed, conditionInfo)

        return ConditionResult(resultStatus, "${resultStatus}: ${matchResult.matchScore} / ${conditionInfo.similarity}", matchResult.matchScore, matchResult.rect)
    }

    private fun checkMatchInEntireScreen(sourceImage : BufferedImage) : PixelCondition.MatchResult {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val rect = Rectangle(0, 0, screenSize.width - 1, screenSize.height - 1)
        val targetImage = captureCurrentScreen(robot, rect)
        val result = templateMatching(sourceImage, targetImage, rect,false)
        return result
    }

    private fun checkMatchInRect(rect : Rectangle, sourceImage : BufferedImage) : PixelCondition.MatchResult {
        val targetImage = captureCurrentScreen(robot, rect)
        val result = templateMatching(sourceImage, targetImage, rect,true)
        return result
    }
}