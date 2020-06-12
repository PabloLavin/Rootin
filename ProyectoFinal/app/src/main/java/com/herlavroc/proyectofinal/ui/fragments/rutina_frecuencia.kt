package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Actividad
import kotlinx.android.synthetic.main.fragment_rutina_frecuencia.*


/**
 * A simple [Fragment] subclass.
 * Use the [rutina_frecuencia.newInstance] factory method to
 * create an instance of this fragment.
 */
class rutina_frecuencia : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutina_frecuencia, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val actividad = arguments?.getSerializable("actividad") as Actividad
        txtFrecuencia_Config.text = actividad.nombre*/
        /*btnFrecuencia_Guardar.setOnClickListener{
            activity!!.onBackPressed();
        }
        btnFrecuencia_Perfil.setOnClickListener{
            findNavController().navigate(R.id.action_rutina_frecuencia_to_usuario_perfil)
        }*/

    }
}
