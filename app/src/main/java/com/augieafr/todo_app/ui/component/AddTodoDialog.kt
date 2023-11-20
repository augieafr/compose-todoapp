package com.augieafr.todo_app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.augieafr.todo_app.R
import com.augieafr.todo_app.ui.component.text_field.TodoOutlineTextField
import com.augieafr.todo_app.ui.component.text_field.rememberTodoTextFieldState
import com.augieafr.todo_app.utils.noRippleClickable
import com.augieafr.todo_app.utils.toDateMillis

@Composable
fun AddTodoDialog(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit,
    onAddTodo: (title: String, description: String, dueDate: String) -> Unit
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        var isShowDatePickerDialog by remember { mutableStateOf(false) }
        val dueDateState = rememberTodoTextFieldState()
        if (isShowDatePickerDialog) {
            TodoDatePicker(
                initialSelectedDateMillis = if (!dueDateState.isError) dueDateState.text.toDateMillis() else null,
                onDismissRequest = { isShowDatePickerDialog = false },
                onSaveButtonClick = {
                    dueDateState.text = it
                    isShowDatePickerDialog = false
                })
        }

        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val titleState = rememberTodoTextFieldState()
                val descriptionState = rememberTodoTextFieldState()
                TitleRow(onDismissDialog = onDismissDialog)
                Spacer(modifier = Modifier.padding(16.dp))
                TodoOutlineTextField(
                    state = titleState, label = "Title", placeholder = stringResource(
                        R.string.title_text_field_placeholder
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TodoOutlineTextField(
                    state = descriptionState,
                    label = "Description",
                    minLines = 3,
                    placeholder = stringResource(R.string.description_text_field_placeholder)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TodoOutlineTextField(
                    modifier = Modifier.weight(0.75f),
                    dueDateState,
                    label = "Due Date",
                    placeholder = stringResource(R.string.due_date_text_field_placeholder),
                    readOnly = true
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Icon(
                        modifier = Modifier.noRippleClickable { isShowDatePickerDialog = true },
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = stringResource(R.string.open_date_picker_content_desc)
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    val hasError by remember {
                        derivedStateOf {
                            titleState.text.isEmpty() || descriptionState.text.isEmpty() || dueDateState.text.isEmpty()
                        }
                    }
                    Button(
                        onClick = {
                            onAddTodo(
                                titleState.text,
                                descriptionState.text,
                                dueDateState.text
                            )
                            onDismissDialog()
                        },
                        enabled = !hasError,
                        modifier = Modifier.testTag(stringResource(R.string.add_button_test_tag))
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleRow(onDismissDialog: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Add Todo", style = MaterialTheme.typography.titleLarge)
        Icon(
            modifier = Modifier.noRippleClickable { onDismissDialog() },
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_content_desc),
        )
    }
}

@Preview
@Composable
fun AddTodoDialogPreview() {
    AddTodoDialog(
        modifier = Modifier.fillMaxSize(),
        onDismissDialog = { },
        onAddTodo = { _, _, _ ->

        })
}