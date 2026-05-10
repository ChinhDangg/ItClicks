package dev.chinh.itcanclick.task

import dev.chinh.itcanclick.task.action.key.KeyClick
import dev.chinh.itcanclick.task.action.key.KeyPress
import dev.chinh.itcanclick.task.action.key.KeyRelease
import dev.chinh.itcanclick.task.action.mouse.MouseClick
import dev.chinh.itcanclick.task.action.mouse.MousePress
import dev.chinh.itcanclick.task.action.mouse.MouseRelease
import dev.chinh.itcanclick.task.condition.PixelExactCondition
import dev.chinh.itcanclick.task.condition.PixelSimilarCondition
import dev.chinh.itcanclick.task.condition.TextCondition
import dev.chinh.itcanclick.task.delay.Delay
import dev.chinh.itcanclick.task.wrapper.LoopTask
import dev.chinh.itcanclick.task.wrapper.NormalWrapper
import dev.chinh.itcanclick.task.wrapper.ScheduledTask
import dev.chinh.itcanclick.task.type.*

object TaskRegistry {

    private val robot = java.awt.Robot()

    private val mouseClick = MouseClick(robot)
    private val mousePress = MousePress(robot)
    private val mouseRelease = MouseRelease(robot)
    private val keyClick = KeyClick(robot)
    private val keyPress = KeyPress(robot)
    private val keyRelease = KeyRelease(robot)
    private val delayAction = Delay()
    private val pixelExactCondition = PixelExactCondition(robot)
    private val pixelSimilarCondition = PixelSimilarCondition(robot)
    private val textCondition = TextCondition(robot)
    private val loopWrapper = LoopTask()
    private val scheduledWrapper = ScheduledTask()
    private val wrapper = NormalWrapper()


    fun getTask(taskType: TaskType) : Task<*> {
        return when (taskType) {
            MouseType.MOUSE_CLICK -> mouseClick
            MouseType.MOUSE_PRESS -> mousePress
            MouseType.MOUSE_RELEASE -> mouseRelease
            KeyType.KEY_CLICK -> keyClick
            KeyType.KEY_PRESS -> keyPress
            KeyType.KEY_RELEASE -> keyRelease
            ConditionType.PIXEL_EXACT_MATCH -> pixelExactCondition
            ConditionType.PIXEL_SIMILAR_MATCH -> pixelSimilarCondition
            ConditionType.TEXT_MATCH -> textCondition
            OtherType.DELAY -> delayAction
            WrapperType.LOOPED_TASK -> loopWrapper
            WrapperType.SCHEDULED_TASK -> scheduledWrapper
            WrapperType.WRAPPER -> wrapper
        }
    }
}