package com.amadiyawa.feature_delivery.presentation

import com.amadiyawa.feature_delivery.presentation.screen.deliverycreate.DeliveryCreateViewModel
import com.amadiyawa.feature_delivery.presentation.screen.deliverylist.DeliveryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::DeliveryListViewModel)
    viewModelOf(::DeliveryCreateViewModel)
}