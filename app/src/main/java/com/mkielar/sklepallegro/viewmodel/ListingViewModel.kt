package com.mkielar.sklepallegro.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkielar.sklepallegro.SingleEvent
import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.model.OffersDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class OfferViewModel(private val allegroApiClient: AllegroApiClient) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val allOffersLiveData: MutableLiveData<List<OfferDTO>> = MutableLiveData()
    val offersLiveData: MediatorLiveData<List<OfferDTO>> = MediatorLiveData()

    val errorLiveData: MutableLiveData<SingleEvent<Throwable>> = MutableLiveData()

    var priceFrom: Double = 0.0
    var priceTo: Double = Double.MAX_VALUE

    init {
        fetch()

        priceFrom = 50.0
        priceTo = 1000.0

        offersLiveData.addSource(allOffersLiveData) {
            offersLiveData.value = filter(it)
        }
    }

    fun fetch() {
        compositeDisposable.add(
            allegroApiClient.getData()
                .timeout(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    allOffersLiveData.value = it.offers
                }, {
                    errorLiveData.value = SingleEvent(it)
                })
        )
    }

    private fun filter(offers: List<OfferDTO>): List<OfferDTO> {
        return offers.filter { it.price.amount.toDouble() in priceFrom..priceTo}
    }

    private fun applyFilter() {
        allOffersLiveData.value?.also {
            offersLiveData.value = filter(it)
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

}