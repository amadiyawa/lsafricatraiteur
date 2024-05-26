package com.amadiyawa.feature_delivery.domain.model

import com.amadiyawa.feature_delivery.data.datasource.database.model.MenuEntityModel
import kotlinx.serialization.Serializable

@Serializable
internal data class Menu(
    val id: Int? = null,
    val name: String,
    val price: Int,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)

internal fun Menu.toMenuEntityModel() = MenuEntityModel(
    name = name,
    price = price
)
