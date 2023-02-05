package fr.messager.popmes.presentation.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import fr.messager.popmes.R
import fr.messager.popmes.presentation.components.buttons.PopMesTextButton

@Composable
fun PopMesAlertDialog(
    openDialog: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable() (() -> Unit)? = null,
    title: @Composable() (() -> Unit)? = null,
    text: @Composable() (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onOpenDialogChange(false) },
            confirmButton = {
                PopMesTextButton(
                    onClick = {
                        onOpenDialogChange(false)
                        onConfirm()
                    },
                    rowModifier = Modifier,
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            icon = icon,
            title = title,
            text = text,
            shape = shape,
            containerColor = containerColor,
            iconContentColor = iconContentColor,
            titleContentColor = titleContentColor,
            textContentColor = textContentColor,
            tonalElevation = tonalElevation,
            properties = properties,
            dismissButton = {
                TextButton(onClick = { onOpenDialogChange(false) }) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
            },
            modifier = modifier,
        )
    }

}