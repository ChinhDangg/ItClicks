package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo

data class LoopTaskInfo(
    val numLoops: Int,
    override val tasksToRun: List<TaskInfo<*>>,
    override val executor: LoopTask,
    override var result: Result?
) : TaskWrapperInfo<LoopTaskInfo> {

    override fun getSelf(): LoopTaskInfo = this
}
