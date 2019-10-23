package org.wit.hillfortfinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.main.MainApp
import org.wit.hillfortfinder.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("hillfort_edit")) {
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            hillfortDescription.setText(hillfort.description)
        }

        btnAdd.setOnClickListener {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = hillfortDescription.text.toString()
            if (hillfort.title.isNotEmpty()) {
                app.hillforts.create(hillfort.copy())
                info("Add Button Pressed: $hillfort")
                app.hillforts.logAll()
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast("Please Enter A Title")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
