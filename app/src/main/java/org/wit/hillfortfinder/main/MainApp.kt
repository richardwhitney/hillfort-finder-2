package org.wit.hillfortfinder.main

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.models.*
import org.wit.hillfortfinder.room.HillfortStoreRoom

class MainApp: Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var users: UserStore
    var currentUser: UserModel? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortStoreRoom(applicationContext)
        users = UserJSONStore(applicationContext)
        auth = FirebaseAuth.getInstance()
        info("Hillfort Finder started")
    }
}