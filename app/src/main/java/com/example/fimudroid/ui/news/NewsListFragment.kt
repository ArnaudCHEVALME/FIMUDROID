package com.example.fimudroid.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.adapter.NewsAdapter
import com.example.fimudroid.database.models.News

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.news_recycler, container, false)

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[NewsViewModel::class.java]

        val recyclerView: RecyclerView = root.findViewById(R.id.news_recycler)
        recyclerView.adapter = NewsAdapter(emptyList())

        viewModel.getAllNews().observe(viewLifecycleOwner) { news ->
            Log.i("NewsData", news.toString())
            // update UI with list of news
            recyclerView.adapter = NewsAdapter(news)
        }

        viewModel.getAllNews()
        Log.i("news",viewModel.getAllNews().toString())

        root.findViewById<RecyclerView>(R.id.news_recycler).setHasFixedSize(true)
        return root
    }

}