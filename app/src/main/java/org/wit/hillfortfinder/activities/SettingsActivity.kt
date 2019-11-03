package org.wit.hillfortfinder.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.models.HillfortModel
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

        settingsEmail.setText(app.currentUser?.email)
        settingsPassword.setText(app.currentUser?.password)

        info("Current user: ${app.currentUser}")

        totalHillforts.setText("Total number of hillforts: ${getTotalHillforts()}")
        totalHillfortsVisited.setText("Total number of hillforts visited: ${getTotalHillfortsVisited()}")

        btnSettings.setOnClickListener {
            info("Update Settings button pressed")
            user.email = settingsEmail.text.toString()
            user.password = settingsPassword.text.toString()
            user.id = app.currentUser?.id!!
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_logout -> {
                app.currentUser = null
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTotalHillforts(): Int {
        return app.hillforts.findByUserId(app.currentUser?.id!!).size
    }

    private fun getTotalHillfortsVisited(): Int {
        var totalHillforts: List<HillfortModel> = app.hillforts.findByUserId(app.currentUser?.id!!)
        return totalHillforts.filter { p -> p.visited }.size
    }
}