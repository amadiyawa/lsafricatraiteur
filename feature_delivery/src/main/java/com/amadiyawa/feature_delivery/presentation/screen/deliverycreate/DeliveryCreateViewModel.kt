package com.amadiyawa.feature_delivery.presentation.screen.deliverycreate

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_base.presentation.viewmodel.BaseAction
import com.amadiyawa.feature_base.presentation.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.viewmodel.BaseViewModel
import com.amadiyawa.feature_delivery.R
import com.amadiyawa.feature_delivery.domain.enum.PaymentMean
import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.domain.model.Menu
import com.amadiyawa.feature_delivery.domain.usecase.CreateDeliveryUseCase
import com.amadiyawa.feature_delivery.domain.usecase.GetMenuListUseCase
import com.amadiyawa.feature_delivery.domain.util.getRandomDeliveryStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class DeliveryCreateViewModel(
    private val getMenuListUseCase: GetMenuListUseCase,
    private val createDeliveryUseCase: CreateDeliveryUseCase
) : BaseViewModel<DeliveryCreateViewModel.UiState, DeliveryCreateViewModel.Action>(UiState.Loading) {

    private var job: Job? = null

    private var _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private var _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private var _paymentMean = MutableStateFlow(R.string.payment_mean)
    val paymentMean = _paymentMean.asStateFlow()

    private var _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private fun getMenuList() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getMenuListUseCase().also { menuListResult ->
                val action = when (menuListResult) {
                    is Result.Success -> {
                        if (menuListResult.value.isEmpty()) {
                            Action.MenuListLoadEmpty
                        } else {
                            Action.MenuListLoadSuccess(
                                menuListResult.value
                            )
                        }
                    }
                    is Result.Failure -> Action.MenuListLoadFailure
                }
                sendAction(action)
            }
        }
    }

    fun sendWhatsAppMessage(context: Context) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val message = "Hi, I am ${fullName.value}.\n Located at ${_address.value}. \n I am ordering ... \n I will pay using ${_paymentMean.value}. \n The phone number for payment is ${_phoneNumber.value}"
            val phoneNumber = "+237698518698"

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
                context.startActivity(intent)
                createDeliveryUseCase(Delivery(
                    customerFullName = _fullName.value,
                    address = _address.value,
                    menuList = listOf("..."),
                    paymentMean = PaymentMean.OM.toString(),
                    paymentPhoneNumber = _phoneNumber.value,
                    status = getRandomDeliveryStatus().toString()
                ))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onFullNameChanged(value: String) {
        _fullName.value = value
    }

    fun onAddressChanged(value: String) {
        _address.value = value
    }

    fun onPaymentMeanChanged(value: Int) {
        _paymentMean.value = value
    }

    fun onPhoneNumberChanged(value: String) {
        _phoneNumber.value = value
    }

    internal sealed interface Action: BaseAction<UiState> {
        data class MenuListLoadSuccess(
            private val menuList: List<Menu>
        ) : Action {
            override fun reduce(state: UiState): UiState = UiState.MenuListContent(
                menuList = menuList
            )
        }

        data object MenuListLoadEmpty : Action {
            override fun reduce(state: UiState) = UiState.EmptyMenuList
        }

        data object MenuListLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.ErrorMenuList
        }

        data object Loading : Action {
            override fun reduce(state: UiState) = UiState.Loading
        }

        data class DeliverySuccess(private val done: Boolean) : Action {
            override fun reduce(state: UiState) = UiState.DeliveryCreateContent(done = done)
        }

        data object ErrorDeliveryCreate : Action {
            override fun reduce(state: UiState) = UiState.ErrorDeliveryCreate
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class MenuListContent(val menuList: List<Menu>) : UiState
        data class DeliveryCreateContent(val done: Boolean) : UiState
        data object IDLE : UiState
        data object EmptyMenuList : UiState
        data object Loading : UiState
        data object ErrorMenuList : UiState
        data object ErrorDeliveryCreate : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}