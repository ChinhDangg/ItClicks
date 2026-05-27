package dev.chinh.itcanclick.task

import dev.chinh.itcanclick.task.type.TaskType
import org.springframework.stereotype.Service

@Service
class TaskRegistry(
    private val tasks: Map<String, Task<*>>
) {

    @Suppress("UNCHECKED_CAST")
    fun <I : TaskInfo<I>> getTask(taskType: TaskType) : Task<I> {
        return (tasks[taskType.typeName] ?: error("No task for $taskType")) as Task<I>
    }
}