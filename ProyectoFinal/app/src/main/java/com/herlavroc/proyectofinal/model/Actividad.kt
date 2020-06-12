package com.herlavroc.proyectofinal.model

import java.io.Serializable
import java.sql.Time
import java.util.*

class Actividad: Serializable
{
    lateinit var nombre: String
    //lateinit var icono: String
    lateinit var descripcion: String
    lateinit var correousuario: String
     var realizada: Boolean = false
    lateinit var inicio: String
    lateinit var fin: String
    lateinit var rutina: String
     var meta: Int = 0
     var recordar: Boolean = false
}