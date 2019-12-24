package org.wit.hillfortfinder.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel

class HillfortMapsPresenter(val view: HillfortMapsView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {
        map.uiSettings.isZoomControlsEnabled = true
        hillforts.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        doAsync {
            val hillfort = app.hillforts.findById(tag)
            uiThread {
                if (hillfort != null) {
                    view.showHillfort(hillfort)
                }
            }
        }
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findByUserId(app.currentUser?.id!!)
            uiThread {
                if (hillforts != null) {
                    view.showHillforts(hillforts)
                }
            }
        }
    }
}