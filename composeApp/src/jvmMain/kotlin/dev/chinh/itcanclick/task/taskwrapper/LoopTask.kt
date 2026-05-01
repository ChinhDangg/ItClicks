package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskInfo

class LoopTask(
    override var tasksToRun: List<TaskInfo<*>> = emptyList(),
) : TaskWrapper<LoopTaskInfo>() {

    override fun perform(taskInfo: LoopTaskInfo): Result {
        TODO("Not yet implemented")
    }

    fun runLoop(taskInfo: LoopTaskInfo) : Result {
        for (i in 0 until taskInfo.numLoops) {

        }

        TODO("Not yet implemented")
    }
}