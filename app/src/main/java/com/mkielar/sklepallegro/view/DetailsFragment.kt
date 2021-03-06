package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mkielar.sklepallegro.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    lateinit var dataBinding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.also {
            val offerDTO = retrieveOfferFromBundle(it)
            setActionBarTitle(offerDTO.name)
            dataBinding.offerDTO = offerDTO
        }
    }

    private fun retrieveOfferFromBundle(bundle: Bundle) =
        DetailsFragmentArgs.fromBundle(bundle).offerDTO

    private fun setActionBarTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }
}
