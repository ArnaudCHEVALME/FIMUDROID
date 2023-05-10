package com.example.fimudroid.ui.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.database.models.Stand
import com.example.fimudroid.network.models.TypeStand

class MapFilterAdapter(
    private var dataset: List<TypeStand>
) :
    RecyclerView.Adapter<MapFilterAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val typeName: TextView = view.findViewById(R.id.icon_name)
        val typeIcon: ImageView = view.findViewById(R.id.icon_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_map_filter_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val typeStands = dataset[position]
        val drawableResource = when (typeStands.libelle) {
            "Bar à eau" -> R.drawable.map_eau
            "Boutique" -> R.drawable.map_boutique
            "Buvette" -> R.drawable.map_buvette
            "Entrées" -> R.drawable.map_entree
            "FIMU des Enfants" -> R.drawable.map_toilet
            "Navette" -> R.drawable.map_navette
            "Parking vélos" -> R.drawable.map_parking_velo
            "Partenaire" -> R.drawable.map_stand
            "Point Infos" -> R.drawable.map_info
            "Prévention" -> R.drawable.map_prevention
            "Secours" -> R.drawable.map_secours
            "Stand alimentaire" -> R.drawable.map_resto
            else -> {R.drawable.map_marker}
        }
        holder.typeIcon.setImageResource(drawableResource)
        holder.typeName.text = typeStands.libelle


        Log.i("ALLLLAED", "onBindViewHolder: ${typeStands.libelle}")
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateData(typeStands: List<TypeStand>) {
        this.dataset = typeStands
        notifyDataSetChanged()
    }
}


