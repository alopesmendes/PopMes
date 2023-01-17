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
import com.google.accompanist.adaptive.calculateDisplayFeatures
import fr.messager.popmes.common.Extension.contactsNavigation
import fr.messager.popmes.common.Extension.conversationNavigation
import fr.messager.popmes.common.Extension.navigateTo
import fr.messager.popmes.common.Extension.tasksNavigation
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.screen.HomeScreen
import java.time.Instant


private const val TAG = "NavigationGraph"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    activity: Activity,
    navController: NavHostController,
    modifier: Modifier,
    startDestination: Screen = Screen.Home,
) {
    val displayFeatures = calculateDisplayFeatures(activity)
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

    val ailton by remember {
        derivedStateOf {
            User(
                id = "0",
                firstName = "Ailton",
                lastName = "Lopes Mendes",
                phoneNumber = "+33781831024",
            )
        }
    }

    val manuel by remember {
        derivedStateOf {
            User(
                id = "1",
                firstName = "Manuel",
                lastName = "Lopes Mendes",
                phoneNumber = "+33681831024",
            )
        }
    }

    val jailsa by remember {
        derivedStateOf {
            User(
                id = "2",
                firstName = "Jailsa",
                lastName = "Lopes Mendes",
                phoneNumber = "+33481831024",
            )
        }
    }

    val family by remember {
        derivedStateOf {
            Group(
                id = "3",
                name = "famille",
                users = listOf(ailton, manuel, jailsa)
            )
        }
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
                onNavigate = { navController.navigateTo(it) },
                navItems = navItems,
                drawerState = drawerState,
                selectedItem = selectedItem,
                scope = scope,
                onSelectedItemChange = { selectedItem = it },
                messages = listOf(
                    Message(
                        id = "0",
                        messageType = MessageType.MessageData,
                        from = ailton,
                        to = family,
                        date = Instant.ofEpochMilli(1673531209291L),
                    ),
                    Message(
                        id = "1",
                        messageType = MessageType.MessageData,
                        from = manuel,
                        to = family,
                        date = Instant.now(),
                    ),
                    Message(
                        id = "2",
                        messageType = MessageType.MessageData,
                        from = jailsa,
                        to = ailton,
                        date = Instant.now(),
                    ),
                    Message(
                        id = "3",
                        messageType = MessageType.MessageData,
                        from = jailsa,
                        to = family,
                        date = Instant.now(),
                    )
                ),
                displayFeatures = displayFeatures,
                currentUser = ailton,
                onBack = { navController.navigate(Screen.Home.navigate()) },
            )
        }

        contactsNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = { navController.navigateTo(it) },
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            onSelectedItemChange = { selectedItem = it },
            onBack = navController::popBackStack,
        )

        conversationNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = { navController.navigateTo(it) },
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            currentUser = ailton,
            onSelectedItemChange = { selectedItem = it },
            onBack = navController::popBackStack,
        )

        tasksNavigation(
            activity = activity,
            navItems = navItems,
            onNavigate = { navController.navigateTo(it) },
            drawerState = drawerState,
            selectedItem = selectedItem,
            scope = scope,
            onSelectedItemChange = { selectedItem = it },
            onBack = navController::popBackStack,
        )
    }
}