package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.utils.noRippleClickable

@Composable
fun TodoOutlineTextField(
    modifier: Modifier = Modifier,
    state: TodoTextFieldState,
    label: String,
    minLines: Int = 1,
    readOnly: Boolean = false,
    trailingIcon: @Composable (RowScope.() -> Unit)? = null
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = modifier,
                value = state.text,
                minLines = minLines,
                textStyle = MaterialTheme.typography.bodyMedium,
                readOnly = readOnly,
                onValueChange = {
                    state.text = it
                },
                isError = state.isError,
            )
            trailingIcon?.invoke(this)
        }
    }
}

@Preview
@Composable
fun TodoOutlineTextFieldPreview() {
    TodoOutlineTextField(
        state = rememberTodoTextFieldState("test"),
        label = "Preview",
        readOnly = true
    ) {
        Icon(
            modifier = Modifier
                .noRippleClickable { }
                .weight(0.25f),
            imageVector = Icons.Filled.DateRange,
            contentDescription = "open date picker"
        )
    }
}