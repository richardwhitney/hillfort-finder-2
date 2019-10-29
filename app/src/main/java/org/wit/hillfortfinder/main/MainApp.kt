package org.wit.hillfortfinder.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.models.HillfortJSONStore
import org.wit.hillfortfinder.models.HillfortMemStore
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.HillfortStore

class MainApp: Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Hillfort Finder started")
    }
}