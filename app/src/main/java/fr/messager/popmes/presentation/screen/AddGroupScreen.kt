package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.components.views.AddGroupComponent
import fr.messager.popmes.presentation.components.views.AddUserComponent
import fr.messager.popmes.presentation.screen.two_pane.ContactsWithAddUserAndAddGroupScreen

@Composable
fun AddGroupScreen(
    modifier: Modifier = Modifier,
    activity: Activity,
    displayFeatures: List<DisplayFeature>,
    contacts: SnapshotStateList<User>,
    onAdd: (Contact) -> Unit,
    onBack: () -> Unit,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    toAddUserComponentVisibility: Boolean,
    toAddGroupComponentVisibility: Boolean,
    onToAddUserComponentVisibilityChange: (Boolean) -> Unit,
    onToAddGroupComponentVisibilityChange: (Boolean) -> Unit,
    onDeleteContact: (String) -> Unit,
    onClickItem: (Contact) -> Unit,
    selectContact: Contact?,
    onSelectContactChange: (Contact) -> Unit,
    contactsAdded: List<User>,
) {
    var openDeleteContactDialog by rememberSaveable {
        mutableStateOf(false)
    }
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
            } else {
                AddGroupComponent(
                    onBack = onBack,
                    contacts = contacts,
                    toAddContact = toAddContact,
                    toRemoveContact = toRemoveContact,
                    onAdd = onAdd,
                    contactsAdded = contactsAdded,
                    modifier = modifier,
                )
            }
        },
        medium = {
            ContactsWithAddUserAndAddGroupScreen(
                modifier = modifier,
                displayFeatures = displayFeatures,
                contacts = contacts,
                onAdd = onAdd,
                toAddContact = toAddContact,
                toRemoveContact = toRemoveContact,
                contactsAdded = contactsAdded,
                toAddGroupComponentVisibility = toAddGroupComponentVisibility,
                toAddUserComponentVisibility = toAddUserComponentVisibility,
                onToAddGroupComponentVisibilityChange = onToAddGroupComponentVisibilityChange,
                onToAddUserComponentVisibilityChange = onToAddUserComponentVisibilityChange,
                onDeleteContact = onDeleteContact,
                onClickItem = onClickItem,
                openDeleteContactDialog = openDeleteContactDialog,
                onOpenDeleteContactDialogChange = { openDeleteContactDialog = it },
                selectContact = selectContact,
                onSelectContactChange = onSelectContactChange,
            )
        },
    )
}