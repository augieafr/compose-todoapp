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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.ui.model.TodoUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    modifier: Modifier,
    todoList: List<TodoUiModel>,
    onTodoEvent: (Int, ToDoEvent) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { Text(text = "KMM ToDo") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onTodoEvent.invoke(0, ToDoEvent.Add)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            itemsIndexed(todoList) { index, todo ->
                ToDo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(IntrinsicSize.Max),
                    todoModel = todo,
                    onTodoEvent = { event ->
                        onTodoEvent.invoke(index, event)
                    }
                )
            }
        }
    }
}

@Composable
fun ToDo(
    todoModel: TodoUiModel,
    modifier: Modifier = Modifier,
    onTodoEvent: (ToDoEvent) -> Unit
) {
    val cardColor: Color
    val textColor: Color
    val textDecoration: TextDecoration

    if (todoModel.isDone) {
        with(MaterialTheme.colorScheme) {
            cardColor = surfaceVariant
            textColor = onSurfaceVariant
        }
        textDecoration = TextDecoration.LineThrough
    } else {
        with(todoModel.deadLine) {
            cardColor = getBackgroundColor()
            textColor = getOnBackgroundColor()
        }
        textDecoration = TextDecoration.None
    }

    Card(
        modifier = modifier.clickable { onTodoEvent.invoke(ToDoEvent.Detail) },
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
                checked = todoModel.isDone,
                onCheckedChange = { onTodoEvent.invoke(ToDoEvent.Done(it)) })
            Spacer(modifier = Modifier.size(8.dp))
            Divider(
                Modifier
                    .width(1.dp)
                    .fillMaxHeight(), color = todoModel.deadLine.getOnBackgroundColor()
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = todoModel.title,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    textDecoration = textDecoration
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = todoModel.description, color = textColor, textDecoration = textDecoration
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.Top)
                    .clickable {
                        onTodoEvent.invoke(ToDoEvent.Delete)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete button",
                tint = todoModel.deadLine.getOnBackgroundColor()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = todoModel.deadLine.timeLeft,
                fontStyle = FontStyle.Italic,
                color = textColor,
                textDecoration = textDecoration
            )
        }
    }
}

sealed class ToDoEvent {
    object Delete : ToDoEvent()
    object Detail : ToDoEvent()
    class Done(val isDone: Boolean) : ToDoEvent()
    object Add : ToDoEvent()
}