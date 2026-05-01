package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.TaskType

data class LoopTaskInfo(
    val numLoops: Int,
    val taskExecutor: TaskWrapper<LoopTaskInfo>
) : TaskWrapperInfo<LoopTaskInfo>(TaskType.LOOPED_TASK, taskExecutor) {

    override fun getSelf(): LoopTaskInfo = this
}
