package com.augieafr.todo_app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.augieafr.todo_app.ui.component.text_field.TodoTextField
import com.augieafr.todo_app.ui.component.text_field.rememberTodoTextFieldState
import com.augieafr.todo_app.utils.changeDatePattern

@Composable
fun AddEditTodoDialog(
    modifier: Modifier = Modifier,
    isEdit: Boolean = false,
    title: String? = null,
    description: String? = null,
    dueDate: String? = null,
    onDismissRequest: () -> Unit,
    onSaveClicked: (title: String, description: String, dueDate: String) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {

            val titleInputState = rememberTodoTextFieldState(title.orEmpty())
            val descriptionInputState =
                rememberTodoTextFieldState(description.orEmpty())
            val dueDateInputState = rememberTodoTextFieldState(
                dueDate.changeDatePattern(
                    sourcePattern = "yyyyMMdd",
                    targetPattern = "MM-dd-yyyy"
                )
            )

            val hasError = remember {
                derivedStateOf {
                    titleInputState.isError || descriptionInputState.isError || dueDateInputState.isError
                }
            }

            Column(Modifier.padding(16.dp)) {
                TitleRow(text = if (isEdit) "Edit" else "Add") {
                    onDismissRequest()
                }
                Spacer(modifier = Modifier.size(16.dp))
                TodoTextField(
                    state = titleInputState,
                    label = "Title"
                )
                Spacer(modifier = Modifier.size(8.dp))
                TodoTextField(
                    state = descriptionInputState,
                    label = "Description",
                    lines = 4,
                )
                Spacer(modifier = Modifier.size(8.dp))
                TodoTextField(
                    state = dueDateInputState,
                    label = "Due date",
                    isDateTextField = true,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(enabled = !hasError.value, onClick = {
                        val newTitle = titleInputState.text
                        val newDescription = descriptionInputState.text
                        val newDueDate =
                            dueDateInputState.text.changeDatePattern("MM-dd-yyyy", "yyyyMMdd")
                        onDismissRequest()
                        onSaveClicked(newTitle, newDescription, newDueDate)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun TitleRow(text: String, onCloseClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        IconButton(
            onClick = { onCloseClicked() },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Icon(
                modifier = Modifier
                    .weight(0.25F)
                    .size(28.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "dialog close icon"
            )
        }
    }
}