package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkielar.sklepallegro.R
import com.mkielar.sklepallegro.ktx.showToast
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private val offerAdapter: OfferAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offerAdapter.onClickListener = this::navigateToDetails
        setupRecyclerView()

        showRefreshing()

        viewModel.offersLiveData.observe(viewLifecycleOwner, Observer {
            offerAdapter.offers = it
            hideRefreshing()
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            showErrorMessage()
            hideRefreshing()
        })

        swipeRefresh.setOnRefreshListener {
            reloadOffers()
        }
    }

    private fun navigateToDetails(it: OfferDTO) {
        findNavController().navigate(
            MainFragmentDirections
                .actionMainFragmentToDetailsFragment(it)
        )
    }

    private fun setupRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = offerAdapter
    }

    private fun showRefreshing() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideRefreshing() {
        swipeRefresh.isRefreshing = false
    }

    private fun showErrorMessage() {
        context?.showToast(getString(R.string.fetch_error_message))
    }

    private fun reloadOffers() {
        viewModel.fetch()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }
}
