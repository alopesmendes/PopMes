package fr.messager.popmes.presentation.components.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.buttons.PopMesTextButton
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.list.items.ContactItem
import fr.messager.popmes.presentation.components.navigation.Navigation
import fr.messager.popmes.presentation.navigation.NavItem
import fr.messager.popmes.presentation.navigation.Screen
import fr.messager.popmes.presentation.navigation.arguments.ContactsParams
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsComponent(
    modifier: Modifier = Modifier,
    onAddNewUser: () -> Unit,
    onAddNewGroup: () -> Unit,
    onDeleteContact: (String) -> Unit,
    onClickItem: (Contact) -> Unit,
    contacts: List<User>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PopMesTextButton(
            onClick = onAddNewUser,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_person_add),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.title_new_contact))
        }

        PopMesTextButton(
            onClick = onAddNewGroup,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_group_add),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.title_new_group))
        }

        Text(
            text = stringResource(id = R.string.contacts_on_pop_mes),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )

        PopMesListColumn(
            values = contacts,
            key = { contacts[it].id },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) { _, value ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing,
                        )
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ContactItem(
                    name = value.fullName(),
                    // TODO add description to user
                    description = "From here",
                    icon = painterResource(id = R.drawable.avatar_0),
                ) {
                    onClickItem(value)
                }

                IconButton(onClick = { onDeleteContact(value.id) }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete contact",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsComponent(
    modifier: Modifier = Modifier,
    activity: Activity,
    navItems: List<NavItem>,
    selectedItem: Int,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSelectedItemChange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    onDeleteContact: (String) -> Unit,
    onClickItem: (Contact) -> Unit,
    contacts: List<User>,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_contacts))
                },
            )
        }
    ) { paddingValues ->
        Navigation(
            activity = activity,
            navItems = navItems,
            selectedItem = selectedItem,
            drawerState = drawerState,
            scope = scope,
            onSelectedItemChange = onSelectedItemChange,
            onNavigate = onNavigate,
            modifier = modifier.padding(paddingValues)
        ) {
            BackHandler(onBack = onBack)

            ContactsComponent(
                modifier = it,
                onAddNewUser = {
                    val contactsParams = ContactsParams(
                        users = contacts,
                        toAddGroupComponentVisibility = false,
                        toAddUserComponentVisibility = true,
                    )
                    onNavigate(
                        Screen.AddUser.navigate(
                            contactsParams.toHex()
                        )
                    )
                },
                onAddNewGroup = {
                    val contactsParams = ContactsParams(
                        users = contacts,
                        toAddGroupComponentVisibility = true,
                        toAddUserComponentVisibility = false,
                    )
                    onNavigate(
                        Screen.AddGroup.navigate(
                            contactsParams.toHex(),
                        )
                    )
                },
                contacts = contacts,
                onDeleteContact = onDeleteContact,
                onClickItem = onClickItem,
            )
        }
    }

}