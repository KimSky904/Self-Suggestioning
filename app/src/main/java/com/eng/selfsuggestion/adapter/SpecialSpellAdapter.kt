package com.eng.selfsuggestion.adapter

import android.content.ContentValues
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
import com.eng.selfsuggestion.model.ArrivedModel
import com.eng.selfsuggestion.view.dialog.SpellListFunctionDialogFragment
import com.eng.selfsuggestion.view.spell.InfoSpecialActivity
import java.text.SimpleDateFormat

class SpecialSpellAdapter(private val context : Context?, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<SpecialSpellAdapter.ViewHolder>() {

    private var specialList: ArrayList<ArrivedModel> = ArrayList()

    // TODO : 위의 List<TestingModel> specialList 적용필요
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)
        private val btnMore : ImageView = itemView.findViewById(R.id.icon_more)

        fun bind(item : ArrivedModel) {
            titleText.text = item.content
            dateText.text = SimpleDateFormat("yyyy-MM-dd").format(item.timestamp)
            btnMore.setOnClickListener {
                val adapter = SpellListFunctionDialogFragment(1)
                adapter.setArrived(item)
                Log.i("TAG", "bind: 스페셜 어댑터 "+item)
                val dialogFragment = adapter
                dialogFragment.show(fragmentManager, "SpellListFunctionDialogFragment")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_special_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(specialList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InfoSpecialActivity::class.java)
            intent.putExtra("content", specialList[position].content)
            intent.putExtra("date", specialList[position].timestamp.toString())
            intent.putExtra("arriveday", specialList[position].arriveday)
            Log.i(ContentValues.TAG, "onBindViewHolder: 어댑터 카운트"+specialList[position].arriveday)
            intent.putExtra("docId", specialList[position].docId)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = specialList.size

    fun setSpecialList(special: ArrayList<ArrivedModel>){
        specialList = special
        notifyDataSetChanged()
    }
}