package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot

class KeyClick : KeyAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is KeyInfo) {
            return
        }
        click(actionInfo)
    }

    private fun click(keyInfo: KeyInfo) {
        robot.keyPress(keyInfo.keyCode)
        robot.delay(keyInfo.delay)
        robot.keyRelease(keyInfo.keyCode)
    }
}