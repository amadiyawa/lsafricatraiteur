package com.amadiyawa.feature_contact.presentation

import org.koin.dsl.module
import com.amadiyawa.feature_contact.presentation.screen.contact.ContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf

internal val presentationModule = module {
    viewModelOf(::ContactViewModel)
}