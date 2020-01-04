package org.wit.hillfortfinder.views.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.hillfortfinder.R

import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.BaseView

class HillfortMapsView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HillfortMapsPresenter
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)
        toolbar.title = title
        super.init(toolbar, true)

        presenter = initPresenter(HillfortMapsPresenter(this)) as HillfortMapsPresenter

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadHillforts()
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
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun showHillfort(hillfort: HillfortModel) {
        currentTitle.text = hillfort.title
        currentDescription.text = hillfort.description
        currentImage.setImageBitmap(readImageFromPath(this, hillfort.image))
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        presenter.doPopulateMap(map, hillforts)
    }
}
