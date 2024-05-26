package com.amadiyawa.feature_delivery.domain.usecase

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.domain.repository.DeliveryRepository

internal class GetDeliveryListUseCase(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(): Result<List<Delivery>> {
        return deliveryRepository.getAllDeliveries()
    }
}