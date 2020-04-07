package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mkielar.sklepallegro.R
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.also {
            val offerDTO = DetailsFragmentArgs.fromBundle(it).offerDTO
            (activity as MainActivity).supportActionBar?.title = offerDTO.name
            webView.loadData(offerDTO.description, "text/html", "UTF-8")
            Glide.with(this)
                .load(offerDTO.thumbnailUrl)
                .into(thumbnail)
        }
    }
}
