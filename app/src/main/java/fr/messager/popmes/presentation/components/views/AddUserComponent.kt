package fr.messager.popmes.presentation.components.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import fr.messager.popmes.presentation.components.image.ProfileImage
import fr.messager.popmes.presentation.components.text.InputPhoneNumber
import fr.messager.popmes.presentation.components.text.InputUserName
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserComponent(
    modifier: Modifier = Modifier,
    onAdd: (User) -> Unit,
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

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
                    firstName = firstName,
                    onFirstNameChange = { firstName = it },
                    lastName = lastName,
                    onLastNameChange = { lastName = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                InputPhoneNumber(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { phoneNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(text = "description (optional)")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TextFieldDefaults.MinHeight.times(3))
                        .padding(start = 40.dp)
                )
            }

            ValidationButton(
                onClick = {
                    onAdd(
                        User(
                            id = "${UUID.randomUUID()}",
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserComponent(
    modifier: Modifier = Modifier,
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
    ) {
        AddUserComponent(
            onAdd = onAdd,
            modifier = modifier.padding(it),
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun AddUserComponentPreview() {
    AddUserComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onAdd = {},
    )
}