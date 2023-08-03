package com.augieafr.todo_app.ui.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
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

sealed class ToDoDeadline(val timeLeft: String) {
    @Composable
    @ReadOnlyComposable
    fun getBackgroundColor() =
        when (this) {
            is FAR -> MaterialTheme.colorScheme.secondaryContainer
            is MID -> MaterialTheme.colorScheme.tertiaryContainer
            is NEAR -> MaterialTheme.colorScheme.errorContainer
        }

    @Composable
    fun getOnBackgroundColor() =
        when (this) {
            is FAR -> MaterialTheme.colorScheme.onSecondaryContainer
            is MID -> MaterialTheme.colorScheme.onTertiaryContainer
            is NEAR -> MaterialTheme.colorScheme.onErrorContainer
        }

    // due date still one week or more
    class FAR(timeLeft: String) : ToDoDeadline(timeLeft)

    // due date less than one week
    class MID(timeLeft: String) : ToDoDeadline(timeLeft)

    // due date less than three days
    class NEAR(timeLeft: String) : ToDoDeadline(timeLeft)
}
