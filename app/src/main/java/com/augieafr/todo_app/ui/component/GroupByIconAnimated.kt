package com.augieafr.todo_app.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.augieafr.todo_app.ui.model.GroupBy
import kotlinx.coroutines.delay

@Composable
fun GroupByIconAnimated(groupBy: GroupBy, onGroupByChanged: () -> Unit) {
    val animationDuration = 1000
    val rotation = remember { Animatable(0f) }
    var isShowGroupByAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isShowGroupByAnimation) {
        if (isShowGroupByAnimation) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(animationDuration, easing = LinearEasing)
                )
            )
            delay(animationDuration.toLong())
            rotation.snapTo(0f)
            isShowGroupByAnimation = false
        }
    }
    val groupByIcon =
        if (groupBy == GroupBy.DEADLINE) Icons.Filled.CheckCircle else Icons.Filled.DateRange
    Icon(
        modifier = Modifier
            .clickable {
                if (!isShowGroupByAnimation) {
                    isShowGroupByAnimation = true
                    onGroupByChanged()
                }
            }
            .rotate(rotation.value),
        imageVector = if (isShowGroupByAnimation) Icons.Filled.Refresh else groupByIcon,
        contentDescription = "Group by",
    )

    AnimatedVisibility(
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        ), visible = isShowGroupByAnimation
    ) {
        Text(text = groupBy.wording)
    }


}