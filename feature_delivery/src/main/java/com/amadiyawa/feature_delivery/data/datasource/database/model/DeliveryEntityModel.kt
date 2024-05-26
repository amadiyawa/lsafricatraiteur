package com.amadiyawa.feature_delivery.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amadiyawa.feature_delivery.domain.model.Delivery
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Entity(tableName = "deliveries")
@TypeConverters(MenusTypeConverters::class)
internal data class DeliveryEntityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerFullName: String,
    val address: String,
    val menuList: List<String>,
    val paymentMean: String,
    val paymentPhoneNumber: String,
    val status: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

internal fun DeliveryEntityModel.toDelivery() = Delivery(
    id = id,
    customerFullName = customerFullName,
    address = address,
    menuList = menuList,
    paymentMean = paymentMean,
    paymentPhoneNumber = paymentPhoneNumber,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt
)

internal class MenusTypeConverters{
    @TypeConverter
    fun menuListToString(menuList: List<String>): String {
        return Json.encodeToString(ListSerializer(String.serializer()), menuList)
    }

    @TypeConverter
    fun stringToMenuList(menuList: String): List<String> {
        return Json.decodeFromString(menuList)
    }
}
