package com.herlavroc.proyectofinal.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.herlavroc.proyectofinal.network.Callback
import com.herlavroc.proyectofinal.network.FirestoreService
import java.lang.Exception
import com.herlavroc.proyectofinal.model.Rutina

class RutinaViewModel: ViewModel()
{
    val firestoreService = FirestoreService()
    var listRutinas: MutableLiveData<List<Rutina>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getRutinasFromFirebase()
    }

    fun getRutinasFromFirebase() {
        firestoreService.getRutinas(object: Callback<List<Rutina>> {
            override fun onSuccess(result: List<Rutina>?) {
                listRutinas.postValue(result)
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