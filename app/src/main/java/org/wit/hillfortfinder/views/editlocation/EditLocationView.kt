package org.wit.hillfortfinder.views.editlocation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_edit_location.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.views.BaseView

class EditLocationView : BaseView(), GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    lateinit var presenter: EditLocationPresenter
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        super.init(toolbarEditLocation, true)

        presenter = initPresenter(EditLocationPresenter(this)) as EditLocationPresenter

        mapViewEdit.onCreate(savedInstanceState)
        mapViewEdit.getMapAsync {
            map = it
            map.setOnMarkerDragListener(this)
            map.setOnMarkerClickListener(this)
            presenter.doConfigureMap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                presenter.doSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMarkerDragStart(marker: Marker) {}

    override fun onMarkerDrag(marker: Marker) {
        lat.text = "%.6f".format(marker.position.latitude)
        lng.text = "%.6f".format(marker.position.longitude)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, map.cameraPosition.zoom)
    }

    override fun onBackPressed() {
        presenter.doSave()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }

    override fun showLocation(latitude: Double, longitude: Double) {
        lat.text = "%.6f".format(latitude)
        lng.text = "%.6f".format(longitude)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewEdit.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewEdit.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapViewEdit.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapViewEdit.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapViewEdit.onSaveInstanceState(outState)
    }
}