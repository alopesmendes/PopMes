package fr.messager.popmes.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fr.messager.popmes.common.Constants.gson
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.NavData
import fr.messager.popmes.presentation.screen.AddGroupScreen
import fr.messager.popmes.presentation.screen.AddUserScreen
import fr.messager.popmes.presentation.screen.ContactsScreen
import fr.messager.popmes.presentation.screen.FileGeneratorScreen
import fr.messager.popmes.presentation.screen.TasksElementScreen
import fr.messager.popmes.presentation.screen.TasksScreen

object Extension {
    inline fun <reified V: NavData> V.mapNavDataToJson(): String {
        return gson.toJson(this)
    }

    inline fun <reified V: NavData> String?.mapToNavData(): V? {
        if (this.isNullOrBlank())
            return null
        return gson.fromJson(this, V::class.java)
    }

    fun NavGraphBuilder.contactsNavigation(
        navController: NavController,
    ) {
        navigation(
            startDestination = Screen.Contacts.route(),
            route = Screen.Contacts.route(),
        ) {
            composable(
                route = Screen.Contacts.route(),
                arguments = Screen.Contacts.navParams(),
            ) {
                ContactsScreen(navController = navController)
            }

            composable(
                route = Screen.AddGroup.route(),
                arguments = Screen.AddGroup.navParams(),
            ) {
                AddGroupScreen(navController = navController,)
            }

            composable(
                route = Screen.AddUser.route(),
                arguments = Screen.AddUser.navParams(),
            ) {
                AddUserScreen(navController = navController)
            }
        }
    }

    fun NavGraphBuilder.conversationNavigation(
        navController: NavController
    ) {
        navigation(
            startDestination = Screen.Conversation.route(),
            route = Screen.Conversation.route(),
        ) {
            composable(
                route = Screen.Conversation.route(),
                arguments = Screen.Conversation.navParams(),
            ) {
                ContactsScreen(navController = navController)
            }

            composable(
                route = Screen.FileGenerator.route(),
                arguments = Screen.FileGenerator.navParams(),
            ) {
                FileGeneratorScreen(navController = navController)
            }
        }
    }

    fun NavGraphBuilder.tasksNavigation(
        navController: NavController
    ) {
        navigation(
            startDestination = Screen.Tasks.route(),
            route = Screen.Tasks.route(),
        ) {
            composable(
                route = Screen.Tasks.route(),
                arguments = Screen.Tasks.navParams(),
            ) {
                TasksScreen(navController = navController)
            }

            composable(
                route = Screen.TasksElement.route(),
                arguments = Screen.TasksElement.navParams(),
            ) {
                TasksElementScreen(navController = navController)
            }
        }
    }
}