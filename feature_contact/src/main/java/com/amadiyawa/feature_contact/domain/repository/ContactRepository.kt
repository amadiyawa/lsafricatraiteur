package com.amadiyawa.feature_contact.domain.repository
import com.amadiyawa.feature_base.domain.result.Result

internal interface ContactRepository {
    suspend fun sendWhatsAppMessage(phoneNumber: String, message: String): Result<Boolean>
}