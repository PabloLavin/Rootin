package com.herlavroc.proyectofinal.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Log

class LogAdapter: RecyclerView.Adapter<LogAdapter.ViewHolder>()
{
    var listLog = ArrayList<Log>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.item_rutina_seguimiento,parent,false))

    fun updateData(data: List<Log>)
    {
        listLog.clear()
        listLog.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() =  listLog.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFechaLog  = itemView.findViewById<TextView>(R.id.txtFechaSeguimiento)
        val tvActividad = itemView.findViewById<TextView>(R.id.txtActividadSeguimiento)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = listLog[position]
        holder.tvFechaLog.text = log.fecha.toString()
        holder.tvActividad.text = log.actividad
    }
}