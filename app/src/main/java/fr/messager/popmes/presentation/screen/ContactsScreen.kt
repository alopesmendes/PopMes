package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.AddGroupComponent
import fr.messager.popmes.presentation.components.views.AddUserComponent
import fr.messager.popmes.presentation.components.views.ContactsComponent
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.screen.two_pane.ContactsWithAddUserAndAddGroupScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    activity: Activity,
    navItems: List<NavItem>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    contacts: List<User>,
    displayFeatures: List<DisplayFeature>,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    toAddUserComponentVisibility: Boolean,
    toAddGroupComponentVisibility: Boolean,
    onToAddUserComponentVisibilityChange: (Boolean) -> Unit,
    onToAddGroupComponentVisibilityChange: (Boolean) -> Unit,
    onAdd: (Contact) -> Unit,
    contactsAdded: List<User>,
) {
    BackHandler(onBack = onBack)

    PopMesWindowSize(
       activity = activity,
       windowSizeDimension = WindowSizeDimension.Width,
       compact = {
           if (toAddUserComponentVisibility) {
                AddUserComponent(
                    onAdd = onAdd,
                    modifier = modifier,
                    onBack = onBack,
                )
           } else if (toAddGroupComponentVisibility) {
                AddGroupComponent(
                    onBack = onBack,
                    contacts = contacts,
                    toAddContact = toAddContact,
                    toRemoveContact = toRemoveContact,
                    onAdd = onAdd,
                    contactsAdded = contactsAdded,
                    modifier = modifier,
                )
           } else {
               ContactsComponent(
                   activity = activity,
                   navItems = navItems,
                   selectedItem = selectedItem,
                   drawerState = drawerState,
                   scope = scope,
                   onSelectedItemChange = onSelectedItemChange,
                   onNavigate = onNavigate,
                   contacts = contacts,
                   onBack = onBack,
                   modifier = modifier,
               )
           }
       },
       medium = {
            ContactsWithAddUserAndAddGroupScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                displayFeatures = displayFeatures,
                contacts = contacts,
                onAdd = onAdd,
                toAddContact = toAddContact,
                toRemoveContact = toRemoveContact,
                contactsAdded = contactsAdded,
                toAddUserComponentVisibility = toAddUserComponentVisibility,
                toAddGroupComponentVisibility = toAddGroupComponentVisibility,
                onToAddUserComponentVisibilityChange = onToAddUserComponentVisibilityChange,
                onToAddGroupComponentVisibilityChange = onToAddGroupComponentVisibilityChange,
            )
       },
   )
}