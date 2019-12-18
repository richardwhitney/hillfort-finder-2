package org.wit.hillfortfinder.activities

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.helpers.showImagePicker
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.Location
import java.text.SimpleDateFormat
import java.util.*

class HillfortPresenter(val view: HillfortActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun doAddOrSave(title: String, description: String, additionalNotes: String, dateVisited: String,
                    rating: Float) {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dateVisited = dateVisited
        hillfort.rating = rating
        hillfort.userId = app.currentUser?.id!!
        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            }
            else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view.finish()
            }
        }
    }

    fun doVisited(visited: Boolean) : String {
        hillfort.visited = visited
        if (hillfort.visited) {
            val currentDate: Date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return dateFormat.format(currentDate)
        }
        else {
            return ""
        }
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}