package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot

class KeyRelease : KeyAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: ActionInfo) {
        if (actionInfo !is KeyInfo) {
            return
        }
        release(actionInfo)
    }

    private fun release(keyInfo: KeyInfo) {
        robot.delay(keyInfo.delay)
        robot.keyRelease(keyInfo.keyCode)
    }
}