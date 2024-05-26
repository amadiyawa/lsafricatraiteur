package com.amadiyawa.feature_delivery.domain

import com.amadiyawa.feature_delivery.domain.usecase.CreateDeliveryUseCase
import com.amadiyawa.feature_delivery.domain.usecase.GetDeliveryListUseCase
import com.amadiyawa.feature_delivery.domain.usecase.GetMenuListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetDeliveryListUseCase)
    singleOf(::CreateDeliveryUseCase)
    singleOf(::GetMenuListUseCase)
}