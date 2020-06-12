package com.herlavroc.proyectofinal.view.adapter

import com.herlavroc.proyectofinal.model.Usuario
interface UsuarioListener
{
    fun onUsuarioClicked(usuario: Usuario, position: Int)
}