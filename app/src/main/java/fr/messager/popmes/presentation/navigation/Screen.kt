package fr.messager.popmes.presentation.navigation

import android.os.Parcelable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import fr.messager.popmes.common.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Screen(
    private val route: String,
    private val params: List<String> = emptyList()
) : Parcelable {
    fun route() = "$route${params.joinToString("/", "/") { "{$it}" }}"

    fun navigate(
        vararg params: String,
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
        route = "AddGroup",
        params = listOf(Constants.PARAM_CONTACTS),
    )

    object AddUser: Screen(
        route = "AddUser",
        params = listOf(Constants.PARAM_CONTACTS),
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
        params = listOf(Constants.PARAM_CONVERSATION)
    )
}
