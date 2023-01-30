package fr.messager.popmes.presentation.components.dialog

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import fr.messager.popmes.R
import fr.messager.popmes.common.Extension.displayDate
import fr.messager.popmes.presentation.components.buttons.PopMesTextButton
import java.time.DayOfWeek
import java.time.Instant
import java.time.Period
import java.time.ZoneId

private const val TAG = "PopMesDatePickerDialog"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopMesDatePickerDialog(
    openDialog: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onDateSelect: (Instant) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DatePickerDefaults.shape,
    tonalElevation: Dp = DatePickerDefaults.TonalElevation,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    hasBlockWeekend: Boolean = false,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    dayAfter: Instant,
) {
    LaunchedEffect(dayAfter) {
        Log.d(TAG, "PopMesDatePickerDialog: ${dayAfter.displayDate()}")
    }

    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled by remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = { onOpenDialogChange(false) },
            confirmButton = {
                PopMesTextButton(
                    onClick = {
                        onOpenDialogChange(false)
                        datePickerState.selectedDateMillis?.let { date ->
                            onDateSelect(Instant.ofEpochMilli(date))
                        }
                    },
                    enabled = confirmEnabled,
                    rowModifier = Modifier,
                ) {
                    Text(text = stringResource(id = R.string.validate))
                }
            },
            modifier = modifier,
            dismissButton = {
                PopMesTextButton(
                    onClick = { onOpenDialogChange(false) },
                    rowModifier = Modifier,
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            shape = shape,
            tonalElevation = tonalElevation,
            colors = colors,
            properties = properties,
        ) {
            DatePicker(
                datePickerState = datePickerState,
                dateValidator = { utcDateInMills ->
                    val date = Instant.ofEpochMilli(utcDateInMills)
                    val value = if (hasBlockWeekend) {
                        val dayOfWeek = Instant
                            .ofEpochMilli(utcDateInMills)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate().dayOfWeek


                        dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.SATURDAY
                    } else {
                        true
                    }

                    value && date.isAfter(dayAfter.minus(Period.ofDays(2)))
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PopMesDatePickerDialogPreview() {
    var openDialog by remember {
        mutableStateOf(false)
    }
    var instant: Instant? by remember {
        mutableStateOf(null)
    }
    Button(onClick = { openDialog = true }) {
        Text(text = "Dialog ${instant?.displayDate() ?: ""}")
    }

    PopMesDatePickerDialog(
        openDialog = openDialog,
        onOpenDialogChange = { openDialog = it },
        onDateSelect = {
            instant = it
        },
        hasBlockWeekend = true,
        modifier = Modifier.fillMaxSize(),
        dayAfter = Instant.now(),
    )
}