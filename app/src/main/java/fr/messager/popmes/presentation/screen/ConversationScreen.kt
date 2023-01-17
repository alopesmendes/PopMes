package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    messages: List<Message>,
    currentUser: User,
) {
    Navigation(
        activity = activity,
        navItems = navItems,
        onNavigate = onNavigate,
        drawerState = drawerState,
        scope = scope,
        selectedItem = selectedItem,
        onSelectedItemChange = onSelectedItemChange,
    ) { modifier ->
        BackHandler(onBack = onBack)

        ConversationComponent(
            modifier = modifier,
            messages = messages,
            currentUser = currentUser
        )
    }
}