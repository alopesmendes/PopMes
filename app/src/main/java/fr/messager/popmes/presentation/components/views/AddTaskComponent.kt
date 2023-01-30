package fr.messager.popmes.presentation.components.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.common.Extension.displayDate
import fr.messager.popmes.presentation.components.dialog.PopMesDatePickerDialog
import fr.messager.popmes.presentation.components.text.PopMesOutlinedTextField
import java.time.Instant

private const val TAG = "AddTaskComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskComponent(
    modifier: Modifier = Modifier,
    beginDate: Instant,
    endDate: Instant?,
    onBeginDateChange: (Instant) -> Unit,
    onEndDateChange: (Instant) -> Unit,
) {
    var openDialogBeginDate by rememberSaveable {
        mutableStateOf(false)
    }

    var openDialogEndDate by rememberSaveable {
        mutableStateOf(false)
    }

    PopMesDatePickerDialog(
        openDialog = openDialogBeginDate,
        onOpenDialogChange = { openDialogBeginDate = it },
        onDateSelect = {
            onBeginDateChange(it)
            Log.d(TAG, "AddTaskComponent: begin->${it.displayDate()}")
        },
        hasBlockWeekend = true,
        dayAfter = Instant.now(),
    )

    PopMesDatePickerDialog(
        openDialog = openDialogEndDate,
        onOpenDialogChange = { openDialogEndDate = it },
        onDateSelect = {
            onEndDateChange(it)
            Log.d(TAG, "AddTaskComponent: end->${it.displayDate()}")
        },
        hasBlockWeekend = true,
        dayAfter = beginDate,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PopMesOutlinedTextField(
                value = beginDate.displayDate(),
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(id = R.string.begin_date),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_calendar_month),
                        contentDescription = "calendar",
                    )
                },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.weight(1f),
                onClick = { openDialogBeginDate = true },
            )

            PopMesOutlinedTextField(
                value = endDate?.displayDate() ?: "",
                onValueChange = {},
                label = {
                    Text(
                        text = "${stringResource(id = R.string.end_date)} (${stringResource(id = R.string.optional)})",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_calendar_month),
                        contentDescription = "calendar",
                    )
                },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.weight(1f),
                onClick = { openDialogEndDate = true },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskComponentPreview() {
    var beginDate by remember { mutableStateOf(Instant.now()) }
    var endDate: Instant? by remember { mutableStateOf(null) }

    AddTaskComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        beginDate = beginDate,
        endDate = endDate,
        onBeginDateChange = { beginDate = it },
        onEndDateChange = { endDate = it },
    )
}