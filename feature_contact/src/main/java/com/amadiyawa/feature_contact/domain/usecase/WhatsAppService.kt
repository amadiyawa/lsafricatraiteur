package com.amadiyawa.feature_contact.domain.usecase

import com.amadiyawa.feature_base.domain.result.Result

interface WhatsAppService {
    fun sendWhatsAppMessage(phoneNumber: String, message: String) : Result<Boolean>
}