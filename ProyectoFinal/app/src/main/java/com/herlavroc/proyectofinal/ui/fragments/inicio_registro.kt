package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.encrypt.Encriptador
import com.herlavroc.proyectofinal.model.Usuario
import kotlinx.android.synthetic.main.fragment_inicio_login.*
import kotlinx.android.synthetic.main.fragment_inicio_registro.*
import org.json.JSONArray
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [registro.newInstance] factory method to
 * create an instance of this fragment.
 */
class registro : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        FirebaseApp.initializeApp(this.context!!);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegistro_Registrar.setOnClickListener {
            if (txtRegistro_Correo.text.contains('@') && txtRegistro_Correo.text.contains('.')) {
                try {
                    //Robando los elementos de los controles

                    var nmbr = txtRegistro_Nombre.text
                    var aplld = txtRegistro_Apellido.text
                    var crr = txtRegistro_Correo.text


                    var cntrsn = txtRegistro_Contra.text


                    var tlfn = txtRegistro_Telefono.text
                    val jsonArrSpeakers =
                        JSONArray("[{'nombre':'$nmbr','apellido':'$aplld','correo':'$crr','contrasenia':'$cntrsn','telefono':'$tlfn'}]")

                    // Obtener la instancia de la BD
                    val firebaseFirestore = FirebaseFirestore.getInstance()

                    // Recorrer el JSON desde 0 hasta el tamaño del arreglo.
                    for (i in 0 until jsonArrSpeakers.length()) {
                        // Por cada JSON...
                        val aux = jsonArrSpeakers.get(i) as JSONObject
                        var usuario = Usuario()
                        usuario.nombre = aux.getString("nombre")
                        usuario.apellido = aux.getString("apellido")
                        usuario.correo = aux.getString("correo")
                        usuario.contrasenia = aux.getString("contrasenia")
                        usuario.telefono = aux.getString("telefono")
                        //Agregar a Firestore
                        firebaseFirestore.collection("usuarios").document().set(usuario)
                        activity!!.onBackPressed();
                    }
                } catch (e: Exception) {
                    //Mostrar error
                    Toast.makeText(
                        this.activity,
                        e.toString() + "\n" + e.cause.toString() + "\n" + e.cause!!.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    this.activity, "Correo electrónico no válido",
                    Toast.LENGTH_LONG
                ).show()
            }
            //Terminar acción

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_registro, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment registro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
