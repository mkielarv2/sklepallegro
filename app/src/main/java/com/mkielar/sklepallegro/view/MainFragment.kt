package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkielar.sklepallegro.R
import com.mkielar.sklepallegro.ktx.setDivider
import com.mkielar.sklepallegro.ktx.showToast
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.viewmodel.ListingViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private val viewModel: ListingViewModel by viewModel()
    private val listingAdapter: ListingAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listingAdapter.onClickListener = this::navigateToDetails
        setupRecyclerView()

        showRefreshing()

        viewModel.offersLiveData.observe(viewLifecycleOwner, Observer {
            listingAdapter.updateDataSet(it)
            hideRefreshing()
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            it.consume()?.apply {
                printStackTrace()
                showErrorMessage()
                hideRefreshing()
            }
        })

        swipeRefresh.setOnRefreshListener {
            fetchOffers()
        }
    }

    private fun navigateToDetails(it: OfferDTO) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
        )
    }

    private fun setupRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = listingAdapter
        recycler.setDivider(R.drawable.recycler_item_divider)
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

    private fun fetchOffers() {
        viewModel.fetch()
    }

    override fun onStop() {
        viewModel.clear()
        super.onStop()
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }
}
