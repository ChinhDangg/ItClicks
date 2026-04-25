package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot
import java.awt.event.InputEvent

class MousePress : MouseAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is MouseInfo) {
            return
        }
        press(actionInfo)
    }

    private fun press(mouseInfo: MouseInfo) {
        val (x, y) = moveMouse(robot, mouseInfo)
        robot.delay(mouseInfo.delay)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        log("Mouse Pressed at ($x, $y)")
    }
}