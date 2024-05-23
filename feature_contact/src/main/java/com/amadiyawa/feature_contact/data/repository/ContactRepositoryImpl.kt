package com.amadiyawa.feature_contact.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_contact.domain.repository.ContactRepository

internal class ContactRepositoryImpl(private val context: Context): ContactRepository {
    override suspend fun sendWhatsAppMessage(
        phoneNumber: String,
        message: String
    ): Result<Boolean> {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
            setPackage("com.whatsapp")
        }

        return if (sendIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(sendIntent)
            Result.Success(true)
        } else {
            Result.Failure()
        }
    }

}