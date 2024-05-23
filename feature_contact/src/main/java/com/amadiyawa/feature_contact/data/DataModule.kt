package com.amadiyawa.feature_contact.data

import com.amadiyawa.feature_contact.data.repository.ContactRepositoryImpl
import com.amadiyawa.feature_contact.domain.repository.ContactRepository
import org.koin.dsl.module

internal val dataModule = module {
    single<ContactRepository> { ContactRepositoryImpl(get()) }
}