package fr.messager.popmes.common

import android.app.Activity
import android.text.format.DateUtils
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.messager.popmes.common.Constants.gson
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.NavData
import fr.messager.popmes.presentation.screen.AddGroupScreen
import fr.messager.popmes.presentation.screen.AddUserScreen
import fr.messager.popmes.presentation.screen.ContactsScreen
import fr.messager.popmes.presentation.screen.ConversationScreen
import fr.messager.popmes.presentation.screen.FileGeneratorScreen
import fr.messager.popmes.presentation.screen.TasksElementScreen
import fr.messager.popmes.presentation.screen.TasksScreen
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

object Extension {
    inline fun <reified V : NavData> V.mapNavDataToJson(): String {
        return gson.toJson(this)
    }

    inline fun <reified V : NavData> String?.mapToNavData(): V? {
        if (this.isNullOrBlank())
            return null
        return gson.fromJson(this, V::class.java)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun NavGraphBuilder.contactsNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
    ) {
        composable(
            route = Screen.Contacts.route(),
            arguments = Screen.Contacts.navParams(),
        ) {
            ContactsScreen(
                activity = activity,
                navItems = navItems,
                onNavigate = onNavigate,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
            )
        }

        composable(
            route = Screen.AddGroup.route(),
            arguments = Screen.AddGroup.navParams(),
        ) {
            AddGroupScreen(
                activity = activity,
                onNavigate = onNavigate,
            )
        }

        composable(
            route = Screen.AddUser.route(),
            arguments = Screen.AddUser.navParams(),
        ) {
            AddUserScreen(
                activity = activity,
                onNavigate = onNavigate,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun NavGraphBuilder.conversationNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
    ) {
        composable(
            route = Screen.Conversation.route(),
            arguments = Screen.Conversation.navParams(),
        ) {
            val currentUser = User(
                id = "0",
                firstName = "Ailton",
                lastName = "Lopes Mendes",
                phoneNumber = "+33781831024",
            )
            val manuel = User(
                id = "1",
                firstName = "Manuel",
                lastName = "Lopes Mendes",
                phoneNumber = "+33681831024",
            )
            ConversationScreen(
                activity = activity,
                navItems = navItems,
                onNavigate = onNavigate,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                currentUser = currentUser,
                messages = listOf(
                    Message(
                        id = "0",
                        messageType = MessageType.Message,
                        from = currentUser,
                        to = manuel,
                        date = Instant.now(),
                    ),
                    Message(
                        id = "1",
                        messageType = MessageType.Message,
                        from = manuel,
                        to = currentUser,
                        date = Instant.now(),
                    ),
                    Message(
                        id = "2",
                        messageType = MessageType.Message,
                        from = manuel,
                        to = currentUser,
                        date = Instant.now(),
                    ),
                    Message(
                        id = "3",
                        messageType = MessageType.Message,
                        from = currentUser,
                        to = manuel,
                        date = Instant.now(),
                    )
                )
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun NavGraphBuilder.tasksNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
    ) {
        composable(
            route = Screen.Tasks.route(),
            arguments = Screen.Tasks.navParams(),
        ) {
            TasksScreen(
                activity = activity,
                navItems = navItems,
                onNavigate = onNavigate,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
            )
        }

        composable(
            route = Screen.TasksElement.route(),
            arguments = Screen.TasksElement.navParams(),
        ) {
            TasksElementScreen(
                activity = activity,
                onNavigate = onNavigate,
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
}