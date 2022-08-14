package com.eng.selfsuggestion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.model.TestingModel
import com.eng.selfsuggestion.view.dialog.SpellListFunctionDialogFragment
import com.eng.selfsuggestion.view.spell.InfoSpellActivity

class SpecialSpellAdapter(private val context : Context?, private val fragmentManager: FragmentManager, private val specialList: List<TestingModel>) : RecyclerView.Adapter<SpecialSpellAdapter.ViewHolder>() {

    // TODO : 위의 List<TestingModel> specialList 적용필요
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val titleText : TextView = itemView.findViewById(R.id.txt_item_title)
        private val dateText : TextView = itemView.findViewById(R.id.txt_item_date)
        private val btnMore : ImageView = itemView.findViewById(R.id.icon_more)

        fun bind(item : TestingModel) {
            titleText.text = item.title
            dateText.text = item.date
            btnMore.setOnClickListener {
                val dialogFragment = SpellListFunctionDialogFragment(1)
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
            val intent = Intent(context, InfoSpellActivity::class.java)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = specialList.size

}