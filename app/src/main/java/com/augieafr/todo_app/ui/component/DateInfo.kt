package com.augieafr.todo_app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.utils.noRippleClickable

@Composable
fun DateInfo(
    modifier: Modifier = Modifier,
    date: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "date picker")
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = date, style = style)
    }
}