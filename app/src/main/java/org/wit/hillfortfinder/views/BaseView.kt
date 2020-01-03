package org.wit.hillfortfinder.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.editlocation.EditLocationView
import org.wit.hillfortfinder.views.hillfort.HillfortView
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView
import org.wit.hillfortfinder.views.login.LoginView
import org.wit.hillfortfinder.views.map.HillfortMapsView
import org.wit.hillfortfinder.views.settings.SettingsView
import org.wit.hillfortfinder.views.signup.SignupView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, HILLFORT, LIST, LOGIN, MAPS, SETTINGS, SIGNUP
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, HillfortListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
      VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
      VIEW.MAPS -> intent = Intent(this, HillfortMapsView::class.java)
      VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
      VIEW.SIGNUP -> intent = Intent(this, SignupView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showHillfort(hillfort: HillfortModel) {}
  open fun showHillforts(hillforts: List<HillfortModel>) {}
  open fun showSettings(email: String, numHillforts: Int, numVisited: Int) {}

  open fun showProgress() {}
  open fun hideProgress() {}
}