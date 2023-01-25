package fr.messager.popmes.presentation.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputUserName(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.Person,
            contentDescription = null,
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            singleLine = true,
            label = { Text(text = "first name") },
            modifier = Modifier.weight(1f),
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text(text = "last name") },
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun InputUserNamePreview() {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }

    InputUserName(
        firstName = firstName,
        onFirstNameChange = { firstName = it },
        lastName = lastName,
        onLastNameChange = { lastName = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}