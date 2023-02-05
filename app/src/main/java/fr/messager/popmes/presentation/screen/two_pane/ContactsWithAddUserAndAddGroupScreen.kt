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
import fr.messager.popmes.presentation.components.views.AddGroupComponent
import fr.messager.popmes.presentation.components.views.AddUserComponent
import fr.messager.popmes.presentation.components.views.ContactsComponent

@Composable
fun ContactsWithAddUserAndAddGroupScreen(
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
    onClickItem: (Contact) -> Unit,
    openDeleteContactDialog: Boolean,
    onOpenDeleteContactDialogChange: (Boolean) -> Unit,
    selectContact: Contact?,
    onSelectContactChange: (Contact) -> Unit,
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
                onClickItem = onClickItem,
                onOpenDeleteContactDialogChange = onOpenDeleteContactDialogChange,
                openDeleteContactDialog = openDeleteContactDialog,
                selectContact = selectContact,
                onSelectContactChange = onSelectContactChange,
            )
        },
        second = {
            if (toAddUserComponentVisibility) {
                AddUserComponent(
                    modifier = Modifier.fillMaxSize(),
                    onAdd = {
                        onAdd(it)
                        onToAddUserComponentVisibilityChange(false)
                    },
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