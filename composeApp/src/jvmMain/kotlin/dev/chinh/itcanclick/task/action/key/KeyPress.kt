package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot

@Component(KeyPress.BEAN_NAME)
class KeyPress : KeyAction {

    companion object { const val BEAN_NAME = "KEY_PRESS" }

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: KeyInfo): Result {
        press(actionInfo)
        return Result(ResultStatus.PASS, "Key Pressed: " + actionInfo.keyCode)
    }

    private fun press(keyInfo: KeyInfo) {
        robot.delay(keyInfo.delay)
        robot.keyPress(keyInfo.keyCode)
    }


}