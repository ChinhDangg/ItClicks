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
import org.springframework.stereotype.Service

@Service
class TaskRegistry {

    private val mouseClick: MouseClick
    private val mousePress: MousePress
    private val mouseRelease: MouseRelease
    private val keyClick: KeyClick
    private val keyPress: KeyPress
    private val keyRelease: KeyRelease
    private val delayAction: Delay
    private val pixelExactCondition: PixelExactCondition
    private val pixelSimilarCondition: PixelSimilarCondition
    private val textCondition: TextCondition
    private val loopWrapper: LoopTask
    private val scheduledWrapper: ScheduledTask
    private val wrapper: NormalWrapper

    constructor(
        mouseClick: MouseClick,
        mousePress: MousePress,
        mouseRelease: MouseRelease,
        keyClick: KeyClick,
        keyPress: KeyPress,
        keyRelease: KeyRelease,
        delayAction: Delay,
        pixelExactCondition: PixelExactCondition,
        pixelSimilarCondition: PixelSimilarCondition,
        textCondition: TextCondition,
        loopWrapper: LoopTask,
        scheduledWrapper: ScheduledTask,
        wrapper: NormalWrapper
    ) {
        this.mouseClick = mouseClick
        this.mousePress = mousePress
        this.mouseRelease = mouseRelease
        this.keyClick = keyClick
        this.keyPress = keyPress
        this.keyRelease = keyRelease
        this.delayAction = delayAction
        this.pixelExactCondition = pixelExactCondition
        this.pixelSimilarCondition = pixelSimilarCondition
        this.textCondition = textCondition
        this.loopWrapper = loopWrapper
        this.scheduledWrapper = scheduledWrapper
        this.wrapper = wrapper
    }

    @Suppress("UNCHECKED_CAST")
    fun <I> getTask(taskType: TaskType) : I {
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
            WrapperType.NORMAL_WRAPPER -> wrapper
        } as I
    }
}