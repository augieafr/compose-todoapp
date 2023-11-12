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
                    1,
                    "Title 1",
                    "20230704",
                    dueDateToDeadline("20230729"),
                    "Description 1",
                    false
                ),
                TodoUiModel(
                    2,
                    "Title 2",
                    "20230630",
                    dueDateToDeadline("20230731"),
                    "Description 2",
                    false
                ),
                TodoUiModel(
                    3,
                    "Title 3",
                    "20230705",
                    dueDateToDeadline("20230905"),
                    "Description 3",
                    true
                ),
                TodoUiModel(
                    4,
                    "Title 4",
                    "20230505",
                    dueDateToDeadline("20230505"),
                    "Description 4",
                    true
                ),
            )
        }
    }
}
