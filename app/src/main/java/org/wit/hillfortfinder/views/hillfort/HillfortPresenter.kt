package org.wit.hillfortfinder.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.helpers.checkLocationPermissions
import org.wit.hillfortfinder.helpers.isPermissionGranted
import org.wit.hillfortfinder.helpers.showImagePicker
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.Location
import org.wit.hillfortfinder.views.*
import java.text.SimpleDateFormat
import java.util.*

class HillfortPresenter(view: BaseView): BasePresenter(view) {

    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    var map: GoogleMap? = null

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
        else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    fun doAddOrSave(title: String, description: String, additionalNotes: String, dateVisited: String,
                    rating: Float) {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.dateVisited = dateVisited
        hillfort.rating = rating
        hillfort.userId = app.auth.currentUser?.uid!!
        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            }
            else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doVisited(visited: Boolean) : String {
        hillfort.visited = visited
        return if (hillfort.visited) {
            val currentDate: Date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            dateFormat.format(currentDate)
        } else {
            ""
        }
    }

    fun doFavourited(favourited: Boolean) {
        hillfort.favourited = favourited
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.lat, hillfort.lng, hillfort.zoom))
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.lat, hillfort.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.lat = lat
        hillfort.lng = lng
        hillfort.zoom = 15f
        map?.clear()
        map?.uiSettings?.isZoomControlsEnabled = true
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.lat, hillfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.lat, hillfort.lng), hillfort.zoom))
        view?.showHillfort(hillfort)
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
                locationUpdate(hillfort.lat, hillfort.lng)
            }
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        }
        else {
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }
}