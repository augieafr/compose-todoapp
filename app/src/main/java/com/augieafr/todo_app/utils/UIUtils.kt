package com.augieafr.todo_app.utils

import com.augieafr.todo_app.ui.model.ToDoDeadline
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
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
                        sdf.applyPattern("MM-dd-yyyy")
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

fun Long?.toDateFormat(pattern: String = "MM-dd-yyyy"): String {
    if (this == null) return ""
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return sdf.format(calendar.time)
}