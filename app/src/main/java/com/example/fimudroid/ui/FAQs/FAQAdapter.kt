package com.example.fimudroid.ui.FAQs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.ui.news.NewsAdapter


data class Question(val questionText: String, val answerText: String)


class FAQAdapter (

    private val dataset: List<Question>,
    private val listener: OnItemClickListener
    ) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {
        class FAQViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val arrowButton: View = view.findViewById(R.id.arrowButton)
            val questionText: TextView = view.findViewById(R.id.questionText)
            val answerText: TextView = view.findViewById(R.id.answerText)
            val answerLayout: View = view.findViewById(R.id.answerLayout)
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FAQViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.faq_recycler_item, parent, false)
        return FAQViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val question = dataset[position]

        holder.questionText.text = question.questionText
        holder.answerText.text = question.answerText

        holder.questionText.setOnClickListener {
            if (holder.answerLayout.visibility == View.GONE) {
                // Expand the answer layout
                holder.answerLayout.visibility = View.VISIBLE
                holder.arrowButton.rotation = 180f
            } else {
                // Collapse the answer layout
                holder.answerLayout.visibility = View.GONE
                holder.arrowButton.rotation = 0f
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
