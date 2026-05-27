package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import org.springframework.stereotype.Component
import java.awt.Robot

@Component(MouseMove.BEAN_NAME)
class MouseMove : MouseAction<MouseMoveInfo>{

    companion object { const val BEAN_NAME = "MOUSE_MOVE" }

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun perform(actionInfo: MouseMoveInfo): Result {
        val (x, y) = moveMouse(robot, actionInfo)
        return Result(ResultStatus.PASS, "Mouse Moved to ($x, $y)")
    }

    fun moveMouse(robot: Robot, actionInfo: MouseMoveInfo) : Pair<Int, Int> {
        var x : Int; var y : Int
        if (actionInfo.isExact) {
            x = actionInfo.rect.x + actionInfo.rect.width / 2
            y = actionInfo.rect.y + actionInfo.rect.height / 2
        } else {
            x = actionInfo.rect.x + (Math.random() * (actionInfo.rect.width + 1)).toInt()
            y = actionInfo.rect.y + (Math.random() * (actionInfo.rect.height + 1)).toInt()
        }
        robot.mouseMove(x, y)
        return Pair(x, y)
    }
}