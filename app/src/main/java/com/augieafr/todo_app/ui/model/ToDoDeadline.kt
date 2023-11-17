package com.augieafr.todo_app.ui.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

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
    @ReadOnlyComposable
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