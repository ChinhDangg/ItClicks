package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo

data class NormalWrapperInfo(
    override val tasksToRun: List<TaskInfo<*>>,
    override val executor: NormalWrapper,
    override var result: Result?
): TaskWrapperInfo<NormalWrapperInfo> {

    override fun getSelf(): NormalWrapperInfo = this
}