package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.conversation.ConversationComponent
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.screen.two_pane.HomeWithConversationScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    lastMessages: List<Message>,
    messages: List<Message>,
    currentUser: User,
    selectedContact: Contact,
    onSelectedContactChange: (Contact?) -> Unit,
    onSend: (Message) -> Unit,
    displayFeatures: List<DisplayFeature>,
) {
    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            ConversationComponent(
                modifier = modifier,
                messages = messages,
                currentUser = currentUser,
                onBack = onBack,
                contact = selectedContact,
                onSend = onSend,
            )
        },
        medium = {
            HomeWithConversationScreen(
                displayFeatures = displayFeatures,
                messages = messages,
                currentUser = currentUser,
                selectedContact = selectedContact,
                onSelectedContactChange = onSelectedContactChange,
                activity = activity,
                drawerState = drawerState,
                scope = scope,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
                navItems = navItems,
                onNavigate = onNavigate,
                lastMessages = lastMessages,
                onSend = onSend,
            )
        },
    )

}