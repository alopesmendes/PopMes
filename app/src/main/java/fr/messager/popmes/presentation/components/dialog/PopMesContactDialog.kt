package fr.messager.popmes.presentation.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.presentation.components.buttons.PopMesTextButton

@Composable
fun PopMesContactDialog(
    modifier: Modifier = Modifier,
    openDialog: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onMessage: () -> Unit,
    contact: Contact,
) {
    PopMesAlertDialog(
        openDialog = openDialog,
        onOpenDialogChange = onOpenDialogChange,
        modifier = modifier,
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Image(
                        painterResource(id = R.drawable.avatar_0),
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = contact.fullName(),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = contact.description,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    PopMesTextButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "edit contact"
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(id = R.string.edit))
                    }

                    PopMesTextButton(
                        onClick = onMessage,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_message),
                            contentDescription = "start conversation"
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = stringResource(id = R.string.message))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopMesContactDialogPreview() {
    var openDialog by remember {
        mutableStateOf(false)
    }

    Button(onClick = { openDialog = true }) {
        Text(text = "Open Contact Dialog")
    }

    PopMesContactDialog(
        openDialog = openDialog,
        onOpenDialogChange = { openDialog = it },
        onEdit = {  },
        onMessage = {  },
        contact = User(
            id = "0",
            firstName = "Ailton",
            lastName = "Lopes Mendes",
            description = "On est la on est la",
            phoneNumber = "0781831024",
        ),
        modifier = Modifier.fillMaxWidth()
    )
}