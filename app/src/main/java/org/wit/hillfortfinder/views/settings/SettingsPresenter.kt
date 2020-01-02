package org.wit.hillfortfinder.views.settings

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
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
                view?.showSettings(app.auth.currentUser?.email!!, numHillforts, numVisited)
            }
        }
    }

    fun doUpdateSettings(email: String, password: String) {
        app.auth.currentUser?.updateEmail(email)?.addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                app.auth.currentUser?.updatePassword(password)?.addOnCompleteListener(view!!) { task2 ->
                    if (task2.isSuccessful) {
                        view?.navigateTo(VIEW.LIST, 0)
                    }
                    else {
                        view?.toast("Update Password Failed: ${task2.exception?.message}")
                    }
                }
            }
            else {
                view?.toast("Update Email Failed: ${task.exception?.message}")
            }
        }
    }

    fun doLogout() {
        app.auth.signOut()
        val intent = Intent(view, LoginView::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        view?.startActivity(intent)
        view?.finish()
    }

    private fun getTotalHillforts(): Int {
        return app.hillforts.findByUserId(app.auth.currentUser?.uid!!).size
    }

    private fun getTotalHillfortsVisited(): Int {
        var totalHillforts: List<HillfortModel> = app.hillforts.findByUserId(app.auth.currentUser?.uid!!)
        return totalHillforts.filter { p -> p.visited }.size
    }
}