package com.augieafr.todo_app.data.mapper

import com.augieafr.todo_app.data.entity.TodoEntity
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.dueDateToDeadline

fun TodoUiModel.toTodoEntity() = TodoEntity(
    id, title, description, dueDate, isDone
)

fun TodoEntity.toTodoUiModel() = TodoUiModel(
    id = id,
    title = title,
    description = description,
    dueDate = dueDate,
    deadLine = dueDateToDeadline(dueDate),
    isDone = isDone
)