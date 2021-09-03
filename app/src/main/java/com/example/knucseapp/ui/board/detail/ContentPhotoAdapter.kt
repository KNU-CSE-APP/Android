package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.databinding.PhotoRecyclerBinding
import kotlinx.android.synthetic.main.image_dialog.view.*

class ContentPhotoAdapter :RecyclerView.Adapter<ContentPhotoAdapter.Holder>() {

    var imageurl = mutableListOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val url = imageurl.get(position)
        holder.binding.contentImageview.setOnClickListener {
            val mDialogView = LayoutInflater.from(holder.binding.root.context).inflate(R.layout.image_dialog, null)
            Glide.with(holder.binding.root.context).load(url).into(mDialogView.imageview)
            val mBuilder = AlertDialog.Builder(holder.binding.root.context)
                .setView(mDialogView)
            mBuilder.show()

        }
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
            Glide.with(binding.root.context).load(item).override(500,500).into(binding.contentImageview)
        }
    }
}

