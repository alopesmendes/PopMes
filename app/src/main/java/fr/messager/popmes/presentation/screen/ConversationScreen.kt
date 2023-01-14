package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.presentation.components.card.MessageCard
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.components.text.InputFieldText
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    messages: List<Message>,
    currentUser: User,
) {
    var value by rememberSaveable {
        mutableStateOf("")
    }

    Navigation(
        activity = activity,
        navItems = navItems,
        onNavigate = onNavigate,
        drawerState = drawerState,
        scope = scope,
        selectedItem = selectedItem,
        onSelectedItemChange = onSelectedItemChange,
    ) { modifier ->
        BoxWithConstraints(
            modifier = modifier,
        ) {
            CardList(
                values = messages,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) { index, value ->
                val alignment by remember(currentUser.id, value.from.id) {
                    derivedStateOf {
                        if (currentUser.id == value.from.id)
                            Alignment.End
                        else
                            Alignment.Start
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    MessageCard(
                        icon = painterResource(id = R.drawable.avatar_0),
                        fullName = "${value.from.firstName} ${value.from.lastName}",
                        date = value.date,
                        text = "One Piece est une série de mangas shōnen créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en Tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022",
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .align(alignment),
                        colors = CardDefaults.cardColors(
                            containerColor = if (currentUser.id == value.from.id)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.surface,
                        ),
                    )
                }
            }

            InputFieldText(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .zIndex(2f),
                fieldModifier = Modifier.background(MaterialTheme.colorScheme.background),
                onSend = {},
            )
        }

    }
}