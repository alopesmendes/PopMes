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
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.contact.AddOrEditGroupComponent
import fr.messager.popmes.presentation.components.contact.AddOrEditUserComponent
import fr.messager.popmes.presentation.components.dimensions.PopMesWindowSize
import fr.messager.popmes.presentation.components.dimensions.WindowSizeDimension
import fr.messager.popmes.presentation.screen.two_pane.ContactsWithAddOrEditUserAndAddGroupScreen

@Composable
fun AddOrEditContactScreen(
    activity: Activity,
    modifier: Modifier,
    displayFeatures: List<DisplayFeature>,
    contacts: List<User>,
    onAddOrEdit: (Contact) -> Unit,
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
) {
    var openDeleteContactDialog by rememberSaveable { mutableStateOf(false) }
    var openInfoContactDialog by rememberSaveable { mutableStateOf(false) }
    BackHandler(onBack = onBack)

    PopMesWindowSize(
        activity = activity,
        windowSizeDimension = WindowSizeDimension.Width,
        compact = {
            if (toAddGroupComponentVisibility) {
                AddOrEditGroupComponent(
                    onBack = onBack,
                    contacts = contacts,
                    onAddOrEdit = onAddOrEdit,
                    onGroupChange = onSelectContactChange,
                    group = if (selectContact is Group) selectContact else Group.unspecified,
                )
            } else if (toAddUserComponentVisibility) {
                AddOrEditUserComponent(
                    onAdd = onAddOrEdit,
                    modifier = modifier,
                    onBack = onBack,
                    user = if (selectContact is User) selectContact else User.unspecified,
                    onUserChange = onSelectContactChange,
                )
            }
        },
        medium = {
            ContactsWithAddOrEditUserAndAddGroupScreen(
                modifier = modifier,
                displayFeatures = displayFeatures,
                contacts = contacts,
                onAdd = onAddOrEdit,
                toAddUserComponentVisibility = toAddUserComponentVisibility,
                toAddGroupComponentVisibility = toAddGroupComponentVisibility,
                onToAddUserComponentVisibilityChange = onToAddUserComponentVisibilityChange,
                onToAddGroupComponentVisibilityChange = onToAddGroupComponentVisibilityChange,
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