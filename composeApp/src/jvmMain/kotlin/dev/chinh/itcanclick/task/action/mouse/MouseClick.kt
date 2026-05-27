package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot
import java.awt.event.InputEvent

@Component(MouseClick.BEAN_NAME)
class MouseClick : MouseAction<MouseClickInfo> {

    companion object { const val BEAN_NAME = "MOUSE_CLICK" }

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseClickInfo): Result {
        click(actionInfo)
        return Result(ResultStatus.PASS, "Mouse Clicked")
    }

    private fun click(mouseInfo: MouseClickInfo) {
        repeat(mouseInfo.numClicks) {
            robot.delay(mouseInfo.delay)
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            robot.delay(mouseInfo.delay)
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            log("Mouse Clicked")
        }
    }
}