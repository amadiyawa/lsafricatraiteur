package com.amadiyawa.feature_delivery.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amadiyawa.feature_delivery.data.datasource.database.model.DeliveryEntityModel
import com.amadiyawa.feature_delivery.data.datasource.database.model.MenuEntityModel

@Database(
    entities = [
        MenuEntityModel::class,
        DeliveryEntityModel::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class DeliveryDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao
    abstract fun deliveryDao(): DeliveryDao
}