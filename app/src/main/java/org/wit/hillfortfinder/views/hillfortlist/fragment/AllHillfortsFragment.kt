package org.wit.hillfortfinder.views.hillfortlist


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_all_hillforts.view.*

import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.views.BaseView


class AllHillfortsFragment : Fragment(), HillfortListener {

  lateinit var presenter: AllHillfortsPresenter
  lateinit var root: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    root = inflater.inflate(R.layout.fragment_all_hillforts, container, false)
    root.recyclerViewAll.layoutManager = LinearLayoutManager(activity)

    presenter = AllHillfortsPresenter(this)
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
    root.recyclerViewAll.adapter = HillfortAdapter(hillforts, this)
    root.recyclerViewAll.adapter?.notifyDataSetChanged()
  }

  companion object {
    @JvmStatic
    fun newInstance() =
        AllHillfortsFragment().apply {
          arguments = Bundle().apply {}
        }
  }
}
