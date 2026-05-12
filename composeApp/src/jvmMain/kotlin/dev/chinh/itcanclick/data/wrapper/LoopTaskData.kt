package dev.chinh.itcanclick.data.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskRegistry
import dev.chinh.itcanclick.task.type.TaskType
import dev.chinh.itcanclick.task.wrapper.LoopTask
import dev.chinh.itcanclick.task.wrapper.LoopTaskInfo

data class LoopTaskData(
    val numLoops: Int,
    override val tasksToRun: List<TaskData<*>>,
    override val taskType: TaskType,
) : WrapperData<LoopTaskInfo> {

    override fun getTaskInfo(): TaskInfo<LoopTaskInfo> {
        return LoopTaskInfo(numLoops, listOf(), TaskRegistry.getTask(taskType) as LoopTask, null)
    }
}
