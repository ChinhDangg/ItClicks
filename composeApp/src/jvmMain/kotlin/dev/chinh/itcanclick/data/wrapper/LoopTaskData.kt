package dev.chinh.itcanclick.data.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.type.TaskType
import dev.chinh.itcanclick.task.type.WrapperType
import dev.chinh.itcanclick.task.wrapper.LoopTaskInfo
import kotlinx.serialization.Serializable

@Serializable
data class LoopTaskData(
    val numLoops: Int,
    override val taskDataList: List<TaskData<*>>,
    override val id: String,
    override val name: String
) : WrapperData<LoopTaskInfo> {

    override val taskType: TaskType = WrapperType.LOOPED_TASK

    override fun getMinimalTaskInfo(): LoopTaskInfo {
        val taskInfoList = taskDataList.map { it.getTaskInfo() }
        return LoopTaskInfo(
            numLoops,
            taskInfoList, name, id
        )
    }
}
