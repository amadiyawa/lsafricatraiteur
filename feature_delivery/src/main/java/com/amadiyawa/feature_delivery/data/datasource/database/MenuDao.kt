package com.amadiyawa.feature_delivery.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amadiyawa.feature_delivery.data.datasource.database.model.MenuEntityModel

@Dao
internal interface MenuDao {
    @Query("SELECT * FROM menus")
    suspend fun getAllMenus(): List<MenuEntityModel>

    @Query("SELECT * FROM menus WHERE id = :id")
    suspend fun getMenuById(id: Int): MenuEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: MenuEntityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenus(menus: List<MenuEntityModel>)

    @Query("DELETE FROM menus")
    suspend fun deleteAllMenus()

    @Query("DELETE FROM menus WHERE id = :id")
    suspend fun deleteMenuById(id: Int)
}