package org.wit.hillfortfinder.views.hillfortlist.fragment.allhillforts

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.hillfort.HillfortView

class AllHillfortsPresenter (val view: AllHillfortsFragment) {

  var app: MainApp

  init {
    app = view.activity?.application as MainApp
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    val intent = Intent (view.activity, HillfortView::class.java).putExtra("hillfort_edit", hillfort)
    view.startActivityForResult(intent, 0)
  }

  fun loadHillforts() {
    doAsync {
      val hillforts = app.hillforts.findAll()
      uiThread {
        view.showHillforts(hillforts)
      }
    }
  }
}