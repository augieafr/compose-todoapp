package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.ui.component.TodoDatePicker
import com.augieafr.todo_app.utils.toDateMillis

@Composable
fun TodoTextField(
    modifier: Modifier = Modifier,
    state: TodoTextFieldState = rememberTodoTextFieldState(),
    label: String,
    lines: Int = 1,
    isDateTextField: Boolean = false
) {
    @Composable
    fun TodoOutlinedTextField(todoOutlineModifier: Modifier = modifier) {
        OutlinedTextField(
            modifier = todoOutlineModifier,
            value = state.text,
            maxLines = lines,
            minLines = lines,
            onValueChange = {
                state.text = it
                state.isError = it.isEmpty()
            },
            enabled = !isDateTextField,
            isError = state.isError,
            label = {
                Text(text = label)
            }
        )
    }

    if (!isDateTextField) {
        TodoOutlinedTextField()
        return
    }

    var isShowDatePickerDialog by remember { mutableStateOf(false) }
    if (isShowDatePickerDialog) {
        TodoDatePicker(
            initialSelectedDateMillis = state.text.toDateMillis(),
            onDismissRequest = { isShowDatePickerDialog = false },
            onSaveButtonClick = {
                state.text = it
                state.isError = it.isEmpty()
                isShowDatePickerDialog = false
            })
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TodoOutlinedTextField(
            Modifier
                .clickable(true) {
                    isShowDatePickerDialog = true
                }
                .weight(0.75f))
        IconButton(
            onClick = {
                isShowDatePickerDialog = true
            },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.25F)
                    .size(28.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = "date dialog icon"
            )
        }
    }
}