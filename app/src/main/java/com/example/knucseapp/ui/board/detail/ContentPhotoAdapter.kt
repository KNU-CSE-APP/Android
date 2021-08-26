package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.databinding.PhotoRecyclerBinding

class ContentPhotoAdapter :RecyclerView.Adapter<ContentPhotoAdapter.Holder>() {

    var imageurl = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val url = imageurl.get(position)
        holder.setSetting(url)
    }

    override fun getItemCount(): Int {
        return imageurl.size
    }

    fun setUrl(urls: List<String>){
        imageurl.clear()
        imageurl.addAll(urls)
        notifyDataSetChanged()
    }

    inner class Holder(val binding: PhotoRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSetting(item: String){
            Glide.with(binding.root.context).load(item).into(binding.contentImageview)
        }
    }
}

