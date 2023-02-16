package fr.messager.popmes.common

import android.app.Activity
import android.text.format.DateUtils
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams
import fr.messager.popmes.presentation.navigation.arguments.NavData
import fr.messager.popmes.presentation.screen.AddOrEditContactScreen
import fr.messager.popmes.presentation.screen.AddTaskScreen
import fr.messager.popmes.presentation.screen.ContactsScreen
import fr.messager.popmes.presentation.screen.ConversationScreen
import fr.messager.popmes.presentation.screen.FileGeneratorScreen
import fr.messager.popmes.presentation.screen.TasksScreen
import fr.messager.popmes.presentation.view_models.ContactsViewModel
import fr.messager.popmes.presentation.view_models.ConversationViewModel
import fr.messager.popmes.presentation.view_models.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Extension {

    inline fun <reified V : NavData, reified T : MessageLite> String?.mapToNavData(
        parseFrom: (ByteArray?) -> T,
        mapTo: T.() -> V,
    ): V? {
        return if (this.isNullOrBlank())
            null
        else
            parseFrom(this.hexStringToByteArray()).mapTo()
    }

    fun NavGraphBuilder.contactsNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
        onBack: () -> Unit,
        displayFeatures: List<DisplayFeature>,
    ) {
        composable(
            route = Screen.Contacts.route(),
            arguments = Screen.Contacts.navParams(),
        ) {
            val contactsViewModel: ContactsViewModel = hiltViewModel()
            val selectOrCreateContact by contactsViewModel.selectOrCreateContact.collectAsState()
            ContactsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                activity = activity,
                navItems = navItems,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                onNavigate = onNavigate,
                onBack = onBack,
                contacts = contactsViewModel.contacts,
                displayFeatures = displayFeatures,
                toAddContact = contactsViewModel::addContactToGroup,
                toRemoveContact = contactsViewModel::removeContactOfGroup,
                contactsAdded = contactsViewModel.contactsAddedToGroup,
                toAddUserComponentVisibility = contactsViewModel.toAddUserComponentVisibility,
                toAddGroupComponentVisibility = contactsViewModel.toAddGroupComponentVisibility,
                onToAddGroupComponentVisibilityChange = { contactsViewModel.toAddGroupComponentVisibility = it
                },
                onToAddUserComponentVisibilityChange = {
                    contactsViewModel.toAddUserComponentVisibility = it
                },
                onAdd = contactsViewModel::addContact,
                onDeleteContact = contactsViewModel::deleteContact,
                selectContact = selectOrCreateContact,
                onSelectContactChange = contactsViewModel::onContactChange,
                onMessageContact = {
                    val conversationParams = ConversationParams(
                        contact = it,
                        messages = listOf()
                    )
                    onNavigate(Screen.Conversation.navigate(conversationParams.toHex()))
                },
                onEditContact = contactsViewModel::toEditContact,
            )
        }

        composable(
            route = Screen.AddOrEditContact.route(),
            arguments = Screen.AddOrEditContact.navParams(),
        ) {
            val contactsViewModel: ContactsViewModel = hiltViewModel()
            val selectOrCreateContact by contactsViewModel.selectOrCreateContact.collectAsState()
            AddOrEditContactScreen(
                activity = activity,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                displayFeatures = displayFeatures,
                contacts = contactsViewModel.contacts,
                onAdd = contactsViewModel::addContact,
                toAddContact = contactsViewModel::addContactToGroup,
                toRemoveContact = contactsViewModel::removeContactOfGroup,
                toAddUserComponentVisibility = contactsViewModel.toAddUserComponentVisibility,
                toAddGroupComponentVisibility = contactsViewModel.toAddGroupComponentVisibility,
                onToAddUserComponentVisibilityChange = { contactsViewModel.toAddUserComponentVisibility = it },
                onToAddGroupComponentVisibilityChange = { contactsViewModel.toAddGroupComponentVisibility = it },
                onBack = onBack,
                onDeleteContact = contactsViewModel::deleteContact,
                selectContact = selectOrCreateContact,
                onSelectContactChange = contactsViewModel::onContactChange,
                contactsAdded = contactsViewModel.contactsAddedToGroup,
                onMessageContact = {
                    val conversationParams = ConversationParams(
                        contact = it,
                        messages = listOf()
                    )
                    onNavigate(Screen.Conversation.navigate(conversationParams.toHex()))
                },
                onEditContact = contactsViewModel::toEditContact,
            )
        }
    }

    private val TAG = Extension::class.simpleName

    fun NavGraphBuilder.conversationNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        currentUser: User,
        displayFeatures: List<DisplayFeature>,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
        onBack: () -> Unit,
    ) {
        composable(
            route = Screen.Conversation.route(),
            arguments = Screen.Conversation.navParams(),
        ) {
            val conversationViewModel: ConversationViewModel = hiltViewModel()
            Log.d(TAG, "conversationNavigation: ${conversationViewModel.messages.size}")
            ConversationScreen(
                modifier = Modifier.fillMaxSize(),
                activity = activity,
                navItems = navItems,
                onNavigate = onNavigate,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                currentUser = currentUser,
                messages = conversationViewModel.messages,
                onBack = onBack,
                selectedContact = conversationViewModel.selectedContact
                    ?: throw IllegalArgumentException("contact param null"),
                displayFeatures = displayFeatures,
                onSelectedContactChange = conversationViewModel::onSelectedContactChange,
                lastMessages = conversationViewModel.lastMessages,
                onSend = conversationViewModel::send,
            )
        }

        composable(
            route = Screen.FileGenerator.route(),
            arguments = Screen.FileGenerator.navParams(),
        ) {
            FileGeneratorScreen(
                activity = activity,
                onNavigate = onNavigate,
            )
        }
    }

    fun NavGraphBuilder.tasksNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
        onBack: () -> Unit,
        displayFeatures: List<DisplayFeature>,
        contacts: List<User>,
    ) {
        composable(
            route = Screen.Tasks.route(),
            arguments = Screen.Tasks.navParams(),
        ) {
            val taskViewModel: TaskViewModel = hiltViewModel()

            TasksScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                activity = activity,
                navItems = navItems,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                onNavigate = onNavigate,
                onBack = onBack,
                contacts = contacts,
                tasks = taskViewModel.tasks,
                displayFeatures = displayFeatures,
                onValidate = taskViewModel::onAddTask,
                onAddContact = taskViewModel::addContact,
                onRemoveContact = taskViewModel::removeContact,
                contactsAdded = taskViewModel.contactsAdded,
                onToAddTaskVisibilityChange = { taskViewModel.toAddTaskVisibility = it },
                toAddTaskVisibility = taskViewModel.toAddTaskVisibility,
                onToAddScheduleVisibilityChange = { taskViewModel.toAddScheduleVisibility = it },
                toAddScheduleVisibility = taskViewModel.toAddScheduleVisibility
            )
        }

        composable(
            route = Screen.AddTask.route(),
            arguments = Screen.AddTask.navParams(),
        ) {
            val taskViewModel: TaskViewModel = hiltViewModel()

            AddTaskScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                activity = activity,
                onBack = onBack,
                contacts = contacts,
                tasks = taskViewModel.tasks,
                displayFeatures = displayFeatures,
                onValidate = taskViewModel::onAddTask,
                onAddContact = taskViewModel::addContact,
                onRemoveContact = taskViewModel::removeContact,
                contactsAdded = taskViewModel.contactsAdded,
                onToAddTaskVisibilityChange = { taskViewModel.toAddTaskVisibility = it },
                toAddTaskVisibility = taskViewModel.toAddTaskVisibility,
                onToAddScheduleVisibilityChange = { taskViewModel.toAddScheduleVisibility = it },
                toAddScheduleVisibility = taskViewModel.toAddScheduleVisibility
            )
        }
    }

    fun Instant.dateAgo(
        now: Instant
    ): String {
        return DateUtils.getRelativeTimeSpanString(
            toEpochMilli(),
            now.toEpochMilli(),
            DateUtils.SECOND_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE,
        )?.toString() ?: ""
    }

    fun Instant.displayDate(
        dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE,
    ): String = dateTimeFormatter.format(this.atZone(ZoneId.systemDefault()).toLocalDate())


    fun NavHostController.navigateTo(destination: String) {
        navigate(destination) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            //popUpTo(graph.findStartDestination().id) {
            //    saveState = true
            //}
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun ByteArray.toHex() =
        this.joinToString(separator = "") { it.toInt().and(0xff).toString(16).padStart(2, '0') }

    fun String.hexStringToByteArray() =
        ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }
}