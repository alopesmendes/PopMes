package fr.messager.popmes.presentation.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.messager.popmes.presentation.components.text.transformation.PhoneNumberVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPhoneNumber(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.Phone,
            contentDescription = null,
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = {
                Text(text = "phone number")
            },
//            trailingIcon = {
//            },
            singleLine = true,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            visualTransformation = PhoneNumberVisualTransformation(),
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun InputPhoneNumberPreview() {
    var phoneNumber by remember {
        mutableStateOf("")
    }
    InputPhoneNumber(
        phoneNumber = phoneNumber,
        onPhoneNumberChange = { phoneNumber = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
}