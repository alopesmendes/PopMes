package fr.messager.popmes.presentation.components.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    activity: Activity,
    modifier: Modifier = Modifier,
    navItems: List<NavItem>,
    selectedItem: Int,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            Scaffold(
                modifier = modifier,
                bottomBar = {
                    BottomNavigationBar(
                        navItems = navItems,
                        onNavigate = onNavigate,
                        selectedItem = selectedItem,
                        onSelectedItemChange = onSelectedItemChange,
                    )
                }
            ) {
                content(Modifier.fillMaxSize().padding(it))
            }
        },
        medium = {
            ModalDrawerNavigation(
                navItems = navItems,
                onNavigate = onNavigate,
                content = {
                    RailNavigation(
                        navItems = navItems,
                        onNavigate = onNavigate,
                        content = {
                            content(Modifier.fillMaxSize())
                        },
                        onDrawerStateChange = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        selectedItem = selectedItem,
                        onSelectedItemChange = onSelectedItemChange,
                    )
                },
                drawerState = drawerState,
                onDrawerStateChange = {
                    scope.launch { drawerState.close() }
                },
                selectedItem = selectedItem,
                onSelectedItemChange = onSelectedItemChange,
            )

        },
    )
}