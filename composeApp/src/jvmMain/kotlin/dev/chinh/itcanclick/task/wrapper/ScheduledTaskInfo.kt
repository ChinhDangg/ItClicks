package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.TaskType

data class ScheduledTaskInfo(
    val delay: Long,
    override val tasksToRun: List<TaskInfo<*>>,
    override var result: Result?,
    override val id: String,
    override val name: String,
    override val taskType: TaskType
) : TaskWrapperInfo<ScheduledTaskInfo> {

    override fun getSelf(): ScheduledTaskInfo = this

    override fun getTaskData(): TaskData<ScheduledTaskInfo> {
        TODO("Not yet implemented")
    }
}
