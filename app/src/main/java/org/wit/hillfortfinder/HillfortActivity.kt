package org.wit.hillfortfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        info("Hillfort Activity Started...")

        btnAdd.setOnClickListener {
            val hillforTitle = hillfortTitle.text.toString()
            if (hillforTitle.isNotEmpty()) {
                info("Add Button Pressed: $hillforTitle")
            }
            else {
                toast("Please Enter A Title")
            }
        }
    }
}
