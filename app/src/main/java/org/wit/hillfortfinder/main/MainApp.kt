package org.wit.hillfortfinder.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.models.HillfortModel

class MainApp: Application(), AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Finder started")
        hillforts.add(HillfortModel("One", "About one..."))
        hillforts.add(HillfortModel("Two", "About two..."))
        hillforts.add(HillfortModel("Three", "About three..."))
    }
}