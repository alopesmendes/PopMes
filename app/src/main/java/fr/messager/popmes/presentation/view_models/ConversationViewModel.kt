package fr.messager.popmes.presentation.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.messager.popmes.common.Constants
import fr.messager.popmes.common.Extension.mapToNavData
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.mapper.ConversationParamsToConversationParamsProto.reverseMapTo
import fr.messager.popmes.proto.ConversationParamsProto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var selectedContact: Contact? by mutableStateOf(null)

    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    private val conversationParams =
        savedStateHandle.get<String>(Constants.PARAM_CONVERSATION).mapToNavData(
            parseFrom = ConversationParamsProto::parseFrom,
            mapTo = { this.reverseMapTo() },
        )

    init {
        onSelectedContactChange(conversationParams?.contact)
        initMessages()
    }

    private fun initMessages() {
        viewModelScope.launch {
            _messages.clear()
            _messages.addAll(conversationParams?.messages ?: emptyList())
        }
    }

    companion object {
        private val TAG = ConversationViewModel::class.simpleName
    }

    fun onSelectedContactChange(contact: Contact?) {
        viewModelScope.launch {
            Log.d(TAG, "onSelectedContactChange: $contact")
            selectedContact = contact
        }
    }
}