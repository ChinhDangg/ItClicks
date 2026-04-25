package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot

class KeyPress : KeyAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is KeyInfo) {
            return
        }
        press(actionInfo)
    }

    private fun press(keyInfo: KeyInfo) {
        robot.delay(keyInfo.delay)
        robot.keyPress(keyInfo.keyCode)
    }


}