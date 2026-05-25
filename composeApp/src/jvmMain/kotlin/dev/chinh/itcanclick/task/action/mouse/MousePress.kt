package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot
import java.awt.event.InputEvent

@Component
class MousePress : MouseAction<MouseBaseInfo> {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseBaseInfo): Result {
        val coord = press(actionInfo)
        return Result(ResultStatus.PASS, "Mouse Pressed: $coord")
    }

    private fun press(mouseInfo: MouseBaseInfo): Pair<Int, Int> {
        val (x, y) = moveMouse(robot, mouseInfo)
        robot.delay(mouseInfo.delay)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Pressed at ($x, $y)")
        return Pair(x, y)
    }
}