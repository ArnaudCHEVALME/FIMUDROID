package com.fimu.app.ui.FAQs

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fimu.app.R


data class Question(val questionText: String, val answerText: String)


class FAQAdapter (
    private val dataset: List<Question>,
    ) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {
        class FAQViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val arrowButton: View = view.findViewById(R.id.arrowButton)
            val questionText: TextView = view.findViewById(R.id.questionText)
            val answerText: TextView = view.findViewById(R.id.answerText)
            val answerLayout: View = view.findViewById(R.id.answerLayout)
            val questionCard: View = view.findViewById(R.id.questionCard)
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : FAQViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.faq_recycler_item, parent, false)
        return FAQViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val question = dataset[position]

        holder.questionText.text = question.questionText
        holder.answerText.text = Html.fromHtml(question.answerText, Html.FROM_HTML_MODE_COMPACT)

        fun collapseAll() {
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
        holder.questionCard.setOnClickListener {
            collapseAll()
        }
        holder.arrowButton.setOnClickListener {
            collapseAll()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
