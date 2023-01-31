package fr.messager.popmes.presentation.components.views

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.common.Extension.displayDate
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.domain.model.task.TaskType
import fr.messager.popmes.domain.model.task.priorities
import fr.messager.popmes.presentation.components.buttons.ValidationButton
import fr.messager.popmes.presentation.components.dialog.PopMesDatePickerDialog
import fr.messager.popmes.presentation.components.list.PopMesDropMenu
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.list.PopMesListRow
import fr.messager.popmes.presentation.components.list.items.ContactItem
import fr.messager.popmes.presentation.components.list.items.SmallContactItem
import fr.messager.popmes.presentation.components.text.InputDescriptionTextField
import fr.messager.popmes.presentation.components.text.PopMesOutlinedTextField
import java.time.Instant
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddTaskComponent(
    modifier: Modifier = Modifier,
    onValidate: (Task) -> Unit,
    beginDate: Instant,
    endDate: Instant?,
    selectedPriorityIndex: Int,
    onBeginDateChange: (Instant) -> Unit,
    onEndDateChange: (Instant) -> Unit,
    onSelectedPriorityIndexChange: (Int) -> Unit,
    contacts: List<Contact>,
    onAddContact: (Contact) -> Unit,
    onRemoveContact: (Int) -> Unit,
    contactsAdded: List<Contact>,
    title: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    description: String,
    onOpenDialogBeginDateChange: (Boolean) -> Unit,
    openDialogBeginDate: Boolean,
    onOpenDialogEndDateChange: (Boolean) -> Unit,
    openDialogEndDate: Boolean,

    ) {
    val painter = painterResource(id = R.drawable.avatar_0)

    PopMesDatePickerDialog(
        openDialog = openDialogBeginDate,
        onOpenDialogChange = onOpenDialogBeginDateChange,
        onDateSelect = onBeginDateChange,
        hasBlockWeekend = true,
        dayAfter = Instant.now(),
    )

    PopMesDatePickerDialog(
        openDialog = openDialogEndDate,
        onOpenDialogChange = onOpenDialogEndDateChange,
        onDateSelect = onEndDateChange,
        hasBlockWeekend = true,
        dayAfter = beginDate,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f),
        ) {
            PopMesOutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.title)) },
                singleLine = true,
            )

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
                    onClick = { onOpenDialogBeginDateChange(true) },
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
                    onClick = { onOpenDialogEndDateChange(true) },
                )
            }

            PopMesDropMenu(
                selectedIndex = selectedPriorityIndex,
                onDisplay = { stringResource(id = it.stringId) },
                onSelectedIndexChange = onSelectedPriorityIndexChange,
                values = priorities,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painterResource(id = priorities[it].drawableId),
                        contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = R.string.priorities))
                }
            )

            InputDescriptionTextField(
                description = description,
                onDescriptionChange = onDescriptionChange,
                modifier = Modifier.fillMaxWidth(),
            )

            PopMesListRow(
                values = contactsAdded,
                modifier = Modifier.fillMaxWidth(),
                key = {}
            ) { index, value ->
                SmallContactItem(
                    name = value.fullName(),
                    description = "Here",
                    icon = painter,
                    onClick = { onRemoveContact(index) },
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing,
                        )
                    )
                )
            }

            PopMesListColumn(
                values = contacts,
                key = { contacts[it].id },
                modifier = Modifier.weight(1f),
            ) { _, value ->
                ContactItem(
                    name = value.fullName(),
                    description = "Here",
                    icon = painter,
                    onClick = { onAddContact(value) },
                )
            }
        }

        ValidationButton(
            onClick = {
                if (selectedPriorityIndex in priorities.indices) {
                    onValidate(
                        Task(
                            id = "${UUID.randomUUID()}",
                            title = title,
                            priority = priorities[selectedPriorityIndex],
                            description = description,
                            beginDate = beginDate,
                            endDate = endDate,
                            type = TaskType.Task,
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun AddTaskComponentPreview() {
    var beginDate by remember { mutableStateOf(Instant.now()) }
    var endDate: Instant? by remember { mutableStateOf(null) }
    var selectedContactIndex by remember { mutableStateOf(-1) }
    val contactsAdded: SnapshotStateList<Contact> = remember { mutableStateListOf() }
    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var openDialogBeginDate by rememberSaveable { mutableStateOf(false) }
    var openDialogEndDate by rememberSaveable { mutableStateOf(false) }

    val users = (1..9).map {
        User(
            id = "$it",
            firstName = "user $it",
            lastName = "last name $it",
            phoneNumber = "+3378183102$it",
        )
    }

    AddTaskComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        beginDate = beginDate,
        endDate = endDate,
        onBeginDateChange = { beginDate = it },
        onEndDateChange = { endDate = it },
        description = description,
        contactsAdded = contactsAdded,
        contacts = users,
        onAddContact = { contactsAdded.add(it) },
        onDescriptionChange = { description = it },
        onRemoveContact = { contactsAdded.removeAt(it) },
        onSelectedPriorityIndexChange = { selectedContactIndex = it },
        selectedPriorityIndex = selectedContactIndex,
        title = title,
        onTitleChange = { title = it },
        onValidate = { },
        onOpenDialogBeginDateChange = { openDialogBeginDate = it },
        onOpenDialogEndDateChange = { openDialogEndDate = it },
        openDialogBeginDate = openDialogBeginDate,
        openDialogEndDate = openDialogEndDate,
    )
}