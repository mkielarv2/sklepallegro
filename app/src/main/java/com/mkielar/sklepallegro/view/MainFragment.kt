package com.mkielar.sklepallegro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkielar.sklepallegro.R
import com.mkielar.sklepallegro.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context)

        val offerAdapter = OfferAdapter()
        recycler.adapter = offerAdapter

        swipeRefresh.isRefreshing = true

        viewModel.offersLiveData.observe(viewLifecycleOwner, Observer {
            offerAdapter.update(it)
            swipeRefresh.isRefreshing = false
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            swipeRefresh.isRefreshing = false
        })

        swipeRefresh.setOnRefreshListener {
            viewModel.fetch()
        }
    }
}
