package com.amadiyawa.feature_delivery.domain.repository

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.domain.model.Menu

internal interface MenuRepository {
    suspend fun getAllMenus(): Result<List<Menu>>
    suspend fun getMenuById(id: Int): Result<Menu>
    suspend fun insertMenu(menu: Menu)
    suspend fun insertMenus(menus: List<Menu>)
}