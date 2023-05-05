package com.example.fimudroid.ui.FAQs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R

interface OnItemClickListener {
    fun onItemClick(itemId: Int)
}

class FAQListFragment : Fragment(), OnItemClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.faq_recycler, container, false)
        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[FAQViewModel::class.java]

        val recyclerView: RecyclerView = root.findViewById(R.id.faq_recycler)
        viewModel.getAllFAQs().observe(viewLifecycleOwner) { faqs ->
            val faqAdapter = FAQAdapter(faqs)

            recyclerView.adapter = faqAdapter
        }

        setHasOptionsMenu(true)

        return root
    }


    override fun onItemClick(itemId: Int) {
        TODO("Not yet implemented")
    }
}


