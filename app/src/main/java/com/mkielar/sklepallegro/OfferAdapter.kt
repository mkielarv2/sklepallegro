package com.mkielar.sklepallegro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkielar.sklepallegro.dto.OfferDTO
import com.mkielar.sklepallegro.dto.OffersDTO

class OfferAdapter : RecyclerView.Adapter<OfferViewHolder>() {
    var offers: List<OfferDTO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OfferViewHolder(inflater.inflate(R.layout.recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    fun update(offersDTO: OffersDTO) {
        offers = offersDTO.offers
        notifyDataSetChanged()
    }
}