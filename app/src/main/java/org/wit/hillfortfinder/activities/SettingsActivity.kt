package org.wit.hillfortfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.models.UserModel

class SettingsActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarSettings.title = "Settings"
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        app = application as MainApp

        settingsEmail.setText(app.currentUser.email)
        settingsPassword.setText(app.currentUser.password)

        info("Current user: ${app.currentUser}")

        btnSettings.setOnClickListener {
            info("Update Settings button pressed")
            user.email = settingsEmail.text.toString()
            user.password = settingsPassword.text.toString()
            user.id = app.currentUser.id
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                var updatedUser = app.users.update(user.copy())
                if (updatedUser != null) {
                    app.currentUser = updatedUser
                    startActivityForResult<HillfortListActivity>(0)
                }
                else {
                    toast("Email is already registered")
                }
            }
            else {
                toast("Email and password are required")
            }
        }
    }
}