package com.augieafr.todo_app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.ui.theme.TODOAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(route: String?, onNavigationUp: (() -> Unit)? = null, onActionClicked: () -> Unit) {
    if (route == null) {
        return
    }

    val windowInsets = WindowInsets(left = 16.dp, right = 16.dp)
    when (route) {
        Screen.Home.route -> {
            TopAppBar(windowInsets = windowInsets, title = { Text(text = "Todo App") }, actions = {
                Icon(

                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "About me"
                )
            })
        }

        Screen.DetailTodo.route -> {
            var isOnEdit by rememberSaveable { mutableStateOf(false) }
            TopAppBar(
                windowInsets = windowInsets,
                title = { Text(text = "Detail Todo") },
                actions = {
                    val modifier = Modifier.clickable {
                        isOnEdit = !isOnEdit
                        onActionClicked()
                    }
                    Icon(
                        modifier = modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = if (isOnEdit) Icons.Filled.Done else Icons.Filled.Edit,
                        contentDescription = "Edit Todo"
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = modifier,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        text = if (isOnEdit) "Done" else "Edit"
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onNavigationUp?.invoke() },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigation Up"
                    )
                })
        }

        Screen.Profile.route -> {
            TopAppBar(
                windowInsets = windowInsets,
                title = { Text(text = "About Me") },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onNavigationUp?.invoke() },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigation Up"
                    )
                })
        }
    }
}

@Preview
@Composable
fun TodoAppBarPreview() {
    TODOAppTheme {
        TodoAppBar(route = Screen.DetailTodo.route, onNavigationUp = {}) {

        }
    }
}