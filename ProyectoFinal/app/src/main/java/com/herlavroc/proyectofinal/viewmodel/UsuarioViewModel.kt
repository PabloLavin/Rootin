package com.herlavroc.proyectofinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.herlavroc.proyectofinal.network.Callback
import com.herlavroc.proyectofinal.network.FirestoreService
import java.lang.Exception
import com.herlavroc.proyectofinal.model.Usuario

class UsuarioViewModel(crrlctrnc: String): ViewModel()
{
    val firestoreService = FirestoreService()
    var listUsuarios: MutableLiveData<List<Usuario>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    var correoelectronico: String =crrlctrnc

    fun refresh() {
        getUsuariosFromFirebase()
    }

    fun getUsuariosFromFirebase() {
        firestoreService.getUsuarios(object: Callback<List<Usuario>> {
            override fun onSuccess(result: List<Usuario>?) {
                listUsuarios.postValue(result)
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