package org.wit.hillfortfinder.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.models.HillfortMemStore
import org.wit.hillfortfinder.models.HillfortModel

class MainApp: Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Finder started")
        hillforts.create(HillfortModel(0,"One", "About one..."))
        hillforts.create(HillfortModel(1,"Two", "About two..."))
        hillforts.create(HillfortModel(2,"Three", "About three..."))
    }
}