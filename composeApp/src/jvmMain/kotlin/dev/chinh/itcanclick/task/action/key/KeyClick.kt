package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot

@Component
class KeyClick : KeyAction {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: KeyInfo) : Result {
        click(actionInfo)
        return Result(ResultStatus.PASS, "Key Clicked: " + actionInfo.keyCode)
    }

    private fun click(keyInfo: KeyInfo) {
        robot.keyPress(keyInfo.keyCode)
        robot.delay(keyInfo.delay)
        robot.keyRelease(keyInfo.keyCode)
    }
}