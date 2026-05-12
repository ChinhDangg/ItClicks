package dev.chinh.itcanclick.data.wrapper

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.wrapper.TaskWrapperInfo

interface WrapperData<W : TaskWrapperInfo<W>> : TaskData<W> {

    val tasksToRun: List<TaskData<*>>
}