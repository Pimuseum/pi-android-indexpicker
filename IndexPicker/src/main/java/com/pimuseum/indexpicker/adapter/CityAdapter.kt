package com.pimuseum.indexpicker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pimuseum.indexpicker.data.Initial
import com.pimuseum.indexpicker.data.ItemFlag
import com.pimuseum.indexpicker.R


class CityAdapter(var context : Context,var list : ArrayList<out Initial>)
    : RecyclerView.Adapter<CityAdapter.CityHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pm_item_city_list,parent,false)
        return CityHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.tvCity?.text = list[position].getTextName()
        generateTag(holder , position)
    }

    private fun generateTag(holder: CityHolder, position: Int) {
        val itemFlag: ItemFlag = if (holder.itemView.tag == null) {
            ItemFlag()
        } else {
            holder.itemView.tag as ItemFlag
        }

        when (position) {
            0 -> {
                itemFlag.isSectionStart = true
                itemFlag.isSectionEnd = list[position].getSpellInitial() != list[position + 1].getSpellInitial()
            }
            list.size - 1 -> {
                itemFlag.isSectionStart = list[position].getSpellInitial() != list[position - 1].getSpellInitial()
                itemFlag.isSectionEnd = true
            }
            else -> {
                itemFlag.isSectionStart = list[position].getSpellInitial() != list[position - 1].getSpellInitial()
                itemFlag.isSectionEnd = list[position].getSpellInitial() != list[position + 1].getSpellInitial()
            }
        }
        holder.itemView.tag = itemFlag
    }

    class CityHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var tvCity : TextView? = null

        init {
            tvCity = itemView.findViewById(R.id.tvCity)
        }
    }
}