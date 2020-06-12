package com.herlavroc.proyectofinal.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.herlavroc.proyectofinal.model.Usuario
import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.model.Log
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.session.Session

const val USUARIO_COLLECTION_NAME  = "usuarios"
const val LOG_COLLECTION_NAME  = "logs"
const val ACTIVIDAD_COLLECTION_NAME  = "actividades"
const val RUTINAS_COLLECTION_NAME  = "rutinas"

class FirestoreService {
    val firebaseFirestore = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init {
        firebaseFirestore.firestoreSettings = settings
    }



    fun getUsuarios(callback: Callback<List<Usuario>>) {
        firebaseFirestore.collection(USUARIO_COLLECTION_NAME)
            .orderBy("correo")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val list = result.toObjects(Usuario::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

    fun getLogs(callback: Callback<List<Log>>) {
        firebaseFirestore.collection(LOG_COLLECTION_NAME)
            .orderBy("fecha")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val list = result.toObjects(Log::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

    fun getRutinas(callback: Callback<List<Rutina>>) {
        firebaseFirestore.collection(RUTINAS_COLLECTION_NAME)
            .whereEqualTo("correousuario", Session.usuario.correo)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val list = result.toObjects(Rutina::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

    fun getActividades(callback: Callback<List<Actividad>>) {
        firebaseFirestore.collection(ACTIVIDAD_COLLECTION_NAME)
            .whereEqualTo("correousuario", Session.usuario.correo).
                whereEqualTo("rutina",Session.rutina.nombre)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    val list = result.toObjects(Actividad::class.java)
                    callback.onSuccess(list)
                    break
                }
            }
    }

}

