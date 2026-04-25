package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.log.log
import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.action.mouse.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent

class MouseClick : MouseAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is MouseInfo) {
            return
        }
        click(actionInfo)
    }

    private fun click(mouseInfo: MouseInfo) {
        val (x, y) = moveMouse(robot, mouseInfo)
        repeat(mouseInfo.numClicks) {
            robot.delay(mouseInfo.delay)
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            robot.delay(mouseInfo.delay)
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            log("Mouse Clicked at ($x, $y)")
        }
    }
}