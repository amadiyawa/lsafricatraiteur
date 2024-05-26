package com.amadiyawa.feature_delivery.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.amadiyawa.feature_delivery.data.datasource.database.DeliveryDatabase
import com.amadiyawa.feature_delivery.data.datasource.database.MenuDao
import com.amadiyawa.feature_delivery.data.repository.DeliveryRepositoryImpl
import com.amadiyawa.feature_delivery.data.repository.MenuRepositoryImpl
import com.amadiyawa.feature_delivery.data.util.loadMenuList
import com.amadiyawa.feature_delivery.domain.model.toMenuEntityModel
import com.amadiyawa.feature_delivery.domain.repository.DeliveryRepository
import com.amadiyawa.feature_delivery.domain.repository.MenuRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module
import timber.log.Timber

internal val dataModule = module {
    single<DeliveryRepository> { DeliveryRepositoryImpl(get()) }

    single<MenuRepository> { MenuRepositoryImpl(get()) }

    single {
        Room.databaseBuilder(
            get(),
            DeliveryDatabase::class.java,
            "deliveries.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                val menuDao: MenuDao by inject()

                override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val menuList = loadMenuList(get())
                            menuDao.insertMenus(
                                menuList.map { it.toMenuEntityModel() }
                            )
                        } catch (e: Exception) {
                            Timber.e(e, "Error initializing database")
                        }
                    }
                }
            })
            .build()
    }

    single { get<DeliveryDatabase>().deliveryDao() }

    single { get<DeliveryDatabase>().menuDao() }
}