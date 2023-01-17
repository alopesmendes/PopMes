package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
) {
    Navigation(
        activity = activity,
        navItems = navItems,
        onNavigate = onNavigate,
        drawerState = drawerState,
        scope = scope,
        selectedItem = selectedItem,
        onSelectedItemChange = onSelectedItemChange,
    ) {
        BackHandler(onBack = onBack)
        // TODO create tasks screen
        Text(text = "Tasks screen")
    }
}