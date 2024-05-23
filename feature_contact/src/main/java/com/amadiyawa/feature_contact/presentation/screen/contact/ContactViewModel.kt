package com.amadiyawa.feature_contact.presentation.screen.contact

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.presentation.viewmodel.BaseAction
import com.amadiyawa.feature_base.presentation.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.viewmodel.BaseViewModel
import com.amadiyawa.feature_contact.domain.usecase.SendWhatsAppMessageUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ContactViewModel(
    private val sendWhatsappMessageUseCase: SendWhatsAppMessageUseCase
) : BaseViewModel<ContactViewModel.UiState, ContactViewModel.Action>(UiState.IDLE) {

    private var job: Job? = null

    private var _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private var _purpose = MutableStateFlow("")
    val purpose = _purpose.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun sendWhatsAppMessage(context: Context) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val message = "Hi, I am ${fullName.value}. I am contacting you for ${purpose.value}. ${message.value}"
            val phoneNumber = "+237698518698"

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onFullNameChanged(value: String) {
        _fullName.value = value
    }

    fun onPurposeChanged(value: String) {
        _purpose.value = value
    }

    fun onMessageChanged(value: String) {
        _message.value = value
    }

    internal sealed interface Action: BaseAction<UiState> {
        data object Loading : Action {
            override fun reduce(state: UiState) = UiState.Loading
        }

        data class ContactSuccess(private val isSent: Boolean) : Action {
            override fun reduce(state: UiState) = UiState.ContactContent(isSent = isSent)
        }

        data object Error : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class ContactContent(val isSent: Boolean) : UiState
        data object IDLE : UiState
        data object Loading : UiState
        data object Error : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}