package com.amadiyawa.feature_delivery.domain.util

import com.amadiyawa.feature_delivery.domain.enum.DeliveryStatus
import kotlin.random.Random

internal fun getRandomDeliveryStatus(): DeliveryStatus {
    val values = DeliveryStatus.entries.toTypedArray()
    return values[Random.nextInt(values.size)]
}