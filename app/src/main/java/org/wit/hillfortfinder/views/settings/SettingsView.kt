package org.wit.hillfortfinder.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*

import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.views.BaseView

class SettingsView: BaseView(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        super.init(toolbarSettings, true)

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

        btnSettings.setOnClickListener {
            info("Update Settings button pressed")
            if (!settingsEmail.text.isNotEmpty() && !settingsPassword.text.isNotEmpty()) {
                toast("Email and password are required")
            }
            else {
                presenter.doUpdateSettings(settingsEmail.text.toString(), settingsPassword.text.toString())
            }
        }
    }

    override fun showSettings(email: String, numHillforts: Int, numVisited: Int) {
        settingsEmail.setText(email)
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

    override fun showProgress() {
        progressBarSettings.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarSettings.visibility = View.GONE
    }
}