package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Robot

class KeyRelease : KeyAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: KeyInfo): Result {
        release(actionInfo)
        return Result(ResultStatus.PASS, "Key Released: " + actionInfo.keyCode)
    }

    private fun release(keyInfo: KeyInfo) {
        robot.delay(keyInfo.delay)
        robot.keyRelease(keyInfo.keyCode)
    }
}