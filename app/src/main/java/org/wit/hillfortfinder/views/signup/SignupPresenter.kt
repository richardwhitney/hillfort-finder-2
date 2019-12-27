package org.wit.hillfortfinder.views.signup

import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class SignupPresenter(view: BaseView): BasePresenter(view) {

    var user = UserModel()

    fun doSignup(email: String, password: String): Boolean {
        user.email = email
        user.password = password
        return if (app.users.signup(user.copy())) {
            app.currentUser = user
            view?.navigateTo(VIEW.LIST, 0)
            true
        } else {
            false
        }
    }
}