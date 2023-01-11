package fr.messager.popmes.presentation.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.messager.popmes.presentation.navigation.NavItem

@Composable
fun RailNavigation(
    navItems: List<NavItem>,
    modifier: Modifier = Modifier,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onDrawerStateChange: () -> Unit,
    content: @Composable () -> Unit,
) {

    Row(modifier = modifier) {
        AnimatedVisibility(visible = true) {
            NavigationRail(
                header = {
                    IconButton(onClick = onDrawerStateChange) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                        )
                    }
                }
            ) {
                Spacer(modifier = Modifier.weight(1f))
                navItems.forEachIndexed { index, navItem ->
                    NavigationRailItem(
                        selected = selectedItem == index,
                        onClick = {
                            onSelectedItemChange(index)
                            onNavigate(navItem.screen.navigate())
                        },
                        icon = {
                            Icon(
                                navItem.painterIcon(),
                                contentDescription = navItem.contentDescription,
                                tint = if (selectedItem == index)
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun RailNavigationPreview() {
    var selectedItem by remember {
        mutableStateOf(-1)
    }
    RailNavigation(
        navItems = listOf(
            NavItem.Home,
            NavItem.Contacts,
            NavItem.Tasks,
        ),
        onNavigate = {},
        content = { Text(text = "rail navigation") },
        onDrawerStateChange = {},
        selectedItem = selectedItem,
        onSelectedItemChange = { selectedItem = it },
    )
}