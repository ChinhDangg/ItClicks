package dev.chinh.itcanclick.data.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.wrapper.TaskWrapperInfo

interface WrapperData<W : TaskWrapperInfo<W>> : TaskData<W> {

    val taskDataList: List<TaskData<*>>

    override fun getTaskInfo(): TaskInfo<W> {
        return getMinimalTaskInfo()
    }
}