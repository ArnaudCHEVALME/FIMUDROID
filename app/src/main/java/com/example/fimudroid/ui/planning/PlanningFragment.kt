package com.example.fimudroid.ui.planning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.network.retrofit
import com.example.fimudroid.util.OnItemClickListener
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanningFragment : Fragment(), OnItemClickListener {
    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    private var concerts: List<Concert> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_planning, container, false)

        val recycler: RecyclerView = root.findViewById(R.id.planning_recycler)

        lifecycleScope.launch {
            val concerts = withContext(Dispatchers.IO) {
                api.getConcerts().data
            }
            recycler.adapter = ConcertAdapter(concerts, this@PlanningFragment)
        }
        return root
    }

    override fun onItemClick(itemId: Int) {
        val bundle = Bundle()
        lifecycleScope.launch {
            val concert = api.getConcertById(itemId).data
            bundle.putInt("id_art", concert.artisteId)
            requireView().findNavController()
                .navigate(R.id.action_programmation_to_artisteDetails, bundle)
        }
    }
}