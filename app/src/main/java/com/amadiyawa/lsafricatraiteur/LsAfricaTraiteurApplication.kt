package com.amadiyawa.lsafricatraiteur

import android.app.Application
import timber.log.Timber

class LsAfricaTraiteurApplication: Application() {
    override fun onCreate() {
        initTimber()
        super.onCreate()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}