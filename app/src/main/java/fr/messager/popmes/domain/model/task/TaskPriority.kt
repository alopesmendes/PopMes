package fr.messager.popmes.domain.model.task

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fr.messager.popmes.R

enum class TaskPriority(
    @StringRes val stringId: Int,
    @DrawableRes val drawableId: Int,
) {
    LOW(
        stringId = R.string.low,
        drawableId = R.drawable.ic_low_priority,
    ),
    HIGH(
        stringId = R.string.high,
        drawableId = R.drawable.ic_priority_high,
    ),
    URGENT(
        stringId = R.string.urgent,
        drawableId = R.drawable.ic_warning,
    ),
}

val priorities = listOf(
    TaskPriority.LOW,
    TaskPriority.HIGH,
    TaskPriority.URGENT,
)