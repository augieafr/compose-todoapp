package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class TodoTextFieldState(
    initialValue: String,
) {
    var text by mutableStateOf(initialValue)
    val isError by derivedStateOf { text.isEmpty() }

    companion object {
        val Saver: Saver<TodoTextFieldState, *> = listSaver(
            save = { listOf(it.text) },
            restore = {
                TodoTextFieldState(
                    initialValue = it[0],
                )
            }
        )
    }
}

@Composable
fun rememberTodoTextFieldState(
    value: String = "",
) = rememberSaveable(value, saver = TodoTextFieldState.Saver) {
    TodoTextFieldState(value)
}