package com.augieafr.todo_app.ui.component.text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTextField(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onCancelSearch: () -> Unit
) {
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    TextField(
        modifier = modifier.focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = query,
        placeholder = {
            Text(
                text = "Search with title",
            )
        },
        onValueChange = { onQueryChange(it) },
        shape = SearchBarDefaults.inputFieldShape,
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { onCancelSearch() },
                imageVector = Icons.Filled.Done,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onCancelSearch() })
    )
}

@Preview
@Composable
fun SearchBarTextFieldPreview() {
    SearchBarTextField(Modifier.fillMaxWidth(), "", {}, {})
}