package com.amadiyawa.feature_delivery.data.repository

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.data.datasource.database.DeliveryDao
import com.amadiyawa.feature_delivery.data.datasource.database.model.toDelivery
import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.domain.model.toDeliveryEntityModel
import com.amadiyawa.feature_delivery.domain.repository.DeliveryRepository

internal class DeliveryRepositoryImpl(
    private val deliveryDao: DeliveryDao
): DeliveryRepository {
    override suspend fun getAllDeliveries(): Result<List<Delivery>> =
        try {
            val deliveryList = deliveryDao.getAllDeliveries().map { it.toDelivery() }
            Result.Success(deliveryList)
        } catch (e: Exception) {
            Result.Failure()
        }

    override suspend fun getDeliveryById(id: Int): Result<Delivery> =
        try {
            val delivery = deliveryDao.getDeliveryById(id = id).toDelivery()
            Result.Success(delivery)
        } catch (e: Exception) {
            Result.Failure()
        }

    override suspend fun insertDelivery(delivery: Delivery) {
        try {
            deliveryDao.insertDelivery(delivery.toDeliveryEntityModel())
        } catch (e: Exception) {
            Result.Failure()
        }
    }

    override suspend fun insertDeliveries(deliveries: List<Delivery>) {
        try {
            deliveryDao.insertDeliveries(deliveries.map { it.toDeliveryEntityModel() })
        } catch (e: Exception) {
            Result.Failure()
        }
    }
}