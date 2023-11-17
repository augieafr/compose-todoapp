package com.augieafr.todo_app.ui.model

import com.augieafr.todo_app.utils.dueDateToDeadline

data class TodoUiModel(
    val id: Int,
    val title: String,
    val dueDate: String,
    val deadLine: ToDoDeadline,
    val description: String,
    val isDone: Boolean
) {
    companion object {
        fun generateDummyTodoList(): ArrayList<TodoUiModel> {
            return arrayListOf(
                TodoUiModel(
                    0,
                    "Title 1",
                    "20231115",
                    dueDateToDeadline("20231115"),
                    "Description 1",
                    false
                ),
                TodoUiModel(
                    2,
                    "Title 2",
                    "20231116",
                    dueDateToDeadline("20231116"),
                    "Description 2",
                    false
                ),
                TodoUiModel(
                    3,
                    "Title 3",
                    "20231117",
                    dueDateToDeadline("20231117"),
                    "Description 3",
                    true
                ),
                TodoUiModel(
                    4,
                    "Title 4",
                    "20231118",
                    dueDateToDeadline("20231118"),
                    "Description 4",
                    true
                ),
            )
        }
    }
}
