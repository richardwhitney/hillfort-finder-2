package org.wit.hillfortfinder.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.models.HillfortModel

class HillfortListView: AppCompatActivity(),
    HillfortListener {

    lateinit var presenter: HillfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)

        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = HillfortListPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddHillfort()
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.item_settings -> presenter.doShowSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        doAsync {
            val hillforts = presenter.app.hillforts.findByUserId(presenter.app.currentUser?.id!!)
            uiThread {
                showHillforts(hillforts)
            }
        }
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter =
            HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}