package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import java.awt.Robot
import java.awt.event.InputEvent

class MouseRelease : MouseAction<MouseBaseInfo> {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseBaseInfo): Result {
        release(actionInfo)
        return Result(ResultStatus.PASS, "Mouse Released")
    }

    private fun release(mouseInfo: MouseBaseInfo) {
        robot.delay(mouseInfo.delay)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Released")
    }
}