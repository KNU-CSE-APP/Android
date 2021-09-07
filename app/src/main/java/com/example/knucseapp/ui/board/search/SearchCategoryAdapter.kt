package com.example.knucseapp.ui.board.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.SearchCategoryRecyclerBinding

class SearchCategoryAdapter : RecyclerView.Adapter<SearchCategoryAdapter.Holder>() {
    var category_list = listOf("내용", "제목", "작성자")
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SearchCategoryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = category_list.get(position)
        holder.binding.btnCategory.isSelected = selectedPosition == position
        holder.binding.btnCategory.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }
        holder.setSetting(menu)
    }

    override fun getItemCount(): Int {
        return category_list.size
    }

    inner class Holder(val binding: SearchCategoryRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSetting(item: String){
            binding.btnCategory.text = item
        }
    }

}

