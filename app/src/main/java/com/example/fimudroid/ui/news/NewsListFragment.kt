package com.example.fimudroid.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fimudroid.adapter.ActuAdapter
import com.example.fimudroid.data.DataSource
import com.example.fimudroid.databinding.FragmentNewsBinding
import com.example.fimudroid.models.Actualite

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myDataset:List<Actualite> = DataSource().LoadActu()
        binding.actuRecycler.adapter = ActuAdapter(this, myDataset)
        //binding.tv.text = DataSource().LoadActu()[0].contenu;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        @JvmStatic
        fun newInstance() =
            NewsListFragment().apply {
            }
    }
}