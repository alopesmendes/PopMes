package fr.messager.popmes.presentation.components.views

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.buttons.ValidationButton
import fr.messager.popmes.presentation.components.image.ProfileImage
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.list.PopMesListRow
import fr.messager.popmes.presentation.components.list.items.ContactItem
import fr.messager.popmes.presentation.components.list.items.SmallContactItem
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddGroupComponent(
    modifier: Modifier = Modifier,
    contacts: List<User>,
    contactsAdded: List<User>,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    onAdd: (Group) -> Unit,
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileImage(
                painter = painterResource(id = R.drawable.avatar_0),
                description = "",
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = {
                    Text(text = stringResource(id = R.string.group_name))
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.number_users, contactsAdded.size),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.outline,
            )

            PopMesListRow(
                values = contactsAdded,
                modifier = Modifier.fillMaxWidth(),
                key = { contactsAdded[it].id },
            ) { index, value ->
                SmallContactItem(
                    name = value.shortName(),
                    description = "",
                    icon = painterResource(id = R.drawable.avatar_0),
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing,
                        )
                    )
                ) { toRemoveContact(index) }
            }
        }

        PopMesListColumn(
            values = contacts,
            key = { contacts[it].id },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) { _, value ->
            ContactItem(
                name = value.fullName(),
                description = value.description,
                icon = painterResource(id = R.drawable.avatar_0),
                modifier = Modifier
                    .animateItemPlacement()
            ) { toAddContact(value) }
        }

        ValidationButton(
            onClick = {
                onAdd(
                    Group(
                        id = "${UUID.randomUUID()}",
                        name = name,
                        users = contactsAdded,
                        description = contactsAdded.joinToString(",") { it.shortName() },
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupComponent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    contacts: List<User>,
    toAddContact: (User) -> Unit,
    toRemoveContact: (Int) -> Unit,
    onAdd: (Group) -> Unit,
    contactsAdded: List<User>,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_new_group))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        BackHandler(onBack = onBack)

        AddGroupComponent(
            contacts = contacts,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onAdd = {
                onAdd(it)
                onBack()
            },
            toAddContact = toAddContact,
            toRemoveContact = toRemoveContact,
            contactsAdded = contactsAdded,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddGroupComponentPreview() {
    val contactsAdded = remember {
        mutableStateListOf<User>()
    }
    AddGroupComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contacts = listOf(
            User(
                id = "0",
                firstName = "Ailton",
                lastName = "Lopes Mendes",
                phoneNumber = "0781831024",
                description = "",
            ),
            User(
                id = "1",
                firstName = "Manuel",
                lastName = "Lopes Mendes",
                phoneNumber = "0681831024",
                description = "",
            ),
            User(
                id = "2",
                firstName = "Jailsa",
                lastName = "Lopes Mendes",
                phoneNumber = "0581831024",
                description = "",
            ),
            User(
                id = "3",
                firstName = "Domingas Varela",
                lastName = "Lopes Mendes",
                phoneNumber = "0581831024",
                description = "",
            )
        ),
        contactsAdded = contactsAdded,
        toRemoveContact = { contactsAdded.removeAt(it) },
        toAddContact = { contactsAdded.add(it) },
        onAdd = {},
    )
}