package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.components.views.HomeComponent
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
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
    currentUser: User,
    displayFeatures: List<DisplayFeature>,
) {
    var selectedContactId: String? by rememberSaveable {
        mutableStateOf(null)
    }
    Navigation(
        activity = activity,
        navItems = navItems,
        onNavigate = onNavigate,
        onSelectedItemChange = onSelectedItemChange,
        scope = scope,
        selectedItem = selectedItem,
        drawerState = drawerState,
        modifier = modifier,
    ) {
        PopMesWindowSize(
            activity = activity,
            windowSizeDimension = WindowSizeDimension.Width,
            compact = {
                if (selectedContactId != null) {
                    BackHandler(onBack = onBack)

                    ConversationComponent(
                        modifier = it,
                        messages = messages
                            .filter { selectedContactId == it.to.id }
                            .sortedBy { it.date },
                        currentUser = currentUser,
                    )
                } else {
                    HomeComponent(
                        modifier = it,
                        messages = messages,
                        onNavigate = onNavigate,
                    )
                }

            },
            medium = {
                HomeWithConversationScreen(
                    displayFeatures = displayFeatures,
                    messages = messages,
                    currentUser = currentUser,
                    selectedContactId = selectedContactId,
                    onSelectedContactIdChange = { selectedContactId = it },
                )
            },
        )
    }
}