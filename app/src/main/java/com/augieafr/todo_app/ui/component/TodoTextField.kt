package com.augieafr.todo_app.ui.component

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodoTextField(
    value: String,
    label: String,
    isDateTextField: Boolean = false,
    onValueChange: (String) -> Unit
) {
    val isError = remember {
        mutableStateOf(false)
    }

    val isErrorDerivedState = remember {
        derivedStateOf {
            isError.value
        }
    }

    if (isDateTextField) {
        var isShowDatePickerDialog by remember { mutableStateOf(false) }
        if (isShowDatePickerDialog) {
            TodoDatePicker(
                onDismissRequest = { isShowDatePickerDialog = false },
                onSaveButtonClick = {
                    onValueChange(it)
                    isError.value = it.isEmpty()
                    isShowDatePickerDialog = false
                })
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(0.75F),
                value = value,
                readOnly = true,
                onValueChange = {
                    onValueChange(it)
                },
                isError = isErrorDerivedState.value,
                label = {
                    Text(
                        text = label
                    )
                }
            )

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
    } else {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                isError.value = it.isEmpty()
            },
            isError = isErrorDerivedState.value,
            label = {
                Text(text = label)
            }
        )
    }
}