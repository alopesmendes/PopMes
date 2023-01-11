package fr.messager.popmes.presentation.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import fr.messager.popmes.presentation.navigation.NavItem

@Composable
fun BottomNavigationBar(
    navItems: List<NavItem>,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
) {
    NavigationBar(
        modifier = modifier
    ) {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
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
                label = {
                    Text(
                        text = stringResource(id = navItem.titleId),
                        color = if (selectedItem == index)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    onSelectedItemChange(index)
                    onNavigate(navItem.screen.navigate())
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    var selectedItem by remember {
        mutableStateOf(-1)
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navItems = listOf(
                    NavItem.Home,
                    NavItem.Contacts,
                    NavItem.Tasks,
                ),
                onNavigate = {},
                selectedItem = selectedItem,
                onSelectedItemChange = { selectedItem = it },
            )
        },
        content = {
            Text(
                text = "Bottom navigation bar",
                modifier = Modifier.padding(it),
            )
        }
    )

}