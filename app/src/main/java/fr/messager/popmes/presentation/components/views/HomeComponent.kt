package fr.messager.popmes.presentation.components.views

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams
import kotlinx.coroutines.CoroutineScope

private const val TAG = "HomeComponent"

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
        val user = painterResource(id = R.drawable.avatar_0)
        CardList(
            modifier = m,
            values = messages
                .sortedByDescending { it.date }
                .distinctBy { it.to.id },
        ) { _, value ->
            DetailedMessageCard(
                icon = user,
                fullName = value.to.fullName(),
                shortName = value.from.firstName,
                date = value.date,
                supportingText = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                onClick = {
                    val conversationParams = ConversationParams(
                        messages = messages
                            .sortedBy { it.date },
                        contact = value.to,
                    )
                    Log.d(TAG, "HomeComponent: ${conversationParams.messages.size}")
                    onNavigate(
                        Screen.Conversation.navigate(params = listOf(conversationParams.toHex()))
                    )
                }
            )
        }
    }
}