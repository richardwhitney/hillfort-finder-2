package org.wit.hillfortfinder.activities

import org.jetbrains.anko.*
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel

class HillfortListPresenter(val view: HillfortListActivity) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getHillforts() {
        doAsync {
            val hillforts = app.hillforts.findByUserId(app.currentUser?.id!!)
            uiThread {
                view.showHillforts(hillforts)
            }
        }
    }

    fun doAddHillfort() {
        view.startActivityForResult<HillfortActivity>(0)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view.startActivityForResult(view.intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    fun doShowHillfortsMap() {
        view.startActivity<HillfortMapsActivity>()
    }

    fun doShowSettings() {
        view.startActivity<SettingsActivity>()
    }
}