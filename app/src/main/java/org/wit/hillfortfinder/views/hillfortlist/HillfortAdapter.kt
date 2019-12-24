package org.wit.hillfortfinder.views.hillfortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.wit.hillfortfinder.R
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.models.HillfortModel

interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
    private val listener: HillfortListener
):
    RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_hillfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int {
        return hillforts.size
    }

    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {
            itemView.hillfortTitle.text = hillfort.title
            itemView.hillfortDescription.text = hillfort.description
            if (hillfort.visited) {
                itemView.imageVisited.setImageResource(R.drawable.ic_checked)
            }
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}