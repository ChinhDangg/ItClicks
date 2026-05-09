package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.action.Action
import java.awt.Robot

interface MouseAction<M : MouseInfo<M>> : Action<M> {

    data class Coord(val x: Int, val y: Int)

    fun moveMouse(robot: Robot, actionInfo: MouseInfo<M>) : Coord {
        var x : Int; var y : Int
        if (actionInfo.isExact) {
            x = actionInfo.rect.x + actionInfo.rect.width / 2
            y = actionInfo.rect.y + actionInfo.rect.height / 2
        } else {
            x = actionInfo.rect.x + (Math.random() * (actionInfo.rect.width + 1)).toInt()
            y = actionInfo.rect.y + (Math.random() * (actionInfo.rect.height + 1)).toInt()
        }
        robot.mouseMove(x, y)
        return Coord(x, y)
    }

}