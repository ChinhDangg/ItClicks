package dev.chinh.itcanclick.data.action.mouse

import dev.chinh.itcanclick.data.action.ActionData
import dev.chinh.itcanclick.task.action.mouse.MouseInfo

interface MouseData<M : MouseInfo<M>> : ActionData<M>