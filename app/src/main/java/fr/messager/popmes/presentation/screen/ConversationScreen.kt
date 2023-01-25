package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.screen.two_pane.HomeWithConversationScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
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
    messages: List<Message>,
    currentUser: User,
    selectedContact: Contact,
    onSelectedContactChange: (Contact?) -> Unit,
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
            )
        },
    )

}