package com.example.fimudroid.ui.news

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use+ the [NewsDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetails(
    ) : Fragment() {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val news_id:Int = arguments?.getInt("news_id") ?: 0
        Log.i("LES DATA PUTAIN MERDE CHAR TU VAS LES BOUFFER TES NEWS", news_id.toString())
        val root = inflater.inflate(R.layout.news_details_fragment, container, false)
        val title:TextView = root.findViewById(R.id.actu_title)
        val core:TextView = root.findViewById(R.id.actu_core)
        val date:TextView = root.findViewById(R.id.detail_news_date)
        val bar:View = root.findViewById(R.id.side_bare_news)
        bar.setBackgroundColor(Color.parseColor("#FFF5B1"))
        title.text = "Chargement..."
        core.text = "Chargement..."
        lifecycleScope.launch(Dispatchers.IO){
            val news = api.getNewsById(news_id)
            title.text = news.titre
            core.text = news.contenu
            date.text = news.date_envoi?.subSequence(0, 10) ?: "no Date"

        }
        return root
    }

    companion object {
    }
}