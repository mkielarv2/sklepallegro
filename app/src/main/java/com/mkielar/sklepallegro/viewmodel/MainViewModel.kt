package com.mkielar.sklepallegro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkielar.sklepallegro.api.AllegroApiClient
import com.mkielar.sklepallegro.model.OfferDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val allegroApiClient: AllegroApiClient) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val offersLiveData: MutableLiveData<List<OfferDTO>> = MutableLiveData()
    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    init {
        fetch()
    }

    fun fetch() {
        compositeDisposable.add(
            allegroApiClient.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    offersLiveData.value = it.offers
                }, {
                    errorLiveData.value = it
                })
        )
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}