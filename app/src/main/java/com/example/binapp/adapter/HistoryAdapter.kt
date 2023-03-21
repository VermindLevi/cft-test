package com.example.binapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.binapp.R
import com.example.binapp.databinding.ItemHistoryLayoutBinding
import com.example.binapp.model.HistoryModel

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyList = ArrayList<HistoryModel>()

    class HistoryViewHolder(item: View):RecyclerView.ViewHolder(item){
        val binding = ItemHistoryLayoutBinding.bind(item)
        fun bind(his: HistoryModel){
            binding.hs.text = his.bin_s

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout, parent,false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addHis(his: HistoryModel){
        historyList.add(his)
        notifyDataSetChanged()
    }
}