package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TodoTransparentTextField(
    modifier: Modifier = Modifier,
    state: TodoTextFieldState = rememberTodoTextFieldState(),
    isEdit: Boolean,
    placeHolder: String,
    textFieldTextStyle: TextStyle,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    val textStyle = textFieldTextStyle.copy(
        color = if (state.isError) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )

    TextField(
        value = state.text,
        onValueChange = { state.text = it },
        modifier = modifier,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        enabled = isEdit,
        textStyle = textStyle,
        placeholder = {
            Text(text = placeHolder, style = textStyle)
        },
        colors = TextFieldDefaults.colors(
            errorContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )

}

@Preview
@Composable
fun TodoTransparentTextFieldPreview() {
    TodoTransparentTextField(
        state = rememberTodoTextFieldState(),
        modifier = Modifier,
        isEdit = true,
        textFieldTextStyle = MaterialTheme.typography.titleLarge,
        placeHolder = "Placeholder"
    )
}