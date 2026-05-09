package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo

data class ScheduledTaskInfo(
    val delay: Long,
    override val tasksToRun: List<TaskInfo<*>>,
    override val executor: ScheduledTask,
    override var result: Result?
) : TaskWrapperInfo<ScheduledTaskInfo> {

    override fun getSelf(): ScheduledTaskInfo = this
}
