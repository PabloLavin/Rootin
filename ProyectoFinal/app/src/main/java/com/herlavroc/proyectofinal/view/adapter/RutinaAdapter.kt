package com.herlavroc.proyectofinal.view.adapter

import kotlin.collections.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Rutina
import com.herlavroc.proyectofinal.ui.fragments.usuario_seguimiento

class RutinaAdapter(val rutinaListener: RutinaListener):RecyclerView.Adapter<RutinaAdapter.ViewHolder>()
{
    var listRutina = ArrayList<Rutina>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_usuario_rutinas,parent,false))

    fun updateData(data: List<Rutina>)
    {
        listRutina.clear()
        listRutina.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() =  listRutina.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreRutina  = itemView.findViewById<TextView>(R.id.LblUsuRut_NombreRutina)
        val btnSeguimiento = itemView.findViewById<ImageButton>(R.id.btnUsuRut_Seguimiento)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rutina = listRutina[position]
        holder.tvNombreRutina.text = rutina.nombre
        holder.btnSeguimiento.setOnClickListener {
            rutinaListener.aSeguimiento(rutina,position)
        }
        holder.itemView.setOnClickListener{
            rutinaListener.onRutinaClicked(rutina,position)

        }

    }
}