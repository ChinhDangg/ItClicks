package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot
import java.awt.event.InputEvent

@Component(MousePress.BEAN_NAME)
class MousePress : MouseAction<MouseBaseInfo> {

    companion object { const val BEAN_NAME = "MOUSE_PRESS" }

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseBaseInfo): Result {
        press(actionInfo)
        return Result(ResultStatus.PASS, "Mouse Pressed")
    }

    private fun press(mouseInfo: MouseBaseInfo) {
        robot.delay(mouseInfo.delay)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Pressed")
    }
}