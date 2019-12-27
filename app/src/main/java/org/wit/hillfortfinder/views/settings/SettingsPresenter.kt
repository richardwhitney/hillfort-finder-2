package org.wit.hillfortfinder.views.settings

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView
import org.wit.hillfortfinder.views.login.LoginView

class SettingsPresenter(view: BaseView): BasePresenter(view) {

    var user = UserModel()

    init {
        doAsync {
            var numHillforts = getTotalHillforts()
            var numVisited = getTotalHillfortsVisited()
            uiThread {
                view?.showSettings(app.currentUser?.email!!, app.currentUser?.password!!, numHillforts, numVisited)
            }
        }
    }

    fun doUpdateSettings(email: String, password: String): Boolean {
        user.email = email
        user.password = password
        user.id = app.currentUser?.id!!
        var updatedUser = app.users.update(user.copy())
        return if (updatedUser != null) {
            app.currentUser = updatedUser
            view?.navigateTo(VIEW.LIST, 0)
            true
        } else {
            false
        }
    }

    fun doLogout() {
        app.currentUser = null
        val intent = Intent(view, LoginView::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        view?.startActivity(intent)
        view?.finish()
    }

    private fun getTotalHillforts(): Int {
        return app.hillforts.findByUserId(app.currentUser?.id!!).size
    }

    private fun getTotalHillfortsVisited(): Int {
        var totalHillforts: List<HillfortModel> = app.hillforts.findByUserId(app.currentUser?.id!!)
        return totalHillforts.filter { p -> p.visited }.size
    }
}