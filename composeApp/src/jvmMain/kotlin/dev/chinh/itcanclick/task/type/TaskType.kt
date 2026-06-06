package dev.chinh.itcanclick.task.type

sealed interface TaskType {
    val typeName: String
    val displayName: String
}