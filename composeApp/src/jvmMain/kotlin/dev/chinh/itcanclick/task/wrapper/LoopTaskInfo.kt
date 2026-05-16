package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.data.wrapper.LoopTaskData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.WrapperType

data class LoopTaskInfo(
    val numLoops: Int,
    override val tasksToRun: List<TaskInfo<*>>,
    override val id: String,
    override val name: String,
    override var result: Result?
) : TaskWrapperInfo<LoopTaskInfo> {

    override val taskType: WrapperType = WrapperType.LOOPED_TASK

    override fun getSelf(): LoopTaskInfo = this

    override fun getTaskData(): LoopTaskData {
        return LoopTaskData(
            numLoops,
            tasksToRun.map { it.getTaskData() },
            id, name
        )
    }
}
