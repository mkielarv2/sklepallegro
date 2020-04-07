package com.mkielar.sklepallegro

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkielar.sklepallegro.dto.OfferDTO

class OfferViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(offerDTO: OfferDTO) {
        view.findViewById<TextView>(R.id.name).text = offerDTO.name
        view.findViewById<TextView>(R.id.price).text = "${offerDTO.price.amount} ${offerDTO.price.currency}"
        val thumbnailView = view.findViewById<ImageView>(R.id.thumbnail)
        Glide.with(view.context)
            .load(offerDTO.thumbnailUrl)
            .into(thumbnailView)
        view.findViewById<ConstraintLayout>(R.id.container).setOnClickListener {
            it.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(offerDTO)
            )
        }
    }
}