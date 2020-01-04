package org.wit.hillfortfinder.views.signup

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.models.firebase.HillfortFireStore
import org.wit.hillfortfinder.views.BasePresenter
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.VIEW
import org.wit.hillfortfinder.views.hillfortlist.HillfortListView

class SignupPresenter(view: BaseView): BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
      if (app.hillforts is HillfortFireStore) {
          fireStore = app.hillforts as HillfortFireStore
      }
    }

    fun doSignup(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                fireStore?.userId = auth.currentUser!!.uid
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            }
            else {
                view?.hideProgress()
                view?.toast("Signup Failed: ${task.exception?.message}")
            }
        }
    }
}