package com.herlavroc.proyectofinal.view.adapter

import com.herlavroc.proyectofinal.model.Rutina

interface RutinaListener {
    fun onRutinaClicked(rutina: Rutina, position: Int)
    fun aSeguimiento(rutina: Rutina, position: Int)
}