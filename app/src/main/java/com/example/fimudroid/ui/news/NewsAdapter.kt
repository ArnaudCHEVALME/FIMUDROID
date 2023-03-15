package com.example.fimudroid.ui.news

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.models.News

class NewsAdapter(
//    private val context: NewsListFragment,
    private val dataset: List<News>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.ActuViewHolder>() {
    // Provide a reference to the views for each data actu
    // Complex data items may need more than one view per actu, and
    // you provide access to all the views for a data actu in a view holder.
    // Each data actu is just an Affirmation object.
    class ActuViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.actu_title)
        val sidebar: View = view.findViewById(R.id.side_bare_news)
        val core: TextView = view.findViewById(R.id.news_core)
        val date: TextView = view.findViewById(R.id.news_date)
    }


    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActuViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_recycler_item, parent, false)
        return ActuViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ActuViewHolder, position: Int) {
        val actu = dataset[position]
        Log.i("DATA", dataset.toString())
        holder.title.text = actu.titre
        holder.core.text =
            ((actu.contenu?.subSequence(0, actu.contenu.length / 3).toString()) + "...")
                ?: "no Text"
        holder.date.text = actu.dateEnvoi?.subSequence(0, 10) ?: "no Date"


        holder.itemView.setOnClickListener{
            listener.onItemClick(actu.id)
        }


        if(actu.typeactu.id == 0){

            holder.sidebar.setBackgroundColor(Color.parseColor("#F47174"))

            //Changer l'icon de l'actu en fonction du type d'actu
//            val resId = R.drawable.ic_notifications_black_24dp
//            holder.icon.setImageResource(resId)
        }
        else if (actu.typeactu.id == 1){
            holder.sidebar.setBackgroundColor(Color.parseColor("#EEEE9B"))
//            val resId = androidx.databinding.library.baseAdapters.R.drawable.notification_icon_background
//            holder.icon.setImageResource(resId)
        }
        else{
            holder.sidebar.setBackgroundColor(Color.parseColor("#93CAED"))
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}