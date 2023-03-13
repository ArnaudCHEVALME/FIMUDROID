package com.example.fimudroid.ui.news

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use+ the [NewsDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetails(
    ) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[NewsViewModel::class.java]

        val news_id:Int = arguments?.getInt("news_id") ?: -1
        Log.i("LES DATA PUTAIN MERDE CHAR TU VAS LES BOUFFER TES NEWS", news_id.toString())
//        Toast.makeText(context, "id news: ${news.value.titre}", Toast.LENGTH_LONG).show()
        val root = inflater.inflate(R.layout.news_details_fragment, container, false)
        lifecycleScope.launch(Dispatchers.IO){
            val news = viewModel.getById(news_id)
            Log.i("VALUES", news.toString())
            val title:TextView = root.findViewById(R.id.actu_title)
            val core:TextView = root.findViewById(R.id.actu_core)
            title.text = news[0].titre
            core.text = news[0].contenu
        }
        return root
    }

    companion object {
    }
}