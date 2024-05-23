package com.amadiyawa.feature_contact.domain.usecase

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_contact.domain.repository.ContactRepository

internal class SendWhatsAppMessageUseCase(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        message: String
    ): Result<Boolean> {
        return contactRepository.sendWhatsAppMessage(phoneNumber, message)
    }
}