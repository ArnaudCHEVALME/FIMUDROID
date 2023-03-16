package com.example.fimudroid.ui.news

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use+ the [NewsDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetails : Fragment() {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val news_id:Int = arguments?.getInt("news_id") ?: 0
        val root = inflater.inflate(R.layout.news_details_fragment, container, false)
        val title:TextView = root.findViewById(R.id.actu_title)
        val core:TextView = root.findViewById(R.id.actu_core)
        val date:TextView = root.findViewById(R.id.detail_news_date)
        val bar:View = root.findViewById(R.id.side_bare_news)
        val header: ImageView = root.findViewById(R.id.actu_image)

        bar.setBackgroundColor(Color.parseColor("#93CAED"))
        title.text = "Chargement..."
        core.text = "Chargement..."
        date.text = "Chargement..."
        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main) {
                val news = api.getNewsById(news_id).data
                title.text = news.titre
                core.text = news.contenu
                date.text = (news.dateEnvoi?.subSequence(0, 10).toString()+", "+news.heureEnvoi)


                val drawableResource = when (news.id) {
                    1 -> R.drawable.ma_joye
                    2 -> R.drawable.ma_pales
                    3 -> R.drawable.ma_grayssoker
                    4 -> R.drawable.ma_nastyjoe
                    5 -> R.drawable.ma_poligone
                    6 -> R.drawable.ma_romainmuller
                    7 -> R.drawable.ma_tomrochet
                    8 -> R.drawable.ma_oceya
                    9 -> R.drawable.ma_encore
                    10 -> R.drawable.ma_encore
                    else -> R.drawable.ma_cloud // Placeholder image when there is no matching drawable resource
                }

                header.setImageResource(drawableResource)


                if(news.typeactu.id == 1){

                    bar.setBackgroundColor(Color.parseColor("#93CAED"))

                    //Changer l'icon de l'actu en fonction du type d'actu
//            val resId = R.drawable.ic_notifications_black_24dp
//            holder.icon.setImageResource(resId)
                }
                else if (news.typeactu.id == 2){
                    bar.setBackgroundColor(Color.parseColor("#EEEE9B"))
//            val resId = androidx.databinding.library.baseAdapters.R.drawable.notification_icon_background
//            holder.icon.setImageResource(resId)
                }
                else{
                    bar.setBackgroundColor(Color.parseColor("#F47174"))
                }

            }
        }

//        Log.i("LES DATA PUTAIN MERDE CHAR TU VAS LES BOUFFER TES NEWS", news_id.toString())
        return root
    }

    companion object
}