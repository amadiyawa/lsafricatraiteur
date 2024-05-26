package com.amadiyawa.feature_delivery.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amadiyawa.feature_delivery.domain.model.Menu

@Entity(tableName = "menus")
internal data class MenuEntityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

internal fun MenuEntityModel.toMenu() = Menu(
    id = id,
    name = name,
    price = price,
    createdAt = createdAt,
    updatedAt = updatedAt
)
