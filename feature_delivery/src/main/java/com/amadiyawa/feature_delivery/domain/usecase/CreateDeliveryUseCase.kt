package com.amadiyawa.feature_delivery.domain.usecase

import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.domain.repository.DeliveryRepository

internal class CreateDeliveryUseCase(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(delivery: Delivery) {
        deliveryRepository.insertDelivery(delivery)
    }
}