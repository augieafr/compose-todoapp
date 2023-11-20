package com.augieafr.todo_app.ui.model

data class TodoUiModel(
    val id: Int,
    val title: String,
    val deadLine: ToDoDeadline,
    val description: String,
    val isDone: Boolean
)
