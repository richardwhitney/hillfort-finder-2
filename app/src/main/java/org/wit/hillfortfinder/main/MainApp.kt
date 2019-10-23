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
        hillforts.create(HillfortModel("One", "About one..."))
        hillforts.create(HillfortModel("Two", "About two..."))
        hillforts.create(HillfortModel("Three", "About three..."))
    }
}