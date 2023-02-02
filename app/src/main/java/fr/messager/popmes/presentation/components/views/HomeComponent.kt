package fr.messager.popmes.presentation.components.views

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams
import kotlinx.coroutines.CoroutineScope

private const val TAG = "HomeComponent"

@Composable
fun HomeComponent(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    onClickItem: (Message) -> Unit,
) {
    val user = painterResource(id = R.drawable.avatar_0)
    PopMesListColumn(
        modifier = modifier,
        values = messages,
    ) { _, value ->
        DetailedMessageCard(
            icon = user,
            fullName = value.to.fullName(),
            shortName = value.from.firstName,
            date = value.date,
            messageType = value.messageType,
            onClick = { onClickItem(value) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComponent(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    activity: Activity,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    navItems: List<NavItem>,
    onNavigate: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_home))
                }
            )
        }
    ) { paddingValues ->
        Navigation(
            activity = activity,
            navItems = navItems,
            selectedItem = selectedItem,
            drawerState = drawerState,
            scope = scope,
            onSelectedItemChange = onSelectedItemChange,
            onNavigate = onNavigate,
            modifier = modifier
                .padding(paddingValues),
        ) { m ->
            HomeComponent(
                messages = messages,
                onClickItem = {
                    val conversationParams = ConversationParams(
                        messages = messages,
                        contact = it.to,
                    )
                    Log.d(TAG, "HomeComponent: ${conversationParams.messages.size}")
                    onNavigate(
                        Screen.Conversation.navigate(conversationParams.toHex())
                    )
                },
                modifier = m,
            )
        }
    }

}