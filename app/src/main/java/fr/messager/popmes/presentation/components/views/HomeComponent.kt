package fr.messager.popmes.presentation.components.views

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams

private const val TAG = "HomeComponent"

@Composable
fun HomeComponent(
    modifier: Modifier,
    messages: List<Message>,
    onNavigate: (String) -> Unit,
) {
    val user = painterResource(id = R.drawable.avatar_0)
    CardList(
        modifier = modifier,
        values = messages
            .sortedByDescending { it.date }
            .distinctBy { it.to.id },
    ) { _, value ->
        DetailedMessageCard(
            icon = user,
            fullName = value.to.fullName(),
            date = value.date,
            supportingText = "${value.from.firstName}: One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
            onClick = {
                val conversationParams = ConversationParams(
                    messages = messages
                        .filter { it.to.id == value.to.id }
                        .sortedBy { it.date },
                    contactId = value.to.id,
                )
                Log.d(TAG, "HomeComponent: ${conversationParams.messages.size}")
                onNavigate(
                    Screen.Conversation.navigate(params = listOf(conversationParams.toHex()))
                )
            }
        )
    }
}