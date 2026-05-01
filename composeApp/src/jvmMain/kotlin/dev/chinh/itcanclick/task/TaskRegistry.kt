package dev.chinh.itcanclick.task

import dev.chinh.itcanclick.task.action.key.KeyClick
import dev.chinh.itcanclick.task.action.key.KeyPress
import dev.chinh.itcanclick.task.action.mouse.MouseClick
import dev.chinh.itcanclick.task.action.mouse.MousePress
import dev.chinh.itcanclick.task.action.mouse.MouseRelease
import dev.chinh.itcanclick.task.condition.PixelCondition
import dev.chinh.itcanclick.task.condition.TextCondition
import dev.chinh.itcanclick.task.taskwrapper.LoopTask
import dev.chinh.itcanclick.task.taskwrapper.ScheduledTask

object TaskRegistry {

    private val robot = java.awt.Robot()

    private val registry = mapOf(
        TaskType.MOUSE_CLICK to MouseClick(robot),
        TaskType.MOUSE_PRESS to MousePress(robot),
        TaskType.MOUSE_RELEASE to MouseRelease(robot),
        TaskType.KEY_CLICK to KeyClick(robot),
        TaskType.KEY_PRESS to KeyPress(robot),
        TaskType.KEY_RELEASE to KeyPress(robot),
        TaskType.PIXEL_MATCH to PixelCondition(robot),
        TaskType.TEXT_MATCH to TextCondition(robot),
        TaskType.LOOPED_TASK to LoopTask(),
        TaskType.SCHEDULED_TASK to ScheduledTask()
    )

//    fun getTask(taskType: TaskType) : Task<TaskInfo>? {
//        return registry[taskType]
//    }
}