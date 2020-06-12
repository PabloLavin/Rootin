package com.herlavroc.proyectofinal.session

import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.model.Usuario

class Session
{
    companion object{
        var rutina: Rutina = Rutina()
        var actividad: Actividad = Actividad()
        var usuario: Usuario = Usuario()
    }
}