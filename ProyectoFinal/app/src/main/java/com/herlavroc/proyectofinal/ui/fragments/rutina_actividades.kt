package com.herlavroc.proyectofinal.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.model.Log
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.session.Session
import com.herlavroc.proyectofinal.view.adapter.ActividadAdapter
import com.herlavroc.proyectofinal.view.adapter.ActividadListener
import com.herlavroc.proyectofinal.viewmodel.ActividadViewModel
import kotlinx.android.synthetic.main.fragment_rutina_actividades.*
import kotlinx.android.synthetic.main.item_rutina_actividades.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.*

class rutina_actividades : Fragment(), ActividadListener {

    private lateinit var actividadAdapter: ActividadAdapter
    private lateinit var viewModel: ActividadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutina_actividades, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rutina = arguments?.getSerializable("rutina") as Rutina
        txtRutAct_Rutina.text = rutina.nombre
        viewModel = ViewModelProviders.of(this).get(ActividadViewModel::class.java)
        viewModel.refresh()

        actividadAdapter = ActividadAdapter(this)

        rvActividades.apply {
            rvActividades.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = actividadAdapter
        }
        observeViewModel()


        btnRutAct_Agregar.setOnClickListener {
            findNavController().navigate(R.id.action_rutina_actividades_to_rutina_nueva_actividad)
        }
        /*btnRutAct_Modificar.setOnClickListener{
            findNavController().navigate(R.id.action_rutina_actividades_to_editar_rutina)
        }*/
        btnRutAct_Perfil.setOnClickListener {
            findNavController().navigate(R.id.action_rutina_actividades_to_usuario_perfil)
        }
        /*btnRutAct_Frecuencia.setOnClickListener {
            findNavController().navigate(R.id.action_rutina_actividades_to_rutina_frecuencia)
        }*/
        btnRutAct_Eliminar.setOnClickListener {
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("rutinas").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (Session.rutina.correousuario == document.getString("correousuario")) {
                            if (Session.rutina.nombre == document.getString("nombre")) {
                                firebaseFirestore.collection("actividades")
                                    .whereEqualTo("rutina", Session.rutina.nombre)
                                    .whereEqualTo("correousuario", Session.rutina.correousuario)
                                    .get().addOnSuccessListener { documentXs ->
                                    for (documentx in documentXs) {

                                        firebaseFirestore.collection("actividades")
                                            .document(documentx.id)
                                            .delete()
                                    }
                                }.addOnFailureListener { exception ->
                                    Toast.makeText(
                                        activity,
                                        exception.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                firebaseFirestore.collection("rutinas").document(document.id)
                                    .delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            activity,
                                            "${Session.rutina.nombre} y sus actividades, eliminadas.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        activity!!.onBackPressed();
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            activity,
                                            "${Session.rutina.nombre} no pudo eliminarse.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }

    fun observeViewModel() {
        viewModel.listActividad.observe(
            this,
            Observer<List<Actividad>> { actividad -> actividadAdapter.updateData(actividad) })
        viewModel.isLoading.observe(
            this,
            Observer<Boolean> { if (it != null) rlBaseActividades.visibility = View.INVISIBLE })
    }

    override fun onActividadClicked(actividad: Actividad, position: Int) {
        try {
            val bundle = bundleOf("actividad" to actividad)

            /*findNavController().navigate(R.id.action_rutina_actividades_to_rutina_frecuencia, bundle)*/
        } catch (e: Exception) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onActividadEliminar(actividad: Actividad, position: Int) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("actividades")
            .whereEqualTo("rutina", Session.rutina.nombre)
            .whereEqualTo("correousuario", Session.rutina.correousuario)
            .get().addOnSuccessListener { documentXs ->
                for (documentx in documentXs) {
                    if(documentx.getString("nombre") == actividad.nombre)
                        firebaseFirestore.collection("actividades")
                            .document(documentx.id)
                            .delete()
                }
                Toast.makeText(
                    activity,
                    "${actividad.nombre} eliminada.",
                    Toast.LENGTH_LONG
                ).show()
                activity!!.recreate()
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    activity,
                    exception.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActividadChecked(actividad: Actividad, position: Int) {

        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("actividades").whereEqualTo("nombre", actividad.nombre)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (actividad.correousuario == document.getString("correousuario")) {
                        if (actividad.rutina == document.getString("rutina")) {
                            firebaseFirestore.collection("actividades").document(document.id)
                                .update("realizada", actividad.realizada)
                            if (actividad.realizada)
                            {
                                Toast.makeText(
                                    activity,
                                    "¡${actividad.nombre.trim()} se ha realizado hoy!",
                                    Toast.LENGTH_LONG
                                ).show()
                                try {
                                    //SE DIO DE ALTA EL LOG
                                    val fecha = LocalDateTime.now().toLocalDate().toString()
                                    val jsonArrSpeakers = JSONArray("[{'actividad':'${actividad.nombre}','correousuario':'${Session.usuario.correo}', 'rutina':'${actividad.rutina}','fecha':'${fecha}'}]")
                                    // Obtener la instancia de la BD

                                    // Recorrer el JSON desde 0 hasta el tamaño del arreglo.
                                    for (i in 0 until jsonArrSpeakers.length()) {
                                        // Por cada JSON...
                                        val aux = jsonArrSpeakers.get(i) as JSONObject
                                        var log = Log()
                                        log.actividad = aux.getString("actividad")
                                        log.correousuario = aux.getString("correousuario")
                                        log.rutina = aux.getString("rutina")
                                        log.fecha = aux.getString("fecha")
                                        //Agregar a Firestore
                                        firebaseFirestore.collection("logs").document().set(log)
                                    }
                                }
                                catch (e:Exception)
                                {
                                    Toast.makeText(
                                        activity,
                                        e.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }
                            else {
                                Toast.makeText(
                                    activity,
                                    "${actividad.nombre.trim()} se ha descartado para hoy",
                                    Toast.LENGTH_LONG
                                ).show()
                                firebaseFirestore.collection("logs")
                                    .whereEqualTo("rutina", Session.rutina.nombre)
                                    .whereEqualTo("correousuario", Session.rutina.correousuario)
                                    .whereEqualTo("actividad", actividad.nombre)
                                    .get().addOnSuccessListener { documentXs ->
                                        for (documentx in documentXs) {
                                            if(documentx.getString("fecha") == LocalDateTime.now().toLocalDate().toString())
                                                firebaseFirestore.collection("logs")
                                                    .document(documentx.id)
                                                    .delete()
                                        }
                                    }.addOnFailureListener { exception ->
                                        Toast.makeText(
                                            activity,
                                            exception.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
            }

    }

    override fun onActividadMapa(actividad: Actividad, position: Int) {
        Session.actividad = actividad
        findNavController().navigate(R.id.action_rutina_actividades_to_actividad_mapa)
    }


}
