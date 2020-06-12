package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.session.Session
import kotlinx.android.synthetic.main.fragment_rutina_nueva.*
import org.json.JSONArray
import org.json.JSONObject

class rutina_nueva : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutina_nueva, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        btnRutNew_AgregarRutina.setOnClickListener{
            try
            {
                //Robando los elementos de los controles

                var nmbr = txtRutNew_Nombre.text
                val jsonArrSpeakers = JSONArray("[{'nombre':'$nmbr','correousuario':'${Session.usuario.correo}'}]")

                // Obtener la instancia de la BD
                val firebaseFirestore = FirebaseFirestore.getInstance()

                // Recorrer el JSON desde 0 hasta el tamaño del arreglo.
                for (i in 0 until jsonArrSpeakers.length()) {
                    // Por cada JSON...
                    val aux = jsonArrSpeakers.get(i) as JSONObject
                    var rutina = Rutina()
                    rutina.nombre = aux.getString("nombre")
                    rutina.correousuario = aux.getString("correousuario")
                    //Agregar a Firestore
                    firebaseFirestore.collection("rutinas").document().set(rutina)
                }
            }
            catch (e: Exception)
            {
                //Mostrar error
                Toast.makeText(this.activity, e.toString() + "\n" + e.cause.toString() + "\n" +e.cause!!.localizedMessage,
                    Toast.LENGTH_LONG).show()
            }
            //Terminar acción
            activity!!.onBackPressed();
        }
        btnRutNew_Perfil.setOnClickListener{
            findNavController().navigate(R.id.action_rutina_nueva_to_usuario_perfil)
        }

    }
}
