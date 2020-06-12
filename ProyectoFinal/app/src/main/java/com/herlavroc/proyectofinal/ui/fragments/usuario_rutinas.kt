package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.session.Session

import com.herlavroc.proyectofinal.view.adapter.RutinaAdapter
import com.herlavroc.proyectofinal.view.adapter.RutinaListener
import com.herlavroc.proyectofinal.viewmodel.RutinaViewModel
import kotlinx.android.synthetic.main.fragment_usuario_rutinas.*

class usuario_rutinas : Fragment(), RutinaListener{

    private lateinit var rutinaAdapter: RutinaAdapter
    private lateinit var viewModel: RutinaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario_rutinas, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RutinaViewModel::class.java)
        viewModel.refresh()

        rutinaAdapter = RutinaAdapter(this)

        rvRutinas.apply{
            rvRutinas.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = rutinaAdapter
        }
        observeViewModel()

        btnUsuRut_NuevaRutina.setOnClickListener{
            findNavController().navigate(R.id.action_usuario_rutinas_to_rutina_nueva)
        }
        /*
        btnUsuRut_EditarRutina.setOnClickListener{
            findNavController().navigate(R.id.action_usuario_rutinas_to_rutina_actividades)
        }*/
        btnUsuRut_Perfil.setOnClickListener { findNavController().navigate(R.id.action_usuario_rutinas_to_usuario_perfil)
        }
        /*btnUsuRut_Seguimiento.setOnClickListener { findNavController().navigate(R.id.action_usuario_rutinas_to_usuario_seguimiento) }*/

    }
    fun observeViewModel()
    {
        viewModel.listRutinas.observe(this, Observer<List<Rutina>> {rutina -> rutinaAdapter.updateData(rutina)})
        viewModel.isLoading.observe(this, Observer <Boolean>{if(it!= null) rlBaseRutinas.visibility = View.INVISIBLE})
    }

    override fun onRutinaClicked(rutina: Rutina, position: Int) {
        val bundle = bundleOf("rutina" to rutina)
        Session.rutina.nombre = rutina.nombre
        findNavController().navigate(R.id.action_usuario_rutinas_to_rutina_actividades, bundle)
    }
    override fun aSeguimiento(rutina: Rutina, position: Int) {
        val bundle = bundleOf("rutina" to rutina)
        findNavController().navigate(R.id.action_usuario_rutinas_to_usuario_seguimiento,bundle)
    }
}
