package fr.messager.popmes.presentation.components.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import fr.messager.popmes.R
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import fr.messager.popmes.presentation.components.contact.ProfileImage
import fr.messager.popmes.presentation.components.list.PopMesListColumn
import fr.messager.popmes.presentation.components.state.rememberTimeAgo
import fr.messager.popmes.presentation.components.text.InputFieldText
import fr.messager.popmes.presentation.components.text.TextAndLabel
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID


@Composable
fun ConversationComponent(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    contact: Contact,
    currentUser: User,
    onSend: (Message) -> Unit,
) {
    var value by rememberSaveable {
        mutableStateOf("")
    }

    val currentUserMessageShape by remember {
        derivedStateOf { RoundedCornerShape(20.dp, 2.dp, 20.dp, 20.dp) }
    }
    val contactMessageShape by remember {
        derivedStateOf { RoundedCornerShape(2.dp, 20.dp, 20.dp, 20.dp) }
    }

    BoxWithConstraints(
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        val lastIndex by remember(messages.lastIndex) {
            derivedStateOf {
                if (messages.lastIndex == -1) 0 else messages.lastIndex
            }
        }
        val state = rememberLazyListState(initialFirstVisibleItemIndex = lastIndex)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(lastIndex) {
            coroutineScope.launch {
                state.animateScrollToItem(index = lastIndex)
            }
        }

        PopMesListColumn(
            values = messages,
            modifier = Modifier.fillMaxWidth(),
            state = state,
        ) { _, value ->
            val alignment by remember(currentUser.id, value.from.id) {
                derivedStateOf {
                    if (currentUser.id == value.from.id)
                        Alignment.End
                    else
                        Alignment.Start
                }
            }

            val shape by remember(currentUser.id, value.from.id) {
                derivedStateOf {
                    if (currentUser.id == value.from.id)
                        currentUserMessageShape
                    else
                        contactMessageShape
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                MessageCard(
                    icon = painterResource(id = R.drawable.avatar_0),
                    fullName = value.from.fullName(),
                    date = value.date,
                    messageType = value.messageType,
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .align(alignment),
                    shape = shape,
                    colors = if (currentUser.id == value.from.id)
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        )
                    else
                        CardDefaults.cardColors()
                )
            }
        }

        InputFieldText(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(2f),
            fieldModifier = Modifier.background(MaterialTheme.colorScheme.background),
            onSend = {
                onSend(
                    Message(
                        id = "${UUID.randomUUID()}",
                        date = Instant.now(),
                        from = currentUser,
                        to = contact,
                        messageType = MessageType.MessageData(value),
                        destination = contact,
                    )
                )
                value = ""
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationComponent(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    contact: Contact,
    onSend: (Message) -> Unit,
    currentUser: User,
    onBack: () -> Unit,
) {
    val timeAgo = rememberTimeAgo(date = messages.lastOrNull()?.date ?: Instant.now())

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }

                ProfileImage(
                    painter = painterResource(R.drawable.avatar_0),
                    description = "profile"
                )

                TextAndLabel(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    name = contact.fullName(),
                    description = timeAgo.value,
                )
            }
        }
    ) { padding ->
        ConversationComponent(
            modifier = modifier.padding(padding),
            messages = messages,
            contact = contact,
            currentUser = currentUser,
            onSend = onSend,
        )
    }
}