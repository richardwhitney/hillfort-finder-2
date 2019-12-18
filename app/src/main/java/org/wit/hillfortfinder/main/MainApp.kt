package org.wit.hillfortfinder.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.models.*
import org.wit.hillfortfinder.room.HillfortStoreRoom

class MainApp: Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var users: UserStore
    var currentUser: UserModel? = null

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortStoreRoom(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Hillfort Finder started")
    }
}