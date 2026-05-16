package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.data.wrapper.NormalWrapperData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.TaskType
import dev.chinh.itcanclick.task.type.WrapperType

data class NormalWrapperInfo(
    override val tasksToRun: List<TaskInfo<*>>,
    override val id: String,
    override val name: String,
    override var result: Result?
): TaskWrapperInfo<NormalWrapperInfo> {

    override val taskType: TaskType = WrapperType.NORMAL_WRAPPER

    override fun getSelf(): NormalWrapperInfo = this

    override fun getTaskData(): NormalWrapperData {
        return NormalWrapperData(
            tasksToRun.map { it.getTaskData() },
            id, name
        )
    }
}