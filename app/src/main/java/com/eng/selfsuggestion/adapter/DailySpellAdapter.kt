package com.eng.selfsuggestion.adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
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
import java.text.SimpleDateFormat

class DailySpellAdapter(private val context : Context?, private val activity : Activity, private val fragmentManager: FragmentManager,) : RecyclerView.Adapter<DailySpellAdapter.ViewHolder>() {

    private var dailyList: ArrayList<RoutineModel> = ArrayList()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)
        private val btnMore : ImageView = itemView.findViewById(R.id.icon_more)

        fun bind(item: RoutineModel) {
            Log.i("TAG", "bind: 데일리 어댑터 아이템 "+item)
            titleText.text = item.content
            dateText.text = SimpleDateFormat("yyyy-MM-dd").format(item.timestamp)

            btnMore.setOnClickListener {
                val adapter = SpellListFunctionDialogFragment(0)
                adapter.setDaily(item)
                val dialogFragment = adapter
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
            Log.i(TAG, "onBindViewHolder: 어댑터 카운트"+dailyList[position].count)
            intent.putExtra("docId", dailyList[position].docId)
            context!!.startActivity(intent)
            activity.overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
        }
    }

    override fun getItemCount(): Int = dailyList.size

    fun setDailyList(dailys: ArrayList<RoutineModel>){
        dailyList = dailys
        notifyDataSetChanged()
    }
}