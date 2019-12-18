package org.wit.hillfortfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfortfinder.R

import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel

class HillfortMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)
        toolbar.title = title
        setSupportActionBar(toolbar)
        app = application as MainApp
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        doAsync {
            val hillfort = app.hillforts.findById(tag)
            uiThread {
                if (hillfort != null) {
                    showHillfort(hillfort)
                }
            }
        }
        return true
    }

    fun showHillfort(hillfort: HillfortModel) {
        currentTitle.text = hillfort.title
        currentDescription.text = hillfort.description
        currentImage.setImageBitmap(readImageFromPath(this, hillfort.image))
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        hillforts.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.setOnMarkerClickListener(this)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        doAsync {
            val hillforts = app.hillforts.findByUserId(app.currentUser?.id!!)
            uiThread {
                if (hillforts != null) {
                    showHillforts(hillforts)
                }
            }
        }
    }
}
