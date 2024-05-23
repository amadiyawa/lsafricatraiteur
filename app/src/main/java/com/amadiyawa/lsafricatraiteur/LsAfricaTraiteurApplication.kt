package com.amadiyawa.lsafricatraiteur

import android.app.Application
import com.amadiyawa.feature_contact.featureContactModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class LsAfricaTraiteurApplication: Application() {
    override fun onCreate() {
        initKoin()
        initTimber()
        super.onCreate()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@LsAfricaTraiteurApplication)

            modules(featureContactModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}