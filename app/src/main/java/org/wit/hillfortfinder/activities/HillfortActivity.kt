package org.wit.hillfortfinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        info("Hillfort Activity Started...")

        btnAdd.setOnClickListener {
            hillfort.title = hillfortTitle.text.toString()
            if (hillfort.title.isNotEmpty()) {
                info("Add Button Pressed: $hillfort")
            }
            else {
                toast("Please Enter A Title")
            }
        }
    }
}
