package fr.messager.popmes.presentation.screen.two_pane

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.presentation.components.task.AddScheduleComponent
import fr.messager.popmes.presentation.components.task.AddTaskComponent
import fr.messager.popmes.presentation.components.task.TaskComponent
import java.time.Instant

@Composable
fun TaskWithAddTaskAndAddScheduleScreen(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    onAddNewTask: () -> Unit,
    onAddNewSchedule: () -> Unit,
    toAddTaskVisibility: Boolean,
    toAddScheduleVisibility: Boolean,
    tasks: List<Task>,
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
    TwoPane(
        first = {
            TaskComponent(
                onAddNewTask = onAddNewTask,
                onAddNewSchedule = onAddNewSchedule,
                tasks = tasks,
                modifier = Modifier.fillMaxSize()
            )
        },
        second = {
            if (toAddTaskVisibility) {
                AddTaskComponent(
                    onValidate = onValidate,
                    beginDate = beginDate,
                    endDate = endDate,
                    selectedPriorityIndex = selectedPriorityIndex,
                    onBeginDateChange = onBeginDateChange,
                    onEndDateChange = onEndDateChange,
                    onSelectedPriorityIndexChange = onSelectedPriorityIndexChange,
                    contacts = contacts,
                    onAddContact = onAddContact,
                    onRemoveContact = onRemoveContact,
                    contactsAdded = contactsAdded,
                    title = title,
                    onTitleChange = onTitleChange,
                    onDescriptionChange = onDescriptionChange,
                    description = description,
                    onOpenDialogBeginDateChange = onOpenDialogBeginDateChange,
                    openDialogBeginDate = openDialogBeginDate,
                    onOpenDialogEndDateChange = onOpenDialogEndDateChange,
                    openDialogEndDate = openDialogEndDate,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            if (toAddScheduleVisibility) {
                AddScheduleComponent(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        },
        strategy = HorizontalTwoPaneStrategy(
            splitFraction = .5f,
            gapWidth = 16.dp,
        ),
        displayFeatures = displayFeatures,
        modifier = modifier,
        foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
    )
}