package fr.messager.popmes.common

import android.app.Activity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fr.messager.popmes.common.Constants.gson
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
            ConversationScreen(
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
}