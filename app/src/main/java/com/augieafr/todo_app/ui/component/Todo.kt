package com.augieafr.todo_app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.ui.home.TodoEvent
import com.augieafr.todo_app.ui.model.ToDoDeadline


@Composable
fun Todo(
    title: String,
    description: String,
    isDone: Boolean,
    deadline: ToDoDeadline,
    modifier: Modifier = Modifier,
    onTodoEvent: (TodoEvent) -> Unit
) {
    val cardColor: Color
    val textColor: Color
    val textDecoration: TextDecoration

    if (isDone) {
        with(MaterialTheme.colorScheme) {
            cardColor = surfaceVariant
            textColor = onSurfaceVariant
        }
        textDecoration = TextDecoration.LineThrough
    } else {
        with(deadline) {
            cardColor = getBackgroundColor()
            textColor = getOnBackgroundColor()
        }
        textDecoration = TextDecoration.None
    }

    Card(
        modifier = modifier.clickable { onTodoEvent(TodoEvent.Detail) },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.size(24.dp),
                checked = isDone,
                onCheckedChange = { onTodoEvent.invoke(TodoEvent.Done(it)) })
            Spacer(modifier = Modifier.size(8.dp))
            VerticalDivider(
                Modifier
                    .fillMaxHeight(), color = deadline.getOnBackgroundColor(),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    textDecoration = textDecoration
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = description, color = textColor, textDecoration = textDecoration
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.Top)
                    .clickable {
                        onTodoEvent.invoke(TodoEvent.Delete)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete button",
                tint = deadline.getOnBackgroundColor()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = deadline.timeLeft,
                fontStyle = FontStyle.Italic,
                color = textColor,
                textDecoration = textDecoration
            )
        }
    }
}

@Preview
@Composable
fun TodoPreview() {
    Todo(
        title = "Preview Todo",
        description = "Preview description",
        isDone = false,
        deadline = ToDoDeadline.FAR("1 Year left", "Jan 1, 2022"),
        onTodoEvent = {

        }
    )
}