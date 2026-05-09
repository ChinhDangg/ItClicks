package dev.chinh.itcanclick.task.condition

import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

class PixelExactCondition : PixelCondition {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun check(conditionInfo: ConditionInfo): ConditionResult {
        val matchResult = if (conditionInfo.globalSearch)
            checkExactInEntireScreen(conditionInfo.originalImage)
        else
            checkExactInRect(conditionInfo.rect, conditionInfo.originalImage)

        val passed = matchResult.matchScore >= conditionInfo.similarity
        val resultStatus = determineResultStatus(passed, conditionInfo)

        return ConditionResult(resultStatus, "${resultStatus}: ${matchResult.matchScore} / ${conditionInfo.similarity}", matchResult.matchScore, matchResult.rect)
    }

    private fun checkExactInRect(rect : Rectangle, sourceImage : BufferedImage) : PixelCondition.MatchResult {
        val targetImage = captureCurrentScreen(robot, rect)

        var passed = true
        val height = sourceImage.height; val width = sourceImage.width
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (sourceImage.getRGB(j, i) != targetImage.getRGB(j, i)) {
                    passed = false
                    break
                }
            }
            if (!passed) break
        }

        return PixelCondition.MatchResult(if (passed) 1.0 else 0.0, rect)
    }

    private fun checkExactInEntireScreen(sourceImage : BufferedImage) : PixelCondition.MatchResult {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val rect = Rectangle(0, 0, screenSize.width - 1, screenSize.height - 1)
        return checkExactInRect(rect, sourceImage)
    }
}