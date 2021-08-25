package com.example.knucseapp.ui.board.writeboard

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.databinding.PhotoWriteRecyclerBinding

class WritePhotoAdapter : RecyclerView.Adapter<WritePhotoAdapter.Holder>() {

    var imageurl = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoWriteRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val url = imageurl.get(position)
        holder.setSetting(url)
    }

    override fun getItemCount(): Int {
        return imageurl.size
    }

    fun setUrl(urls: Uri){
        imageurl.add(urls)
    }

    inner class Holder(val binding: PhotoWriteRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSetting(item: Uri){
            Glide.with(binding.root.context).load(item).into(binding.writeImageview)
        }
    }
}
