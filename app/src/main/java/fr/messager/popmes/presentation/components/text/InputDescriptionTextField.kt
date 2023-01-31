package fr.messager.popmes.presentation.components.text

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDescriptionTextField(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        label = {
            Text(text = "description (optional)")
        },
        modifier = modifier
            .height(TextFieldDefaults.MinHeight.times(3))

    )
}