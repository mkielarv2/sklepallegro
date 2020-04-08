package com.mkielar.sklepallegro.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mkielar.sklepallegro.R
import com.mkielar.sklepallegro.databinding.RecyclerItemBinding
import com.mkielar.sklepallegro.model.OfferBindingHelper
import com.mkielar.sklepallegro.model.OfferDTO

class OfferAdapter : RecyclerView.Adapter<OfferViewHolder>() {
    lateinit var onClickListener: (OfferDTO) -> Unit

    var offers: List<OfferDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val recyclerItemBinding: RecyclerItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_item, parent, false
            )
        return OfferViewHolder(recyclerItemBinding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.recyclerItemBinding.helper = OfferBindingHelper(offer, onClickListener)
    }

    override fun getItemCount(): Int {
        return offers.size
    }


}