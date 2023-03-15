package com.example.fimudroid.ui.news

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.database.models.News

class NewsAdapter(
//    private val context: NewsListFragment,
    private val dataset: List<News>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.ActuViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
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
        val item = dataset[position]
        Log.i("DATA", dataset.toString())
        holder.title.text = item.titre
        holder.core.text = (item.contenu?.subSequence(0, item.contenu.length/3).toString())+"..." ?: "no Text"
        holder.date.text = item.date_envoi?.subSequence(0, 10) ?: "no Date"
        holder.sidebar.setBackgroundColor(Color.parseColor("#FFF5B1"))


        holder.itemView.setOnClickListener{
            listener.onItemClick(item.id)
        }


//        if(item.type_news.libelle == "Important"){
//            //Changer l'icon de l'actu en fonction du type d'actu
//            val resId = R.drawable.ic_notifications_black_24dp
//            holder.icon.setImageResource(resId)
//        //            holder.truc.setBackgroundColor(getRessources().getColor(R.color.purple_700));
//
//        }
//        else if (item.type_news.libelle == "Info"){
//            val resId = androidx.databinding.library.baseAdapters.R.drawable.notification_icon_background
//            holder.icon.setImageResource(resId)
//        }
//        else{
//            holder.card.setCardBackgroundColor()
//        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}