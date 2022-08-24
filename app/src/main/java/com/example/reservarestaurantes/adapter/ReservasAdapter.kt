package com.example.reservarestaurantes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservarestaurantes.databinding.BookingFilaBinding
import com.example.reservarestaurantes.model.Reservas
import com.example.reservarestaurantes.ui.home.BookingFragmentDirections

class ReservasAdapter: RecyclerView.Adapter<ReservasAdapter.ReservasViewHolder>() {
    // lista de reservas almacenadas
    private var list = emptyList<Reservas>()

    /*View Holder de la lista de reservas
    * Aqui se esta llenando los campos en la estructura de fila,
    *  con los datos de las reservas que se traen
    */
    inner class ReservasViewHolder(private val itemBinding: BookingFilaBinding)
        : RecyclerView.ViewHolder (itemBinding.root){
        fun draw(reserva: Reservas) {
            itemBinding.tvName.text = reserva.name
            itemBinding.tvPhone.text = reserva.phone1.toString()
            itemBinding.tvPhone2.text = reserva.phone2.toString()
            itemBinding.tvBookingTime.text = reserva.bookingTime.toString()
            itemBinding.tvRestaurantName.text = reserva.restaurant.name
            itemBinding.tvReservationTime.text = reserva.reservationTime

            itemBinding.vistaFila.setOnClickListener {
                val accion = BookingFragmentDirections
                    .actionBookingFragmentToUpdateBookingFragment(reserva)
                itemView.findNavController().navigate(accion)
            }
        }
    }

    //Acá se va a crear una "cajita" del reciclador...  una fila... sólo la estructura
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservasViewHolder {
        val itemBinding =
            BookingFilaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false)
        return ReservasViewHolder(itemBinding)
    }

    //Acá se va a solicitar "dibujar" una cajita, según el elemento de la lista...
    override fun onBindViewHolder(holder: ReservasViewHolder, position: Int) {
        val reservas = list[position]
        holder.draw(reservas)
    }

    // Cantidad de items en la lista
    override fun getItemCount(): Int {
        return list.size
    }

    // asignacion de datos
    fun setData(reservas: List<Reservas>) {
        list = reservas
        notifyDataSetChanged()
    }
}