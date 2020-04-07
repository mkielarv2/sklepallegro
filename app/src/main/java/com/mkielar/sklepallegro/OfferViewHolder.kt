package com.mkielar.sklepallegro

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mkielar.sklepallegro.dto.OfferDTO

class OfferViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(offerDTO: OfferDTO) {
        view.findViewById<TextView>(R.id.mainText).text = offerDTO.name
        view.findViewById<ConstraintLayout>(R.id.container).setOnClickListener {
            it.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(offerDTO)
            )
        }
    }
}