package org.wit.hillfortfinder.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.BaseView
import org.wit.hillfortfinder.views.hillfortlist.fragment.AllHillfortsFragment
import org.wit.hillfortfinder.views.hillfortlist.fragment.FavouriteHillfortsFragment

class HillfortListView: BaseView(),
    HillfortListener {

    lateinit var presenter: HillfortListPresenter
    lateinit var ft: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)

        init(toolbar, false)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.hillfortListBottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

        ft = supportFragmentManager.beginTransaction()
        val fragment = AllHillfortsFragment.newInstance()
        ft.replace(R.id.container, fragment)
        ft.commit()
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
        supportFragmentManager.fragments.last()?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_all -> {
                presenter.navigateToFrag(AllHillfortsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourite -> {
                presenter.navigateToFrag(FavouriteHillfortsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}