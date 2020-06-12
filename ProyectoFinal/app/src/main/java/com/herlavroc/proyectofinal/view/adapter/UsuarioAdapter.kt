package com.herlavroc.proyectofinal.view.adapter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Usuario

class UsuarioAdapter(val usuarioListener: UsuarioListener):RecyclerView.Adapter<UsuarioAdapter.ViewHolder>()
{
    var listUsuario = ArrayList<Usuario>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_usuario_perfil,parent,false))

    fun updateData(data: List<Usuario>)
    {
        listUsuario.clear()
        listUsuario.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() =  listUsuario.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsuarioNombreApellido = itemView.findViewById<TextView>(R.id.txtPerfil_NombreApellido)
        val tvCorreo = itemView.findViewById<TextView>(R.id.txtPerfil_Correo)
        val tvCantidadRutinas = itemView.findViewById<TextView>(R.id.txtPerfil_CantidadRutinas)
        val tvCantidadActividades = itemView.findViewById<TextView>(R.id.txtPerfil_CantidadActividades)
        val tvDiasRacha = itemView.findViewById<TextView>(R.id.txtPerfil_DiasRacha)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = listUsuario[position]
        holder.tvUsuarioNombreApellido.text = "${usuario.nombre} ${usuario.apellido}"
        holder.tvDiasRacha.text = "0"
        holder.tvCorreo.text = usuario.correo
        holder.tvCantidadRutinas.text = "0"
        holder.tvCantidadActividades.text = "0"

        holder.itemView.setOnClickListener{
            usuarioListener.onUsuarioClicked(usuario,position)
        }
    }
}

