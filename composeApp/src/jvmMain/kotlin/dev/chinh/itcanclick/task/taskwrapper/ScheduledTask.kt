package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo

class ScheduledTask(
    override var tasksToRun: List<TaskInfo<*>> = emptyList(),
) : TaskWrapper<ScheduledTaskInfo>() {

    override fun perform(taskInfo: ScheduledTaskInfo): Result {
        TODO("Not yet implemented")
    }
}