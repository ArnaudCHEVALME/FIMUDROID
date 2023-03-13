package com.example.fimudroid.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.adapter.ImageHeaderNewsAdapter
import com.example.fimudroid.adapter.NewsAdapter
import com.example.fimudroid.network.models.News

interface OnItemClickListener {
    fun onItemClick(itemId: Int)
}

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment(), OnItemClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.news_recycler, container, false)
        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[NewsViewModel::class.java]

        val recyclerView: RecyclerView = root.findViewById(R.id.news_recycler)
        viewModel.getAllNews().observe(viewLifecycleOwner) { news ->
            val newsHeaderAdapter = ImageHeaderNewsAdapter("pute.com")
            val newsAdapter = NewsAdapter(news, this)

            recyclerView.adapter = ConcatAdapter(newsHeaderAdapter, newsAdapter)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
        }


        viewModel.getAllNews()
        Log.i("news", viewModel.getAllNews().toString())

        root.findViewById<RecyclerView>(R.id.news_recycler).setHasFixedSize(true)
        return root
    }

    override fun onItemClick(itemId: Int) {
//        Log.i("CLICK", itemId.toString())
        var bundle = Bundle()
        bundle.putInt("news_id", itemId)
//        Log.i("CLICK", bundle.getInt("id_news").toString())
        requireView().findNavController().navigate(R.id.action_navigation_news_to_newsDetails, bundle)
    }
}