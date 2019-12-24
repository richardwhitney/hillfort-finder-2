package org.wit.hillfortfinder.views.signup

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class SignupPresenter(val view: SignupView) {

    var app: MainApp
    var user = UserModel()

    init {
        app = view.application as MainApp
    }

    fun doSignup(email: String, password: String): Boolean {
        user.email = email
        user.password = password
        return if (app.users.signup(user.copy())) {
            app.currentUser = user
            view.startActivityForResult<HillfortListView>(0)
            true
        } else {
            false
        }
    }
}