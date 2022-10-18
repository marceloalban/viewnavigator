package com.alban.viewnavigator.app

import android.app.Application
import com.alban.viewnavigator.ViewNavigatorRegister
import com.alban.viewnavigator.ViewNavigatorRegister.Companion.register

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ViewNavigatorRegister().register(this)
    }
}