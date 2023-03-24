package com.example.fimudroid.ui.planning

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.util.OnItemClickListener

class ConcertAdapter(
    private val concerts: List<Concert>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ConcertAdapter.ConcertViewHolder>(){

    class ConcertViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val rectangle : View = view.findViewById(R.id.rectangle)
        val nameView: TextView = view.findViewById(R.id.artiste_name_view)
        val paysView: TextView = view.findViewById(R.id.pays_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcertViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.concert_layout, parent, false)
        return ConcertViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ConcertViewHolder, position: Int) {
        val concert = concerts[position]

        holder.rectangle.setBackgroundColor(Color.parseColor("#FFC0CB"))
        holder.nameView.text = concert.artiste?.nom
        holder.paysView.text = concert.artiste?.pays?.joinToString(", ") { it.libelle }


        holder.rectangle.setOnClickListener {
            listener.onItemClick(concert.id)
        }
    }

    override fun getItemCount() = concerts.size
}