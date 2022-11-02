package com.example.a7minworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkoutapp.databinding.HistoryRecyclerBinding
import com.example.a7minworkoutapp.databinding.ItemRecyclerBinding

class HistoryAdapter(private val items: List<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: HistoryRecyclerBinding ):
        RecyclerView.ViewHolder(binding.root){
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
        val llHistoryItemLayout = binding.llHistoryItemLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryRecyclerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val date = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if(position % 2 == 0){
            holder.llHistoryItemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }else{
            holder.llHistoryItemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}