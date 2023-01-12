package fr.messager.popmes.presentation.components.list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> CardList(
    modifier: Modifier = Modifier,
    values: List<T>,
    onItemContent: @Composable (index: Int, value: T) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(values.size) { index ->
            onItemContent(index, values[index])
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}