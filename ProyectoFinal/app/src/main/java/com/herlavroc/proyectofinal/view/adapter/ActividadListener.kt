package com.herlavroc.proyectofinal.view.adapter

import com.herlavroc.proyectofinal.model.Actividad

interface ActividadListener {
    fun onActividadClicked(actividad: Actividad, position: Int)
    fun onActividadChecked(actividad: Actividad, position: Int)
    fun onActividadMapa(actividad: Actividad,position: Int)
    fun onActividadEliminar(actividad: Actividad,position: Int)
}