package com.mkielar.sklepallegro.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mkielar.sklepallegro.R
import com.mkielar.sklepallegro.databinding.RecyclerItemBinding
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.model.OfferItemDataBind
import com.mkielar.sklepallegro.model.OffersDTO

class OfferAdapter : RecyclerView.Adapter<OfferViewHolder>() {
    var offers: List<OfferDTO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val recyclerItemBinding: RecyclerItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_item, parent, false
            )
        return OfferViewHolder(recyclerItemBinding)
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.recyclerItemBinding.offer = OfferItemDataBind(
            offer.name,
            offer.price.amount,
            offer.price.currency,
            offer.thumbnailUrl
        )
    }

    fun update(offersDTO: OffersDTO) {
        offers = offersDTO.offers
        notifyDataSetChanged()
    }
}