package com.example.convertmate.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertmate.data.network.dto.ConvertDto
import com.example.convertmate.data.network.dto.CurrencyDto
import com.example.convertmate.domain.Resource
import com.example.convertmate.domain.repository.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseViewModel(private val rep: MainRepo) : ViewModel()  {

    private val _convertedData = MutableLiveData<Resource<ConvertDto>>()
    private val _currencyData = MutableLiveData<Resource<CurrencyDto>>()
    val convertedData  =  _convertedData
    val currencyData  =  _currencyData
    val convertedRate = MutableLiveData<Double?>()
    val currencies = MutableLiveData<Collection<String>?>()

    fun getConvertedData(accessKey: String, from: String, to: String, amount: Double) {
        viewModelScope.launch(Dispatchers.Main) {
            rep.getConvertedData(accessKey, from, to, amount).collect {
                convertedData.value = it
            }
        }
    }

    fun getCurrencies(accessKey: String) {
        viewModelScope.launch {
            rep.getCurrencies(accessKey).collect {
                currencyData.value = it
                Log.d("ChooseVM", "${currencyData.value}")
            }
        }
    }

    fun clearCurrencies() {
        currencies.value = null
    }

    fun clearConvertedRate() {
        convertedRate.value = null
        convertedData.value = Resource(Resource.Status.LOADING, null, null)
    }
}