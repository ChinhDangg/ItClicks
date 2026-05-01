package dev.chinh.itcanclick.task.condition

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import java.awt.Rectangle

data class ConditionResult(
    val status: ResultStatus,
    val mess: String,
    val percent: Double,
    val rect: Rectangle) : Result(result = status, message = mess)
