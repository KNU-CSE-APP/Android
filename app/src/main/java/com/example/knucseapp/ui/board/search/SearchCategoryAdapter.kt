package com.example.knucseapp.ui.board.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.SearchCategoryRecyclerBinding

class SearchCategoryAdapter : RecyclerView.Adapter<Holder>() {
    var category_list = mutableListOf<String>()
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
}

class Holder(val binding: SearchCategoryRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnCategory.isSelected = false
    }
    fun setSetting(item: String){
        binding.btnCategory.text = item
        binding.btnCategory.setOnClickListener {
            binding.btnCategory?.isSelected = binding.btnCategory?.isSelected != true
        }
    }
}