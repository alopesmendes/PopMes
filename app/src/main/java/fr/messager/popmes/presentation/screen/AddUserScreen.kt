package fr.messager.popmes.presentation.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
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
fun AddUserScreen(
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
    contactsAdded: List<User>,
) {
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
            } else {
                AddUserComponent(
                    onAdd = onAdd,
                    modifier = modifier,
                    onBack = onBack,
                )
            }
        },
        medium = {
            ContactsWithAddUserAndAddGroupScreen(
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
            )
        }
    )
}