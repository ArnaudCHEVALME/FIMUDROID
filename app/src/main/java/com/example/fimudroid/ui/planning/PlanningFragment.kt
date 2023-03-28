package com.example.fimudroid.ui.planning

import PlanningViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
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
class PlanningFragment : Fragment() {
    private val viewModel: PlanningViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadConcerts()
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_planning, container, false)

        val recycler: RecyclerView = root.findViewById(R.id.planning_recycler)

        lifecycleScope.launch {

            val concertsByScene = if (viewModel.concertsByDateByScene.isEmpty()) {
                emptyMap()
            } else {
                viewModel.concertsByDateByScene.values.first()
            }

            Log.i("ViewModel", viewModel.concertsByDateByScene.toString())

            // Set up the adapter with the concerts data
            recycler.adapter = PlanningAdapter(concertsByScene) // TODO - REMOVE (key depending on button) !!!
        }
        return root
    }
}