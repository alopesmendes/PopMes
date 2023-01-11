package fr.messager.popmes.presentation.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerContent(
    navItems: List<NavItem>,
    onNavigate: (String) -> Unit,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onDrawerStateChange: () -> Unit,
) {

    ModalDrawerSheet {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleSmall,
            )
            IconButton(onClick = onDrawerStateChange) {
                Icon(
                    painterResource(id = R.drawable.ic_menu_open),
                    contentDescription = "drawer menu"
                )
            }
        }

        navItems.forEachIndexed { index, navItem ->
            NavigationDrawerItem(
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
                label = {
                    Text(
                        text = stringResource(id = navItem.titleId),
                        color = if (selectedItem == index) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawerNavigation(
    navItems: List<NavItem>,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit,
    onDrawerStateChange: () -> Unit,
) {

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                navItems = navItems,
                onNavigate = onNavigate,
                onDrawerStateChange = onDrawerStateChange,
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
            )
        },
        modifier = modifier,
        content = content,
        drawerState = drawerState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ModalNavigationDrawerPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember {
        mutableStateOf(-1)
    }
    ModalDrawerNavigation(
        navItems = listOf(
            NavItem.Home,
            NavItem.Contacts,
            NavItem.Tasks,
        ),
        onNavigate = {},
        content = {
            Text(text = "drawer navigation")
        },
        drawerState = drawerState,
        onDrawerStateChange = {
            scope.launch {
                if (drawerState.isClosed)
                    drawerState.close()
                else
                    drawerState.open()
            }
        },
        selectedItem = selectedItem,
        onSelectedItemChange = { selectedItem = it },
    )
}