package fr.messager.popmes.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.components.views.ConversationComponent

@Composable
fun HomeWithConversationScreen(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    messages: List<Message>,
    currentUser: User,
    selectedContactId: String?,
    onSelectedContactIdChange: (String) -> Unit,
) {
    val user = painterResource(id = R.drawable.avatar_0)
    TwoPane(
        first = {
            CardList(
                values = messages
                    .sortedByDescending { it.date }
                    .distinctBy { it.to.id },
                modifier = Modifier,
            ) { _, value ->
                DetailedMessageCard(
                    icon = user,
                    fullName = value.to.fullName(),
                    date = value.date,
                    supportingText = "${value.from.firstName}: One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                    onClick = {
                        onSelectedContactIdChange(value.to.id)
                    }
                )
            }
        },
        second = {
            selectedContactId?.let { id ->
                ConversationComponent(
                    modifier = Modifier.fillMaxSize(),
                    messages = messages
                        .filter { id == it.to.id }
                        .sortedBy { it.date },
                    currentUser = currentUser,
                )
            }
        },
        strategy = HorizontalTwoPaneStrategy(
            splitFraction = .5f,
            gapWidth = 16.dp,
        ),
        displayFeatures = displayFeatures,
        modifier = modifier,
    )
}
