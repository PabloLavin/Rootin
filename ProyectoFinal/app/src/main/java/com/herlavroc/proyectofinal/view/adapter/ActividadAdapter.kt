package com.herlavroc.proyectofinal.view.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.model.Actividad
import com.herlavroc.proyectofinal.session.Session
import org.w3c.dom.Text

class ActividadAdapter(val actividadListener: ActividadListener): RecyclerView.Adapter<ActividadAdapter.ViewHolder>()
{
    var listActividad = ArrayList<Actividad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_rutina_actividades,parent,false))

    fun updateData(data: List<Actividad>)
    {
        listActividad.clear()
        listActividad.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() =  listActividad.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvRealizada  = itemView.findViewById<CheckBox>(R.id.chkRutAct_Realizada)
            val tvHoraInicio  = itemView.findViewById<TextView>(R.id.txtRutAct_HoraInicio)
            val tvHoraFinal  = itemView.findViewById<TextView>(R.id.txtRutAct_HoraFinal)
            val tvNombre  = itemView.findViewById<TextView>(R.id.txtRutAct_NombreActividad)
        val tvMapa = itemView.findViewById<ImageButton>(R.id.btnRutAct_Mapa)
        val tvEliminar = itemView.findViewById<ImageButton>(R.id.btnRutAct_EliminarAct)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actividad = listActividad[position]
        holder.tvNombre.text = actividad.nombre
        holder.tvHoraInicio.text = actividad.inicio
        holder.tvHoraFinal.text = actividad.fin
        holder.tvRealizada.isChecked = actividad.realizada
        //Session.actividad = actividad ESTO NO SUCEDÍA CADA QUE SE EJECUTAN LOS LISTENERS!!!!

        holder.tvRealizada.setOnClickListener{
            Session.actividad = actividad
            actividad.realizada = holder.tvRealizada.isChecked
            actividadListener.onActividadChecked(actividad,position)

        }
        holder.tvEliminar.setOnClickListener {
            Session.actividad = actividad
            actividadListener.onActividadEliminar(actividad,position)
        }
        holder.tvMapa.setOnClickListener {
            Session.actividad = actividad
            actividadListener.onActividadMapa(actividad,position)
        }
        holder.itemView.setOnClickListener{
            Session.actividad = actividad
            actividadListener.onActividadClicked(actividad,position)

        }

    }
}