package com.amadiyawa.feature_delivery.presentation.screen.deliverylist

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_base.presentation.viewmodel.BaseAction
import com.amadiyawa.feature_base.presentation.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.viewmodel.BaseViewModel
import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.domain.usecase.GetDeliveryListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DeliveryListViewModel(
    private val getDeliveryListUseCase: GetDeliveryListUseCase,
) : BaseViewModel<DeliveryListViewModel.UiState, DeliveryListViewModel.Action>(UiState.Loading) {
    private var job: Job? = null

    fun onEnter() {
        getDeliveryList()
    }

    private fun getDeliveryList() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getDeliveryListUseCase().also { deliveryListResult ->
                val action = when (deliveryListResult) {
                    is Result.Success -> {
                        if (deliveryListResult.value.isEmpty()) {
                            Action.DeliveryListLoadEmpty
                        } else {
                            Action.DeliveryListLoadSuccess(
                                deliveryListResult.value
                            )
                        }
                    }
                    is Result.Failure -> Action.DeliveryListLoadFailure
                }
                sendAction(action)
            }
        }
    }

    internal sealed interface Action : BaseAction<UiState> {
        data class DeliveryListLoadSuccess(
            private val deliveryList: List<Delivery>
        ) : Action {
            override fun reduce(state: UiState): UiState = UiState.Content(
                deliveryList = deliveryList
            )
        }

        data object DeliveryListLoadEmpty : Action {
            override fun reduce(state: UiState) = UiState.Empty
        }

        data object DeliveryListLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class Content(
            val deliveryList: List<Delivery>
        ) : UiState
        data object Empty : UiState
        data object Loading : UiState
        data object Error : UiState
    }
}