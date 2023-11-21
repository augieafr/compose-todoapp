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
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun dueDateToDeadline(dueDate: String, sourcePattern: String = "yyyyMMdd"): ToDoDeadline {
    val targetPattern = "MMM dd, yyyy"
    val sdf = SimpleDateFormat(sourcePattern, Locale.getDefault())

    val endDate = sdf.parse(dueDate)
    val calendar = Calendar.getInstance()
    if (endDate != null) {
        calendar.time = endDate
    }
    val currentDate = LocalDate.now()
    val dayOffset = calendar.get(Calendar.DAY_OF_MONTH) - currentDate.dayOfMonth

    val uiDueDate = if (sourcePattern == targetPattern) dueDate else dueDate.changeDatePattern(
        sourcePattern,
        targetPattern
    )
    return when {
        dayOffset <= 0 -> ToDoDeadline.NEAR("Due date has passed", uiDueDate)
        dayOffset < 3 -> ToDoDeadline.NEAR("$dayOffset days left", uiDueDate)
        dayOffset < 7 -> ToDoDeadline.MID("$dayOffset days left", uiDueDate)
        else -> ToDoDeadline.FAR(uiDueDate, uiDueDate)
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