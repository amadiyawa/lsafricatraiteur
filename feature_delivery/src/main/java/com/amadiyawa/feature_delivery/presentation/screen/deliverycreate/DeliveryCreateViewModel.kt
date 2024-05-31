package com.amadiyawa.feature_delivery.presentation.screen.deliverycreate

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.common.util.formatDate
import com.amadiyawa.feature_base.common.util.formatTime
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

    private var _menuList = MutableStateFlow(emptyList<Menu>())

    private var _filteredMenuList = MutableStateFlow(emptyList<Menu>())
    val filteredMenuList = _filteredMenuList.asStateFlow()

    private var _selectedMenuList = MutableStateFlow(emptyList<Menu>())
    val selectedMenuList = _selectedMenuList.asStateFlow()

    private var _menu = MutableStateFlow("")
    val menu = _menu.asStateFlow()

    private var _selectedDate = MutableStateFlow(0L)

    private var _selectedTime = MutableStateFlow(0L)

    fun onEnter() {
        getMenuList()
    }

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
                            _menuList.value = menuListResult.value
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

    fun onMenuChanged(value: String) {
        _menu.value = value
        _filteredMenuList.value = _menuList.value.filter { it.name.contains(value, ignoreCase = true) }
    }

    fun onMenuSelected(menu: Menu) {
        _selectedMenuList.value += menu
        _menuList.value = _menuList.value.filter { it != menu }
        _menu.value = ""
        _filteredMenuList.value = emptyList()
    }

    fun onMenuRemoved(menu: Menu) {
        _selectedMenuList.value = _selectedMenuList.value.filter { it != menu }
        _menuList.value += menu
    }

    fun onDateSelected(date: Long) {
        _selectedDate.value = date
    }

    fun onTimeSelected(time: Long) {
        _selectedTime.value = time
    }

    fun validateForm() : Boolean {
        if (_fullName.value.isEmpty()) {
            return false
        }

        if (_address.value.isEmpty()) {
            return false
        }

        if (_paymentMean.value == R.string.payment_mean) {
            return false
        }

        if (_phoneNumber.value.isEmpty()) {
            return false
        }

        if (_selectedMenuList.value.isEmpty()) {
            return false
        }

        if (_selectedDate.value == 0L) {
            return false
        }

        if (_selectedTime.value == 0L) {
            return false
        }

        return true
    }

    fun sendWhatsAppMessage(context: Context) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val message = context.getString(R.string.message_greeting, fullName.value) + "\n " +
                    context.getString(R.string.message_location, _address.value) + "\n " +
                    context.getString(R.string.message_order,
                        _selectedMenuList.value.joinToString(", ") { it.name }) + "\n " +
                    context.getString(R.string.message_payment, _paymentMean.value.toString()) + "\n " +
                    context.getString(R.string.message_phone, _phoneNumber.value) + "\n" +
                    context.getString(R.string.message_delivery_date, formatDate(_selectedDate.value), formatTime(_selectedTime.value))
            val phoneNumber = "+237698518698"

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
                context.startActivity(intent)
                createDeliveryUseCase(
                    Delivery(
                        customerFullName = _fullName.value,
                        address = _address.value,
                        menuList = _selectedMenuList.value.map { it.name },
                        paymentMean = PaymentMean.OM.toString(),
                        paymentPhoneNumber = _phoneNumber.value,
                        status = getRandomDeliveryStatus().toString(),
                        deliveryDate = _selectedDate.value
                    )
                )
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
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class MenuListContent(val menuList: List<Menu>) : UiState
        data object EmptyMenuList : UiState
        data object Loading : UiState
        data object ErrorMenuList : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}