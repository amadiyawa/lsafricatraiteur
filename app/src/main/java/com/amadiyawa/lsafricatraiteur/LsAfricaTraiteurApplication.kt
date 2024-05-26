package com.amadiyawa.lsafricatraiteur

import android.app.Application
import com.amadiyawa.feature_contact.featureContactModule
import com.amadiyawa.feature_delivery.featureDeliveryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level
import timber.log.Timber

class LsAfricaTraiteurApplication: Application() {
    override fun onCreate() {
        initKoin()
        initTimber()
        super.onCreate()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@LsAfricaTraiteurApplication)

            modules(featureContactModule)
            modules(featureDeliveryModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}