package com.herlavroc.proyectofinal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herlavroc.proyectofinal.model.Log
import com.herlavroc.proyectofinal.network.Callback
import com.herlavroc.proyectofinal.network.FirestoreService

class LogViewModel: ViewModel()
{
    val firestoreService = FirestoreService()
    var listLog: MutableLiveData<List<Log>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getLogFromFirebase()
    }

    fun getLogFromFirebase() {
        firestoreService.getLogs(object: Callback<List<Log>> {
            override fun onSuccess(result: List<Log>?) {
                listLog.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    fun processFinished() {
        isLoading.value = true
    }
}