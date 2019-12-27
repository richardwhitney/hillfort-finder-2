package org.wit.hillfortfinder.views.login

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.views.signup.SignupView
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class LoginPresenter(view: BaseView): BasePresenter(view) {

    fun doLogin(email: String, password: String): Boolean {
        var user = app.users.loign(email, password)
        return if (user != null) {
            app.currentUser = user
            view?.navigateTo(VIEW.LIST)
            true
        } else {
            false
        }
    }

    fun showSignup() {
        view?.navigateTo(VIEW.SIGNUP)
    }
}