package com.eng.selfsuggestion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.view.dialog.SpellListFunctionDialogFragment
import com.eng.selfsuggestion.view.spell.InfoSpellActivity

class DailySpellAdapter(private val context : Context?, private val fragmentManager: FragmentManager,) : RecyclerView.Adapter<DailySpellAdapter.ViewHolder>() {

    private var dailyList: ArrayList<RoutineModel> = ArrayList()

    // TODO : 위의 List<TestingModel> specialList 적용필요
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)
        private val btnMore : ImageView = itemView.findViewById(R.id.icon_more)

        fun bind(item: RoutineModel) {
            titleText.text = item.content
            dateText.text = item.timestamp.toString()

            btnMore.setOnClickListener {
                val dialogFragment = SpellListFunctionDialogFragment(0)
                dialogFragment.show(fragmentManager, "SpellListFunctionDialogFragment")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dailyList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InfoSpellActivity::class.java)
            intent.putExtra("content", dailyList[position].content)
            intent.putExtra("date", dailyList[position].timestamp.toString())
            intent.putExtra("count", dailyList[position].count)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dailyList.size

    fun setDailyList(dailys: ArrayList<RoutineModel>){
        dailyList = dailys
        notifyDataSetChanged()
    }
}