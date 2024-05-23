package com.amadiyawa.feature_contact.domain

import com.amadiyawa.feature_contact.domain.usecase.SendWhatsAppMessageUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::SendWhatsAppMessageUseCase)
}