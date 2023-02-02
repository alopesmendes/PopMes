package fr.messager.popmes.presentation.screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.components.views.HomeComponent
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.screen.two_pane.HomeWithConversationScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(
    activity: Activity,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    navItems: List<NavItem>,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    messages: List<Message>,
    lastMessages: List<Message>,
    currentUser: User,
    selectedContact: Contact?,
    onSelectedContactChange: (Contact?) -> Unit,
    displayFeatures: List<DisplayFeature>,
) {
    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            if (selectedContact != null) {
                ConversationComponent(
                    modifier = modifier,
                    messages = messages,
                    currentUser = currentUser,
                    onBack = onBack,
                    contact = selectedContact,
                )
            } else {
                HomeComponent(
                    modifier = modifier,
                    messages = lastMessages,
                    onNavigate = onNavigate,
                    onSelectedItemChange = onSelectedItemChange,
                    selectedItem = selectedItem,
                    scope = scope,
                    drawerState = drawerState,
                    activity = activity,
                    navItems = navItems,
                )
            }
        },
        medium = {
            LaunchedEffect(selectedContact) {
                Log.d(TAG, "HomeScreen: $selectedContact")
            }
            HomeWithConversationScreen(
                displayFeatures = displayFeatures,
                messages = messages,
                currentUser = currentUser,
                selectedContact = selectedContact,
                onSelectedContactChange = onSelectedContactChange,
                navItems = navItems,
                activity = activity,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                modifier = modifier,
                onNavigate = onNavigate,
                lastMessages = lastMessages,
            )
        },
    )
}

private const val TAG = "HomeScreen"