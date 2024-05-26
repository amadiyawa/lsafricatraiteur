package com.amadiyawa.feature_delivery.data.repository

import com.amadiyawa.feature_base.domain.result.Result
import com.amadiyawa.feature_delivery.data.datasource.database.MenuDao
import com.amadiyawa.feature_delivery.data.datasource.database.model.toMenu
import com.amadiyawa.feature_delivery.domain.model.Menu
import com.amadiyawa.feature_delivery.domain.model.toMenuEntityModel
import com.amadiyawa.feature_delivery.domain.repository.MenuRepository

internal class MenuRepositoryImpl(
    private val menuDao: MenuDao
): MenuRepository {
    override suspend fun getAllMenus(): Result<List<Menu>> {
        return try {
            val menuList = menuDao.getAllMenus().map { it.toMenu() }
            Result.Success(menuList)
        } catch (e: Exception) {
            Result.Failure()
        }
    }

    override suspend fun getMenuById(id: Int): Result<Menu> {
        return try {
            val menu = menuDao.getMenuById(id = id).toMenu()
            Result.Success(menu)
        } catch (e: Exception) {
            Result.Failure()
        }
    }

    override suspend fun insertMenu(menu: Menu) {
        try {
            menuDao.insertMenu(menu.toMenuEntityModel())
        } catch (e: Exception) {
            Result.Failure()
        }
    }

    override suspend fun insertMenus(menus: List<Menu>) {
        try {
            menuDao.insertMenus(menus.map { it.toMenuEntityModel() })
        } catch (e: Exception) {
            Result.Failure()
        }
    }

}