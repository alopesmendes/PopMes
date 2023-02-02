package fr.messager.popmes.presentation.screen.two_pane

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.components.views.HomeComponent
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeWithConversationScreen(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    lastMessages: List<Message>,
    messages: List<Message>,
    currentUser: User,
    selectedContact: Contact?,
    onSelectedContactChange: (Contact?) -> Unit,
    activity: Activity,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onSend: (Message) -> Unit,
    navItems: List<NavItem>,
    onNavigate: (String) -> Unit,
) {
    Navigation(
        activity = activity,
        navItems = navItems,
        selectedItem = selectedItem,
        drawerState = drawerState,
        scope = scope,
        onSelectedItemChange = onSelectedItemChange,
        onNavigate = onNavigate,
        modifier = modifier.padding(vertical = 8.dp),
    ) { m ->
        TwoPane(
            first = {
                HomeComponent(
                    messages = lastMessages,
                    onClickItem = {
                        onSelectedContactChange(it.destination)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            },
            second = {
                selectedContact?.let { contact ->
                    LaunchedEffect(contact.id) {
                        Log.d(TAG, "HomeWithConversationScreen: ${messages.size}")
                    }
                    ConversationComponent(
                        modifier = Modifier.fillMaxSize(),
                        messages = messages,
                        currentUser = currentUser,
                        contact = contact,
                        onSend = onSend,
                    )
                }
            },
            strategy = HorizontalTwoPaneStrategy(
                splitFraction = .5f,
                gapWidth = 16.dp,
            ),
            displayFeatures = displayFeatures,
            modifier = m,
        )
    }
}

private const val TAG = "HomeWithConversationScreen"
