package org.wit.hillfortfinder.views.hillfortlist.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favourite_hillforts.view.*

import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.hillfortlist.HillfortAdapter
import org.wit.hillfortfinder.views.hillfortlist.HillfortListener

class FavouriteHillfortsFragment : Fragment(), HillfortListener {

  lateinit var presenter: FavouriteHillfortsPresenter
  lateinit var root: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    root = inflater.inflate(R.layout.fragment_favourite_hillforts, container, false)
    root.recyclerViewFavourite.layoutManager = LinearLayoutManager(activity)

    presenter = FavouriteHillfortsPresenter(this)
    presenter.loadHillforts()
    return root
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  fun showHillforts(hillforts: List<HillfortModel>) {
    root.recyclerViewFavourite.adapter = HillfortAdapter(hillforts, this)
    root.recyclerViewFavourite.adapter?.notifyDataSetChanged()
  }

  companion object {
    @JvmStatic
    fun newInstance() =
        FavouriteHillfortsFragment().apply {
          arguments = Bundle().apply {}
        }
  }
}
