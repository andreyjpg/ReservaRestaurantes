package com.example.reservarestaurantes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservarestaurantes.databinding.RestaurantFilaBinding
import com.example.reservarestaurantes.model.Restaurant
import com.example.reservarestaurantes.ui.home.RestaurantFragmentDirections

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private var lista = emptyList<Restaurant>()

    inner class RestaurantViewHolder(private val itemBinding: RestaurantFilaBinding)
        : RecyclerView.ViewHolder (itemBinding.root){
        fun draw(restaurant: Restaurant) {
            itemBinding.tvName.text = restaurant.name
            itemBinding.tvPhone.text = restaurant.phone1.toString()
            itemBinding.tvAddress.text = restaurant.Address
            itemBinding.tvPrice.text = restaurant.priceRange
            Glide.with(itemView)
                .load(restaurant.googleAddress)
                .into(itemBinding.imagen);

           itemBinding.vistaFila.setOnClickListener {
                val accion = RestaurantFragmentDirections
                    .actionNavRestaurantToUpdateRestaurantFragment(restaurant)
                itemView.findNavController().navigate(accion)
            }
        }
    }

    //Acá se va a crear una "cajita" del reciclador...  una fila... sólo la estructura
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemBinding =
            RestaurantFilaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false)
        return RestaurantViewHolder(itemBinding)
    }

    //Acá se va a solicitar "dibujar" una cajita, según el elemento de la lista...
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = lista[position]
        holder.draw(restaurant)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(restaurants: List<Restaurant>) {
        lista = restaurants
        notifyDataSetChanged()
    }
}
