package org.wit.hillfortfinder.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*

import org.wit.hillfortfinder.R

class SettingsView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarSettings.title = "Settings"
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = SettingsPresenter(this)

        btnSettings.setOnClickListener {
            info("Update Settings button pressed")
            if (settingsEmail.text.isNotEmpty() && settingsPassword.text.isNotEmpty()) {
                if (!presenter.doUpdateSettings(settingsEmail.text.toString(), settingsPassword.text.toString())) {
                    toast("Email is already registered")
                }
            }
            else {
                toast("Email and password are required")
            }
        }
    }

    fun showSettings(email: String, password: String, numHillforts: Int, numVisited: Int) {
        settingsEmail.setText(email)
        settingsPassword.setText(password)
        totalHillforts.text = "Total number of hillforts: $numHillforts"
        totalHillfortsVisited.text = "Total number of hillforts visited: $numVisited"
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_logout -> {
                presenter.doLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}