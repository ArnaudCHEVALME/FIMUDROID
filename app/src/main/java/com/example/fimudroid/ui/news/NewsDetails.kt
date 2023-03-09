package com.example.fimudroid.ui.news

import android.graphics.text.TextRunShaper
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fimudroid.R


/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetails(
    ) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val idNews:Int = requireArguments().getInt("news_id")
        if(idNews>0){
            Log.i("IDPUTAIN", idNews.toString())
        }
        else{
            Log.i("NOID", "sad samson noises...")
        }
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.news_details_fragment, container, false)
        return root
    }

    companion object
}