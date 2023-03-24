package com.example.fimudroid.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.News
import com.example.fimudroid.network.retrofit
import com.example.fimudroid.util.OnItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment(), OnItemClickListener {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.news_recycler, container, false)
//        val viewModel = ViewModelProvider(
//            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
//        )[NewsViewModel::class.java]

        val recyclerView: RecyclerView = root.findViewById(R.id.news_recycler)

        lifecycleScope.launch {
            val news: List<News> = withContext(Dispatchers.IO) {
                api.getNews().data
            }
            Log.i("news", news.toString())

            val newsHeaderAdapter = ImageHeaderNewsAdapter("pute.com")
            val newsAdapter = NewsAdapter(news, this@NewsListFragment)
            recyclerView.adapter = ConcatAdapter(newsHeaderAdapter, newsAdapter)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
            root.findViewById<RecyclerView>(R.id.news_recycler).setHasFixedSize(true)
        }

        return root
    }

    override fun onItemClick(itemId: Int) {
        var bundle = Bundle()
        bundle.putInt("news_id", itemId)
        requireView().findNavController()
            .navigate(R.id.action_navigation_news_to_newsDetails, bundle)
    }
}