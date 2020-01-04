package org.wit.hillfortfinder.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import org.jetbrains.anko.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.BaseView

class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    lateinit var map: GoogleMap
    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        super.init(toolbarAdd, true)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        mapViewHillfort.onCreate(savedInstanceState)
        mapViewHillfort.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
        }

        hillfortVisited.setOnClickListener {
            dateVisited.text = presenter.doVisited(hillfortVisited.isChecked)
        }

        hillfortFavourited.setOnClickListener {
            presenter.doFavourited(hillfortFavourited.isChecked)
        }

        choseImage.setOnClickListener {
            presenter.doSelectImage()
        }

        hillfortLocation.setOnClickListener {
            presenter.doSetLocation()
        }
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        hillfortDiscription.setText(hillfort.description)
        hillfortVisited.isChecked = hillfort.visited
        hillfortFavourited.isChecked = hillfort.favourited
        dateVisited.text = hillfort.dateVisited
        additionalNotes.setText(hillfort.additionalNotes)
        ratingBar.rating = hillfort.rating
        hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        if (hillfort.image != null) {
            choseImage.setText(R.string.change_hillfort_image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        var deleteItem: MenuItem = menu?.findItem(R.id.item_delete)!!
        deleteItem.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                }
                else {
                    presenter.doAddOrSave(hillfortTitle.text.toString(), hillfortDiscription.text.toString(),
                        additionalNotes.text.toString(), dateVisited.text.toString(), ratingBar.rating)
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewHillfort.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewHillfort.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapViewHillfort.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapViewHillfort.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapViewHillfort.onSaveInstanceState(outState)
    }
}
