package com.amadiyawa.feature_contact

import com.amadiyawa.feature_contact.data.dataModule
import com.amadiyawa.feature_contact.domain.domainModule
import com.amadiyawa.feature_contact.presentation.presentationModule

val featureContactModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)