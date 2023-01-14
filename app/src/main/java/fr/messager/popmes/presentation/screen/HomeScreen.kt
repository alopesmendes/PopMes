package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.presentation.components.card.DetailedMessageCard
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.list.CardList
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import java.time.Instant

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
    conversation: List<String>,
) {
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
        val date = Instant.now() //Instant.ofEpochMilli(1673531209291L)
        val user = painterResource(id = R.drawable.avatar_0)
        PopMesWindowSize(
            activity = activity,
            windowSizeDimension = WindowSizeDimension.Width,
            compact = {
                CardList(
                    modifier = it.padding(horizontal = 16.dp),
                    values = conversation,
                ) { index, value ->
                    DetailedMessageCard(
                        icon = user,
                        fullName = "Ailton Lopes Mendes",
                        date = date,
                        supportingText = value,
                        onClick = {
                            onNavigate(
                                Screen.Conversation.navigate()
                            )
                        }
                    )
                }
            },
            medium = {
                Row(modifier = it) {
                    CardList(
                        values = conversation,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f)
                    ) { index, value ->
                        DetailedMessageCard(
                            icon = user,
                            fullName = "Ailton Lopes Mendes",
                            date = date,
                            supportingText = value,
                            onClick = {

                            }
                        )
                    }
                    Text(text = "Conversation", modifier = Modifier.weight(1f))
                }
            },
        )
    }
}