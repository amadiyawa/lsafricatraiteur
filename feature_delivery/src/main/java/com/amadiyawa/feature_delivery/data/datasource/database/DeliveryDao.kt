package com.amadiyawa.feature_delivery.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amadiyawa.feature_delivery.data.datasource.database.model.DeliveryEntityModel

@Dao
internal interface DeliveryDao {
    @Query("SELECT * FROM deliveries")
    suspend fun getAllDeliveries(): List<DeliveryEntityModel>

    @Query("SELECT * FROM deliveries WHERE id = :id")
    suspend fun getDeliveryById(id: Int): DeliveryEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDelivery(delivery: DeliveryEntityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveries(deliveries: List<DeliveryEntityModel>)

    @Query("DELETE FROM deliveries")
    suspend fun deleteAllDeliveries()

    @Query("DELETE FROM deliveries WHERE id = :id")
    suspend fun deleteDeliveryById(id: Int)
}