package com.amadiyawa.feature_delivery.domain.repository

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.domain.model.Delivery

internal interface DeliveryRepository {
    suspend fun getAllDeliveries(): Result<List<Delivery>>
    suspend fun getDeliveryById(id: Int): Result<Delivery>
    suspend fun insertDelivery(delivery: Delivery)
    suspend fun insertDeliveries(deliveries: List<Delivery>)
}