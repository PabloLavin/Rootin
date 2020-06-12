package com.herlavroc.proyectofinal.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.session.Session
import kotlinx.android.synthetic.main.fragment_rutina_nueva_actividad.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [rutina_nueva_actividad.newInstance] factory method to
 * create an instance of this fragment.
 */
class rutina_nueva_actividad : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutina_nueva_actividad, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRutNewAct_NuevaActividad.setOnClickListener{
            try
            {
                if(timePickerRutNewAct_Inicio.hour < timePickerRutNewAct_Final.hour  || (timePickerRutNewAct_Inicio.hour == timePickerRutNewAct_Final.hour  && timePickerRutNewAct_Inicio.minute < timePickerRutNewAct_Final.minute))
                {
                    var sesion = Session()
                    //Robando los elementos de los controles

                    var titl = txtRutNewAct_Titulo.text
                    var dscr = txtRutNewAct_Descripcion.text
                    var ti = timePickerRutNewAct_Inicio.hour.toString() + ":" + timePickerRutNewAct_Inicio.minute.toString()
                    var tf = timePickerRutNewAct_Final.hour.toString() + ":" + timePickerRutNewAct_Final.minute.toString()
                    var crr = Session.usuario.correo

                    val jsonArrSpeakers = JSONArray("[{'nombre':'$titl','descripcion':'$dscr','correousuario':'$crr', 'realizada':'false', 'inicio':'$ti', 'fin':'$tf','rutina':'${Session.rutina.nombre}', 'meta':'0', 'recordar':'false'}]")

                    // Obtener la instancia de la BD
                    val firebaseFirestore = FirebaseFirestore.getInstance()

                    // Recorrer el JSON desde 0 hasta el tamaño del arreglo.
                    for (i in 0 until jsonArrSpeakers.length()) {
                        // Por cada JSON...
                        val aux = jsonArrSpeakers.get(i) as JSONObject
                        var actividad = Actividad()
                        actividad.nombre = aux.getString("nombre")
                        actividad.correousuario = aux.getString("correousuario")
                        actividad.descripcion = aux.getString("descripcion")
                        actividad.realizada = aux.getBoolean("realizada")
                        actividad.inicio = aux.getString("inicio")
                        actividad.fin = aux.getString("fin")
                        actividad.rutina = aux.getString("rutina")
                        actividad.meta = aux.getInt("meta")
                        actividad.recordar = aux.getBoolean("recordar")

                        //Agregar a Firestore
                        firebaseFirestore.collection("actividades").document().set(actividad)
                        activity!!.onBackPressed();
                    }
                }
                else
                {
                    Toast.makeText(this.activity, "La hora inicial debe ser anterior a la final",
                        Toast.LENGTH_LONG).show()
                }
            }
            catch (e: Exception)
            {
                //Mostrar error
                Toast.makeText(this.activity, e.toString() + "\n" + e.cause.toString() + "\n" +e.cause!!.localizedMessage,
                    Toast.LENGTH_LONG).show()
            }
            //Terminar acción

        }
        btnRutNewAct_Perfil.setOnClickListener{
            findNavController().navigate(R.id.action_rutina_nueva_actividad_to_usuario_perfil)
        }
    }
}
