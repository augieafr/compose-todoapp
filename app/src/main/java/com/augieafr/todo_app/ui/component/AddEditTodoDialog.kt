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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.changeDatePattern

@Composable
fun AddEditTodoDialog(
    modifier: Modifier = Modifier,
    event: ToDoEvent,
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

            val todoUiModel: TodoUiModel? = when (event) {
                is ToDoEvent.Edit -> {
                    event.todoUiModel
                }

                else -> null
            }

            val textFieldValues = remember {
                mutableStateListOf(
                    todoUiModel?.title.orEmpty(),
                    todoUiModel?.description.orEmpty(),
                    todoUiModel?.dueDate.changeDatePattern(
                        sourcePattern = "yyyyMMdd",
                        targetPattern = "MM-dd-yyyy"
                    )
                )
            }

            val hasError = remember {
                derivedStateOf {
                    textFieldValues.hasError()
                }
            }

            Column(Modifier.padding(16.dp)) {
                TitleRow(text = if (todoUiModel == null) "Add" else "Edit") {
                    onDismissRequest()
                }
                Spacer(modifier = Modifier.size(16.dp))
                TodoTextField(value = textFieldValues[0], label = "Title") {
                    textFieldValues[0] = it
                }
                Spacer(modifier = Modifier.size(8.dp))
                TodoTextField(
                    value = textFieldValues[1],
                    label = "Description"
                ) {
                    textFieldValues[1] = it
                }
                Spacer(modifier = Modifier.size(8.dp))
                TodoTextField(
                    value = textFieldValues[2],
                    label = "Due date",
                    isDateTextField = true,
                ) {
                    textFieldValues[2] = it
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(enabled = !hasError.value, onClick = {
                        val title = textFieldValues[0]
                        val description = textFieldValues[1]
                        val dueDate = textFieldValues[2].changeDatePattern("MM-dd-yyyy", "yyyyMMdd")
                        onSaveClicked(title, description, dueDate)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

private fun SnapshotStateList<String>.hasError(): Boolean {
    for (text in this) {
        if (text.isEmpty()) return true
    }
    return false
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