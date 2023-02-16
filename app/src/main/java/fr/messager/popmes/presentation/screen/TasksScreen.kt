package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.state.saver.InstantSaver
import fr.messager.popmes.presentation.components.state.saver.InstantSaverNullable
import fr.messager.popmes.presentation.components.task.TaskComponent
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.TaskParams
import fr.messager.popmes.presentation.screen.two_pane.TaskWithAddTaskAndAddScheduleScreen
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    contacts: List<User>,
    tasks: List<Task>,
    displayFeatures: List<DisplayFeature>,
    onValidate: (Task) -> Unit,
    onAddContact: (Contact) -> Unit,
    onRemoveContact: (Int) -> Unit,
    contactsAdded: List<Contact>,
    onToAddTaskVisibilityChange: (Boolean) -> Unit,
    toAddTaskVisibility: Boolean,
    onToAddScheduleVisibilityChange: (Boolean) -> Unit,
    toAddScheduleVisibility: Boolean,
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var openDialogEndDate by rememberSaveable { mutableStateOf(false) }
    var openDialogBeginDate by rememberSaveable { mutableStateOf(false) }
    var beginDate: Instant by rememberSaveable(stateSaver = InstantSaver) { mutableStateOf(Instant.now()) }
    var endDate: Instant? by rememberSaveable(stateSaver = InstantSaverNullable) { mutableStateOf(null) }
    var selectedPriorityIndex by rememberSaveable { mutableStateOf(-1) }

    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            TaskComponent(
                activity = activity,
                navItems = navItems,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                onNavigate = onNavigate,
                onAddNewTask = {
                    val taskParams = TaskParams(
                        contacts = contacts,
                        toAddScheduleVisibility = false,
                        toAddTaskVisibility = true,
                    )
                    onNavigate(
                        Screen.AddTask.navigate(taskParams.toHex())
                    )
                },
                onAddNewSchedule = { /*TODO*/ },
                tasks = tasks,
                modifier = modifier,
            )
        },
        medium = {
            TaskWithAddTaskAndAddScheduleScreen(
                displayFeatures = displayFeatures,
                modifier = modifier,
                onValidate = onValidate,
                beginDate = beginDate,
                endDate = endDate,
                selectedPriorityIndex = selectedPriorityIndex,
                onBeginDateChange = { beginDate = it },
                onEndDateChange = { endDate = it },
                onSelectedPriorityIndexChange = { selectedPriorityIndex = it },
                contacts = contacts,
                onAddContact = onAddContact,
                onRemoveContact = onRemoveContact,
                contactsAdded = contactsAdded,
                title = title,
                onTitleChange = { title = it },
                onDescriptionChange = { description = it },
                description = description,
                onOpenDialogBeginDateChange = { openDialogBeginDate = it },
                openDialogBeginDate = openDialogBeginDate,
                onOpenDialogEndDateChange = { openDialogEndDate = it },
                openDialogEndDate = openDialogEndDate,
                tasks = tasks,
                toAddTaskVisibility = toAddTaskVisibility,
                toAddScheduleVisibility = toAddScheduleVisibility,
                onAddNewTask = {
                    onToAddScheduleVisibilityChange(false)
                    onToAddTaskVisibilityChange(true)
                },
                onAddNewSchedule = {
                    onToAddScheduleVisibilityChange(true)
                    onToAddTaskVisibilityChange(false)
                },
            )
        }
    )
}