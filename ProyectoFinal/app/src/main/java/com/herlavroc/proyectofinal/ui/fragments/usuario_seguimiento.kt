package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Log
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.view.adapter.LogAdapter
import com.herlavroc.proyectofinal.viewmodel.LogViewModel
import kotlinx.android.synthetic.main.fragment_usuario_seguimiento.*

/**
 * A simple [Fragment] subclass.
 * Use the [usuario_seguimiento.newInstance] factory method to
 * create an instance of this fragment.
 */
class usuario_seguimiento : Fragment() {

    private lateinit var logAdapter: LogAdapter
    private lateinit var viewModel: LogViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario_seguimiento, container, false)
    }

    fun observeViewModel()
    {
        viewModel.listLog.observe(this, Observer<List<Log>> { log -> logAdapter.updateData(log)})
        viewModel.isLoading.observe(this, Observer <Boolean>{if(it!= null) rlBaseLogs.visibility = View.INVISIBLE})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LogViewModel::class.java)
        viewModel.refresh()

        logAdapter = LogAdapter()

        rvLogs.apply{
            rvLogs.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = logAdapter
        }
        observeViewModel()


        val rutina = arguments?.getSerializable("rutina") as Rutina
        txtSeg_NombreRutina.text = rutina.nombre
        txtSeg_DiasRacha.text = ""

        btnUsuSeg_Perfil.setOnClickListener{
            findNavController().navigate(R.id.action_usuario_seguimiento_to_usuario_perfil)
        }

    }
}
