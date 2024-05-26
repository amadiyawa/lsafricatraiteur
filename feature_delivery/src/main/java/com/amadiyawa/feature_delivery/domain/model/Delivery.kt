package com.amadiyawa.feature_delivery.domain.model

import com.amadiyawa.feature_delivery.data.datasource.database.model.DeliveryEntityModel
import kotlinx.serialization.Serializable

@Serializable
data class Delivery(
    val id: Int? = null,
    val customerFullName: String,
    val address: String,
    val menuList: List<String>,
    val paymentMean: String,
    val paymentPhoneNumber: String,
    val status: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)

internal fun Delivery.toDeliveryEntityModel() = DeliveryEntityModel(
    customerFullName = customerFullName,
    address = address,
    menuList = menuList,
    paymentMean = paymentMean,
    paymentPhoneNumber = paymentPhoneNumber,
    status = status,
)
