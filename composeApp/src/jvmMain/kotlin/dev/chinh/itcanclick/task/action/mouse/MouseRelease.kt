package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot
import java.awt.event.InputEvent

class MouseRelease : MouseAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is MouseInfo) {
            return
        }
        release(actionInfo)
    }

    private fun release(mouseInfo: MouseInfo) {
        robot.delay(mouseInfo.delay)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Released")
    }
}