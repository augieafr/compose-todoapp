package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class TodoTextFieldState(
    initialValue: String,
    isError: Boolean,
) {
    var text by mutableStateOf(initialValue)
    var isError by mutableStateOf(isError)

    companion object {
        val Saver: Saver<TodoTextFieldState, *> = listSaver(
            save = { listOf(it.text, it.isError) },
            restore = {
                TodoTextFieldState(
                    initialValue = it[0] as String,
                    isError = it[1] as Boolean,
                )
            }
        )
    }
}

@Composable
fun rememberTodoTextFieldState(
    value: String = "",
    isError: Boolean = false,
) = rememberSaveable(value, isError, saver = TodoTextFieldState.Saver) {
    TodoTextFieldState(value, isError)
}