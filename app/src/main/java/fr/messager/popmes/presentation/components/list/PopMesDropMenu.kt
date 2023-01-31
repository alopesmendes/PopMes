package fr.messager.popmes.presentation.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.messager.popmes.presentation.components.text.PopMesOutlinedTextField

@Composable
fun <T> PopMesDropMenu(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onDisplay: @Composable (T) -> String,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    onSelectedIndexChange: (Int) -> Unit,
    leadingIcon: @Composable ((Int) -> Unit)? = null,
    trailingIcon: @Composable ((Int) -> Unit)? = null,
    values: List<T>,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    )  {
        PopMesOutlinedTextField(
            value = if (selectedIndex in values.indices)
                onDisplay(values[selectedIndex])
            else
                "",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                expanded = true
            },
            singleLine = true,
            placeholder = placeholder,
            label = label,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "drop menu",
                    )
                }
            },
            leadingIcon = if (leadingIcon == null || selectedIndex !in values.indices)
                null
            else {
                { leadingIcon(selectedIndex) }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(IntrinsicSize.Max),
        ) {
            values.forEachIndexed { index, t ->
                DropdownMenuItem(
                    text = { Text(text = onDisplay(t)) },
                    onClick = {
                        onSelectedIndexChange(index)
                        expanded = false
                    },
                    leadingIcon = if (leadingIcon == null)
                        null
                    else {
                        { leadingIcon(index) }
                    },
                    trailingIcon = if (trailingIcon == null)
                        null
                    else {
                        { trailingIcon(index) }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PopMesDropMenuPreview() {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    val values = listOf(
        "Ailton",
        "Jailsa",
        "Manuel",
        "Carlos",
    )
    PopMesDropMenu(
        selectedIndex = selectedIndex,
        onDisplay = { it },
        onSelectedIndexChange = { selectedIndex = it },
        values = values,
        modifier = Modifier.fillMaxWidth(),
    )
}