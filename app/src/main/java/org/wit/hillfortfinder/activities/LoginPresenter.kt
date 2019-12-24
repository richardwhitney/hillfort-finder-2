package org.wit.hillfortfinder.activities

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.main.MainApp

class LoginPresenter(val view: LoginView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doLogin(email: String, password: String): Boolean {
        var user = app.users.loign(email, password)
        return if (user != null) {
            app.currentUser = user
            view.startActivityForResult<HillfortListView>(0)
            true
        } else {
            false
        }
    }

    fun showSignup() {
        view.startActivityForResult<SignupView>(0)
    }
}