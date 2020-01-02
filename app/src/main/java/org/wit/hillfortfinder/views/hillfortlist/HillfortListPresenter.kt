package org.wit.hillfortfinder.views.hillfortlist

import org.jetbrains.anko.*
import org.wit.hillfortfinder.views.map.HillfortMapsView
import org.wit.hillfortfinder.views.settings.SettingsView
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfort.HillfortView

class HillfortListPresenter(view: BaseView): BasePresenter(view) {

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doShowSettings() {
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findByUserId(app.auth.currentUser?.uid!!)
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }
}