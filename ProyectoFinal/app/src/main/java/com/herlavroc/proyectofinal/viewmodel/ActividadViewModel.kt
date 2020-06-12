package com.herlavroc.proyectofinal.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.network.Callback
import com.herlavroc.proyectofinal.network.FirestoreService

class ActividadViewModel: ViewModel()
{
    val firestoreService = FirestoreService()
    var listActividad: MutableLiveData<List<Actividad>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getActividadesFromFirebase()
    }

    fun getActividadesFromFirebase() {
        firestoreService.getActividades(object: Callback<List<Actividad>> {
            override fun onSuccess(result: List<Actividad>?) {
                listActividad.postValue(result)
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