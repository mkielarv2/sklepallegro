package com.mkielar.sklepallegro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    private val allegroApiClient by lazy {
        AllegroApiClient.create()
    }

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
        recycler.adapter = OfferAdapter()

        swipeRefresh.isRefreshing = true
        val disposable = allegroApiClient.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (recycler.adapter as OfferAdapter).update(it)
                swipeRefresh.isRefreshing = false
            }, {
                it.printStackTrace()
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
            })


        swipeRefresh.setOnRefreshListener {
            val disposable = allegroApiClient.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (recycler.adapter as OfferAdapter).update(it)
                    swipeRefresh.isRefreshing = false
                }, {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    swipeRefresh.isRefreshing = false
                })

        }
    }
}
