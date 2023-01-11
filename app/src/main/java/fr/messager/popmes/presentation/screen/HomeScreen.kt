package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import kotlinx.coroutines.CoroutineScope

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
        // TODO create home screen
        Text(text = "Home screen")
    }
}