package org.wit.hillfortfinder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import kotlinx.android.synthetic.main.card_hillfort.*
import org.jetbrains.anko.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.helpers.readImage
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.helpers.showImagePicker
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.Location
import java.text.SimpleDateFormat
import java.util.*

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = HillfortPresenter(this)

        btnAdd.setOnClickListener {
            if (hillfortTitle.text.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            }
            else {
                presenter.doAddOrSave(hillfortTitle.text.toString(), hillfortDiscription.text.toString(),
                    additionalNotes.text.toString(), dateVisited.text.toString(), ratingBar.rating )
            }
        }

        hillfortVisited.setOnClickListener {
            dateVisited.text = presenter.doVisited(hillfortVisited.isChecked)
        }

        choseImage.setOnClickListener {
            presenter.doSelectImage()
        }

        hillfortLocation.setOnClickListener {
            presenter.doSetLocation()
        }
    }

    fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        hillfortDiscription.setText(hillfort.title)
        hillfortVisited.isChecked = hillfort.visited
        dateVisited.text = hillfort.dateVisited
        additionalNotes.setText(hillfort.additionalNotes)
        ratingBar.rating = hillfort.rating
        hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        btnAdd.setText(R.string.save_hillfort)
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
            R.id.item_cancel -> {
                presenter.doCancel()
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

}
