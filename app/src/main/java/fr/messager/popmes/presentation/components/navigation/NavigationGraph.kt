package fr.messager.popmes.presentation.components.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.messager.popmes.common.Extension.contactsNavigation
import fr.messager.popmes.common.Extension.conversationNavigation
import fr.messager.popmes.common.Extension.tasksNavigation
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.screen.HomeScreen


private const val TAG = "NavigationGraph"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    activity: Activity,
    navController: NavHostController,
    modifier: Modifier,
    startDestination: Screen = Screen.Home,
) {
    val navItems by remember {
        derivedStateOf {
            listOf(NavItem.Home, NavItem.Contacts, NavItem.Tasks)
        }
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable {
        mutableStateOf(-1)
    }

    LaunchedEffect(selectedItem) {
        Log.d(TAG, "NavigationGraph: $selectedItem")
    }

    NavHost(
        navController = navController,
        startDestination = startDestination.route(),
        modifier = modifier,
    ) {
        composable(
            route = Screen.Home.route(),
        ) {
            HomeScreen(
                activity = activity,
                onNavigate = navController::navigate,
                navItems = navItems,
                drawerState = drawerState,
                selectedItem = selectedItem,
                scope = scope,
                onSelectedItemChange = { selectedItem = it },
            )
        }

        contactsNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = navController::navigate,
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            onSelectedItemChange = { selectedItem = it },
        )

        conversationNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = navController::navigate,
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            onSelectedItemChange = { selectedItem = it },
        )

        tasksNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = navController::navigate,
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            onSelectedItemChange = { selectedItem = it },
        )
    }
}