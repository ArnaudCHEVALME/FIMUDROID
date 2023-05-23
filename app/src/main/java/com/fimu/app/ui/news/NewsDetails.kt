package com.fimu.app.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fimu.app.R
import com.fimu.app.network.FimuApiService
import com.fimu.app.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Month


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
        val header: ImageView = root.findViewById(R.id.actu_image)

        title.text = "Chargement..."
        core.text = "Chargement..."
        date.text = "Chargement..."
        lifecycleScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main) {
                val news = api.getNewsById(news_id).data
                title.text = news.titre
                core.text = news.contenu

                val jour = news.dateEnvoi!!.split("-")[2].toInt()
                val mois = getMonth(Month.of(news.dateEnvoi.split("-")[1].toInt()))
                // heure + min
                val heure = news.heureEnvoi

                date.text = "Publié le $jour $mois à $heure"

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

            }
        }

//        Log.i("LES DATA PUTAIN MERDE CHAR TU VAS LES BOUFFER TES NEWS", news_id.toString())
        return root
    }

    private fun getMonth(month: Month) : String{
        return when (month) {
            Month.JANUARY -> "Janvier"
            Month.FEBRUARY -> "Février"
            Month.MARCH-> "Mars"
            Month.APRIL -> "Avril"
            Month.MAY -> "Mai"
            Month.JUNE -> "Juin"
            Month.JULY -> "Juillet"
            Month.AUGUST -> "Août"
            Month.SEPTEMBER -> "Séptembre"
            Month.OCTOBER -> "Octobre"
            Month.NOVEMBER -> "Novembre"
            else -> "Décembre"
        }
    }

    companion object
}

