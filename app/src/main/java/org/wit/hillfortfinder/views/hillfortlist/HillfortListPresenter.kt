package org.wit.hillfortfinder.views.hillfortlist

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillfortfinder.R
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

    fun navigateToFrag(fragment: Fragment) {
        view?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}