package fr.messager.popmes.presentation.screen.two_pane

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.contact.AddGroupComponent
import fr.messager.popmes.presentation.components.contact.AddOrEditUserComponent
import fr.messager.popmes.presentation.components.contact.ContactsComponent

@Composable
fun ContactsWithAddOrEditUserAndAddGroupScreen(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    contacts: List<User>,
    onAdd: (Contact) -> Unit,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    toAddUserComponentVisibility: Boolean,
    toAddGroupComponentVisibility: Boolean,
    onToAddUserComponentVisibilityChange: (Boolean) -> Unit,
    onToAddGroupComponentVisibilityChange: (Boolean) -> Unit,
    onDeleteContact: (String) -> Unit,
    openDeleteContactDialog: Boolean,
    onOpenDeleteContactDialogChange: (Boolean) -> Unit,
    selectContact: Contact?,
    onSelectContactChange: (Contact) -> Unit,
    openInfoContactDialog: Boolean,
    onOpenInfoContactDialogChange: (Boolean) -> Unit,
    onMessageContact: (Contact) -> Unit,
    onEditContact: (Contact) -> Unit,
    contactsAdded: List<User>,
) {
    TwoPane(
        first = {
            ContactsComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                onAddNewUser = {
                    onToAddGroupComponentVisibilityChange(false)
                    onToAddUserComponentVisibilityChange(true)
                },
                onAddNewGroup = {
                    onToAddUserComponentVisibilityChange(false)
                    onToAddGroupComponentVisibilityChange(true)
                },
                contacts = contacts,
                onDeleteContact = onDeleteContact,
                onOpenDeleteContactDialogChange = onOpenDeleteContactDialogChange,
                openDeleteContactDialog = openDeleteContactDialog,
                selectContact = selectContact,
                onSelectContactChange = onSelectContactChange,
                openInfoContactDialog = openInfoContactDialog,
                onOpenInfoContactDialogChange = onOpenInfoContactDialogChange,
                onEditContact = onEditContact,
                onMessageContact = onMessageContact,
            )
        },
        second = {
            if (toAddUserComponentVisibility) {
                AddOrEditUserComponent(
                    modifier = Modifier.fillMaxSize(),
                    onAdd = {
                        onAdd(it)
                        onToAddUserComponentVisibilityChange(false)
                    },
                    user = selectContact as User,
                    onUserChange = onSelectContactChange,
                )
            }
            if (toAddGroupComponentVisibility) {
                AddGroupComponent(
                    contacts = contacts,
                    contactsAdded = contactsAdded,
                    toAddContact = toAddContact,
                    toRemoveContact = toRemoveContact,
                    onAdd = {
                        onAdd(it)
                        onToAddGroupComponentVisibilityChange(false)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }

        },
        strategy = HorizontalTwoPaneStrategy(
            splitFraction = .5f,
            gapWidth = 16.dp,
        ),
        displayFeatures = displayFeatures,
        modifier = modifier,
        foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
    )
}