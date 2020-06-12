package com.herlavroc.proyectofinal.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Usuario
import com.herlavroc.proyectofinal.network.Callback
import com.herlavroc.proyectofinal.network.FirestoreService
import com.herlavroc.proyectofinal.network.USUARIO_COLLECTION_NAME
import com.herlavroc.proyectofinal.session.Session
import kotlinx.android.synthetic.main.fragment_usuario_perfil.*
import kotlinx.android.synthetic.main.fragment_usuario_seguimiento.*
import java.lang.Exception
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 * Use the [usuario_perfil.newInstance] factory method to
 * create an instance of this fragment.
 */
class usuario_perfil : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario_perfil, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {

            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("rutinas").whereEqualTo("correousuario",Session.usuario.correo).get()
                .addOnSuccessListener { documents ->
                    txtPerfil_CantidadRutinas.text = "${documents.size().toString()} rutinas"
                } .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
                }
            firebaseFirestore.collection("actividades").whereEqualTo("correousuario",Session.usuario.correo).get()
                .addOnSuccessListener { documents ->
                    txtPerfil_CantidadActividades.text = "${ documents.size().toString()} actividades"
                        } .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
                }
            firebaseFirestore.collection("logs").whereEqualTo("correousuario",Session.usuario.correo).whereEqualTo("fecha",
                LocalDateTime.now().toLocalDate().toString()).get()
                .addOnSuccessListener { documents ->
                    txtPerfil_DiasRacha.text = "${ documents.size().toString()} actividades realizadas hoy"
                } .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
                }
            txtPerfil_NombreApellido.text = "${Session.usuario.nombre} ${Session.usuario.apellido}"
            txtPerfil_Correo.text = "${Session.usuario.correo}"

        }
        catch (e:Exception)
        {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
