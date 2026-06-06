package dev.chinh.itcanclick.perform

import dev.chinh.itcanclick.task.TaskInfo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskRegisterService {

    val tasks = mutableMapOf<String, TaskInfo<*>>()

    fun register(taskInfo: TaskInfo<*>) {
        tasks[taskInfo.id] = taskInfo
    }

    companion object {
        fun generateId(provided: String? = null) : String {
            return provided ?: UUID.randomUUID().toString()
        }
    }
}