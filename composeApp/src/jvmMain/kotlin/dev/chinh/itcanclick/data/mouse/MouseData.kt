package dev.chinh.itcanclick.data.mouse

import dev.chinh.itcanclick.data.ActionData
import dev.chinh.itcanclick.task.action.mouse.MouseInfo

interface MouseData<M : MouseInfo<M>> : ActionData<M>