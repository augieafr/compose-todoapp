package com.augieafr.todo_app.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.augieafr.todo_app.utils.toDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDatePicker(
    initialSelectedDateMillis: Long? = null,
    onDismissRequest: () -> Unit,
    onSaveButtonClick: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        initialDisplayedMonthMillis = initialSelectedDateMillis ?: System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker
    )
    DatePickerDialog(onDismissRequest = { onDismissRequest() }, confirmButton = {
        Button(onClick = {
            datePickerState.selectedDateMillis?.toDateFormat()
                ?.let { onSaveButtonClick(it) }
        }) {
            Text(text = "Save")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
fun TodoDatePickerPreview() {
    TodoDatePicker(onDismissRequest = { }, onSaveButtonClick = {})
}