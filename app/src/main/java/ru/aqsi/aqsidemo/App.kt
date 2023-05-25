package ru.studiq.m2.mcashier.model.classes

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        instance = this
        res = resources
    }
    companion object {
        lateinit  var appContext: Context
        var instance: App? = null
            private set
        var res: Resources? = null
            private set

        var displayDensity: Float = 1.0F
            get() {
                return App.appContext.getResources().getDisplayMetrics().density
            }
    }
}