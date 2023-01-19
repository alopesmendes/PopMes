package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.components.views.ConversationComponent
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeWithConversationScreen(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    messages: List<Message>,
    currentUser: User,
    selectedContact: Contact?,
    onSelectedContactChange: (Contact?) -> Unit,
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
                        supportingText = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                        onClick = {
                            onSelectedContactChange(value.to)
                        },
                        shortName = value.from.firstName,
                    )
                }
            },
            second = {
                selectedContact?.let { contact ->
                    ConversationComponent(
                        modifier = Modifier.fillMaxSize(),
                        messages = messages
                            .filter { contact.id == it.to.id }
                            .sortedBy { it.date },
                        currentUser = currentUser,
                        contact = contact,
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
