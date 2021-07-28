package com.example.knucseapp.ui.board.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.SearchCategoryRecyclerBinding

class SearchCategoryAdapter : RecyclerView.Adapter<SearchCategoryAdapter.Holder>() {
    var category_list = mutableListOf<String>()
    interface OnItemClickListener{
        fun onItemClick(v: View, data: String, pos : Int, flag: Boolean)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SearchCategoryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = category_list.get(position)
        holder.setSetting(menu)

    }

    override fun getItemCount(): Int {
        return category_list.size
    }

    inner class Holder(val binding: SearchCategoryRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnCategory.isSelected = false
        }
        fun setSetting(item: String){
            binding.btnCategory.text = item
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                binding.btnCategory.setOnClickListener {
                    binding.btnCategory?.isSelected = binding.btnCategory?.isSelected != true
                    listener?.onItemClick(itemView, item, pos, binding.btnCategory?.isSelected)
                }
            }

        }
    }
}

