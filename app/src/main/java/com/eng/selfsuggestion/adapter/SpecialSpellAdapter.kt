package com.eng.selfsuggestion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.model.TestingModel

class SpecialSpellAdapter(private val specialList: List<TestingModel>) : RecyclerView.Adapter<SpecialSpellAdapter.ViewHolder>() {

    // TODO : 위의 List<TestingModel> specialList 적용필요
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)

        fun bind(item : TestingModel) {
            titleText.text = item.title
            dateText.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_special_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(specialList[position])
    }

    override fun getItemCount(): Int = specialList.size

}