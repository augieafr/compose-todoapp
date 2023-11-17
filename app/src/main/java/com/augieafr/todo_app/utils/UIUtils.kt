package com.augieafr.todo_app.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.augieafr.todo_app.ui.model.ToDoDeadline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun dueDateToDeadline(dueDate: String): ToDoDeadline {
    val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val currentDate = Date()
    val endDate = sdf.parse(dueDate)

    val timeLeft = (endDate?.time ?: 0) - currentDate.time
    val dayLeft = TimeUnit.MILLISECONDS.toDays(timeLeft)
    return when {
        dayLeft <= 0 -> ToDoDeadline.NEAR("Due date has passed")
        dayLeft < 3 -> ToDoDeadline.NEAR("$dayLeft days left")
        dayLeft < 7 -> ToDoDeadline.MID("$dayLeft days left")
        else -> {
            ToDoDeadline.FAR(
                "${
                    endDate?.let {
                        sdf.applyPattern("MMM dd, yyyy")
                        sdf.format(endDate)
                    }
                }"
            )
        }
    }
}

fun String?.changeDatePattern(
    sourcePattern: String,
    targetPattern: String
): String {
    if (this == null) return ""
    val parser = SimpleDateFormat(sourcePattern, Locale.getDefault())
    val formatter = SimpleDateFormat(targetPattern, Locale.getDefault())
    return parser.parse(this)?.let { formatter.format(it) } ?: ""
}

fun Long?.toDateFormat(pattern: String = "MMM dd, yyyy"): String {
    if (this == null) return ""
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return sdf.format(calendar.time)
}

fun String.toDateMillis(pattern: String = "MMM dd, yyyy"): Long? {
    if (this.isEmpty()) return null
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.parse(this)?.let {
        it.time + TimeZone.getDefault().rawOffset
    }
}

@Composable
fun Modifier.noRippleClickable(onClicked: () -> Unit) = clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = null
) {
    onClicked()
}

fun SnackbarHostState.showSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionPerformed: (() -> Unit)? = null,
    onDismissed: (() -> Unit)? = null,
    scope: CoroutineScope
) {
    val dismissed = {
        onDismissed?.invoke()
            ?: this@showSnackbar.currentSnackbarData?.dismiss()
    }
    scope.launch {
        when (showSnackbar(message, actionLabel = actionLabel, duration = SnackbarDuration.Short)) {
            SnackbarResult.Dismissed -> dismissed()

            SnackbarResult.ActionPerformed -> {
                onActionPerformed?.invoke()
                dismissed()
            }
        }
    }
}