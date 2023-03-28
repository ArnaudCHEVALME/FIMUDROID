package com.example.fimudroid.ui.planning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.util.OnItemClickListener

class PlanningAdapter(
    private val data: Map<Scene, List<Concert>>
) : RecyclerView.Adapter<PlanningAdapter.ConcertViewHolder>(){
    private var scenes:List<Scene> = data.keys.sortedBy { it.libelle }

    class ConcertViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val sceneName : TextView = view.findViewById(R.id.scene_name)
        val layout : LinearLayout = view.findViewById(R.id.scene_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcertViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.scene_recycler_item, parent, false)
        return ConcertViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ConcertViewHolder, position: Int) {
        val scene = scenes[position]

        holder.sceneName.text = scene.libelle

        val concerts = data[scene]!!
        for (concert in concerts){
            holder.layout.addView(ConcertView(holder.layout.context,concert))
        }
    }

    override fun getItemCount() = data.size
}