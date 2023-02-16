package fr.messager.popmes.presentation.components.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.buttons.ValidationButton
import fr.messager.popmes.presentation.components.text.InputDescriptionTextField

@Composable
fun AddOrEditUserComponent(
    modifier: Modifier = Modifier,
    user: User,
    onUserChange: (User) -> Unit,
    onAdd: (User) -> Unit,
) {
    val painter = rememberVectorPainter(Icons.Filled.AccountBox)
    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileImage(
                    painter = painter,
                    description = "",
                    modifier = Modifier.size(128.dp)
                )

                InputUserName(
                    firstName = user.firstName,
                    onFirstNameChange = {
                        onUserChange(
                            user.copy(firstName = it)
                        )
                    },
                    lastName = user.lastName,
                    onLastNameChange = {
                        onUserChange(
                            user.copy(lastName = it)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                InputPhoneNumber(
                    phoneNumber = user.phoneNumber,
                    onPhoneNumberChange = {
                        onUserChange(
                            user.copy(phoneNumber = it)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                InputDescriptionTextField(
                    description = user.description,
                    onDescriptionChange = {
                        onUserChange(
                            user.copy(description = it)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp),
                )
            }

            ValidationButton(
                onClick = { onAdd(user) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditUserComponent(
    modifier: Modifier = Modifier,
    user: User,
    onUserChange: (User) -> Unit,
    onAdd: (User) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back add user",
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.title_new_contact))
                },
            )
        }
    ) { paddingValues ->
        AddOrEditUserComponent(
            onAdd = {
                onAdd(it)
                onBack()
            },
            modifier = modifier.padding(paddingValues),
            user = user,
            onUserChange = onUserChange,
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun AddUserComponentPreview() {
    var user by remember {
        mutableStateOf(User.unspecified)
    }
    AddOrEditUserComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onAdd = {},
        user = user,
        onUserChange = { user = it }
    )
}