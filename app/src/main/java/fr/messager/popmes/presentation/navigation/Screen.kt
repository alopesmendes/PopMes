package fr.messager.popmes.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    private val route: String,
    private val params: List<String> = emptyList()
) {
    fun route() = "$route${params.joinToString("/", "/") { "{$it}" }}"

    fun navigate(
        params: List<String>
    ) = "$route${params.joinToString("/", "/")}"

    fun navParams(): List<NamedNavArgument> = params.map {
        navArgument(it) {
            type = NavType.StringType
        }
    }

    object Home: Screen(
        route = "Home"
    )

    object Contacts: Screen(
        route = "Contacts",
    )

    object AddGroup: Screen(
        route = "AddGroup"
    )

    object AddUser: Screen(
        route = "AddUser"
    )

    object FileGenerator: Screen(
        route = "FileGenerator"
    )

    object Tasks: Screen(
        route = "Tasks"
    )

    object TasksElement: Screen(
        route = "TasksElement"
    )

    object Conversation: Screen(
        route = "Conversation",
    )



}
