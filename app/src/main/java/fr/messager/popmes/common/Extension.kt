package fr.messager.popmes.common

import android.app.Activity
import android.text.format.DateUtils
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.contact.User
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
import fr.messager.popmes.presentation.view_models.ConversationViewModel
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

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

    @OptIn(ExperimentalMaterial3Api::class)
    fun NavGraphBuilder.contactsNavigation(
        activity: Activity,
        navItems: List<NavItem>,
        drawerState: DrawerState,
        scope: CoroutineScope,
        selectedItem: Int,
        onSelectedItemChange: (Int) -> Unit,
        onNavigate: (String) -> Unit,
        onBack: () -> Unit,
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
                onBack = onBack,
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

    private val TAG = Extension::class.simpleName

    @OptIn(ExperimentalMaterial3Api::class)
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
        onBack: () -> Unit,
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
                onBack = onBack,
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

    fun NavHostController.navigateTo(destination: String) {
        navigate(destination) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }
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