package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mkielar.sklepallegro.BuildConfig
import com.mkielar.sklepallegro.R
import kotlinx.android.synthetic.main.fragment_details.*
import java.nio.charset.StandardCharsets

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
            val offerDTO = retrieveOfferFromBundle(it)

            setActionBarTitle(offerDTO.name)
            loadDescription(offerDTO.description)
            loadThumbnail(offerDTO.thumbnailUrl)
        }
    }

    private fun retrieveOfferFromBundle(bundle: Bundle) =
        DetailsFragmentArgs.fromBundle(bundle).offerDTO

    private fun setActionBarTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }

    private fun loadDescription(description: String) {
        webView.loadData(description, BuildConfig.MIME_TEXT_HTML, StandardCharsets.UTF_8.name())
    }

    private fun loadThumbnail(thumbnailUrl: String) {
        Glide.with(this)
            .load(thumbnailUrl)
            .into(thumbnail)
    }
}
