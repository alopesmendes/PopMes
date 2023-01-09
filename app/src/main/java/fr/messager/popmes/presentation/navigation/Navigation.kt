package fr.messager.popmes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: Screen = Screen.Home,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route(),
        modifier = modifier,
    ) {
        composable(
            route = Screen.Home.route(),
        ) {
            // TODO add screen home
        }

        composable(
            route = Screen.Conversation.route(),
            arguments = Screen.Conversation.navParams(),
        ) {
            // TODO add screen group chat
        }
    }
}