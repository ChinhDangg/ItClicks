package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import java.awt.Robot
import java.awt.event.InputEvent

class MousePress : MouseAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseInfo): Result {
        if (actionInfo !is MouseInfo) {
            return Result(ResultStatus.FAIL, "Not MouseInfo received")
        }
        val coord = press(actionInfo)
        return Result(ResultStatus.PASS, "Mouse Pressed: $coord")
    }

    private fun press(mouseInfo: MouseInfo): Pair<Int, Int> {
        val (x, y) = moveMouse(robot, mouseInfo)
        robot.delay(mouseInfo.delay)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Pressed at ($x, $y)")
        return Pair(x, y)
    }
}