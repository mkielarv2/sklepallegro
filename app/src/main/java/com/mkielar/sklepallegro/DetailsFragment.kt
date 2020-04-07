package com.mkielar.sklepallegro

import android.content.ContentResolver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            arguments?.also {
                val offerDTO = DetailsFragmentArgs.fromBundle(it).offer
                (activity as MainActivity).supportActionBar?.title = offerDTO.name
                webView.loadData(offerDTO.description, "text/html", "UTF-8")
                Glide.with(this)
                    .load(offerDTO.thumbnailUrl)
                    .into(thumbnail)
            }

    }
}
