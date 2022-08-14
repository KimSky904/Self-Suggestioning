package com.eng.selfsuggestion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.model.RoutineModel

class DailySpellAdapter() : RecyclerView.Adapter<DailySpellAdapter.ViewHolder>() {

    private var dailyList: ArrayList<RoutineModel> = ArrayList()

    // TODO : 위의 List<TestingModel> specialList 적용필요
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)

        fun bind(item: RoutineModel) {
            titleText.text = item.content
            dateText.text = item.timestamp.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    override fun getItemCount(): Int = dailyList.size

    fun setDailyList(dailys: ArrayList<RoutineModel>){
        dailyList = dailys
        notifyDataSetChanged()
    }
}