package com.amadiyawa.feature_delivery

import com.amadiyawa.feature_delivery.data.dataModule
import com.amadiyawa.feature_delivery.domain.domainModule
import com.amadiyawa.feature_delivery.presentation.presentationModule

val featureDeliveryModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)