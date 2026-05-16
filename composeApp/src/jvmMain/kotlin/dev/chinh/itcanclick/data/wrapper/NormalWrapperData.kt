package dev.chinh.itcanclick.data.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.type.TaskType
import dev.chinh.itcanclick.task.type.WrapperType
import dev.chinh.itcanclick.task.wrapper.NormalWrapperInfo

data class NormalWrapperData(
    override val taskDataList: List<TaskData<*>>,
    override val id: String,
    override val name: String
) : WrapperData<NormalWrapperInfo> {

    override val taskType: TaskType = WrapperType.NORMAL_WRAPPER

    override fun getMinimalTaskInfo(): NormalWrapperInfo {
        return NormalWrapperInfo(
            taskDataList.map { it.getTaskInfo() },
            id, name,
            null
        )
    }

}