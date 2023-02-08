package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.AddGroupComponent
import fr.messager.popmes.presentation.components.views.AddOrEditUserComponent
import fr.messager.popmes.presentation.screen.two_pane.ContactsWithAddOrEditUserAndAddGroupScreen

@Composable
fun AddOrEditUserScreen(
    activity: Activity,
    modifier: Modifier,
    displayFeatures: List<DisplayFeature>,
    contacts: List<User>,
    onAdd: (Contact) -> Unit,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    toAddUserComponentVisibility: Boolean,
    toAddGroupComponentVisibility: Boolean,
    onToAddUserComponentVisibilityChange: (Boolean) -> Unit,
    onToAddGroupComponentVisibilityChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onDeleteContact: (String) -> Unit,
    selectContact: Contact?,
    onSelectContactChange: (Contact) -> Unit,
    onMessageContact: (Contact) -> Unit,
    onEditContact: (Contact) -> Unit,
    contactsAdded: List<User>,
) {
    var openDeleteContactDialog by rememberSaveable { mutableStateOf(false) }
    var openInfoContactDialog by rememberSaveable { mutableStateOf(false) }
    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            if (toAddGroupComponentVisibility) {
                AddGroupComponent(
                    onBack = onBack,
                    contacts = contacts,
                    toAddContact = toAddContact,
                    toRemoveContact = toRemoveContact,
                    onAdd = onAdd,
                    contactsAdded = contactsAdded,
                    modifier = modifier,
                )
            } else if (toAddUserComponentVisibility) {
                AddOrEditUserComponent(
                    onAdd = onAdd,
                    modifier = modifier,
                    onBack = onBack,
                    user = selectContact as User,
                    onUserChange = onSelectContactChange,
                )
            }
        },
        medium = {
            ContactsWithAddOrEditUserAndAddGroupScreen(
                displayFeatures = displayFeatures,
                contacts = contacts,
                onAdd = onAdd,
                toAddContact = toAddContact,
                toRemoveContact = toRemoveContact,
                toAddUserComponentVisibility = toAddUserComponentVisibility,
                toAddGroupComponentVisibility = toAddGroupComponentVisibility,
                onToAddUserComponentVisibilityChange = onToAddUserComponentVisibilityChange,
                onToAddGroupComponentVisibilityChange = onToAddGroupComponentVisibilityChange,
                contactsAdded = contactsAdded,
                modifier = modifier,
                onDeleteContact = onDeleteContact,
                openDeleteContactDialog = openDeleteContactDialog,
                onOpenDeleteContactDialogChange = { openDeleteContactDialog = it },
                selectContact = selectContact,
                onSelectContactChange = onSelectContactChange,
                openInfoContactDialog = openInfoContactDialog,
                onOpenInfoContactDialogChange = { openInfoContactDialog = it },
                onMessageContact = onMessageContact,
                onEditContact = onEditContact,
            )
        }
    )
}