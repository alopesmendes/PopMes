package fr.messager.popmes.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fr.messager.popmes.common.Constants.gson
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.NavData

object Extension {
    inline fun <reified V: NavData> V.mapNavDataToJson(): String {
        return gson.toJson(this)
    }

    inline fun <reified V: NavData> String?.mapToNavData(): V? {
        if (this.isNullOrBlank())
            return null
        return gson.fromJson(this, V::class.java)
    }

    fun NavGraphBuilder.contactsNavigation() {
        navigation(
            startDestination = Screen.Contacts.route(),
            route = Screen.Contacts.route(),
        ) {
            composable(
                route = Screen.Contacts.route(),
                arguments = Screen.Contacts.navParams(),
            ) {
                // TODO add screen contacts
            }

            composable(
                route = Screen.AddGroup.route(),
                arguments = Screen.AddGroup.navParams(),
            ) {
                // TODO add screen add group
            }

            composable(
                route = Screen.AddUser.route(),
                arguments = Screen.AddUser.navParams(),
            ) {
                // TODO add screen add group
            }


        }
    }

    fun NavGraphBuilder.conversationNavigation() {
        navigation(
            startDestination = Screen.Conversation.route(),
            route = Screen.Conversation.route(),
        ) {
            composable(
                route = Screen.Conversation.route(),
                arguments = Screen.Conversation.navParams(),
            ) {
                // TODO add screen conversation
            }

            composable(
                route = Screen.FileGenerator.route(),
                arguments = Screen.FileGenerator.navParams(),
            ) {
                // TODO add screen file generator
            }
        }
    }

    fun NavGraphBuilder.tasksNavigation() {
        navigation(
            startDestination = Screen.Tasks.route(),
            route = Screen.Tasks.route(),
        ) {
            composable(
                route = Screen.Tasks.route(),
                arguments = Screen.Tasks.navParams(),
            ) {
                // TODO add screen tasks
            }

            composable(
                route = Screen.TasksElement.route(),
                arguments = Screen.TasksElement.navParams(),
            ) {
                // TODO add screen tasks element
            }
        }
    }
}