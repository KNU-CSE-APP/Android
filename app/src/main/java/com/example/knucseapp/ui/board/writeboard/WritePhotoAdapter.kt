package com.example.knucseapp.ui.board.writeboard

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ReservationDTO
import com.example.knucseapp.databinding.PhotoWriteRecyclerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WritePhotoAdapter : RecyclerView.Adapter<WritePhotoAdapter.Holder>() {

    var imageurl = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoWriteRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val url = imageurl.get(position)
        holder.binding.cancelBtn.setOnClickListener {
            MaterialAlertDialogBuilder(holder.binding.root.context)
                    .setTitle("삭제 확인")
                    .setMessage("이미지를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { dialog, whichButton ->
                        deleteUri(position)
                    }
                    .setNegativeButton("취소") { dialog, whichButton -> // 취소시 처리 로직
                    }
                    .show()
        }
        holder.setSetting(url)
    }

    override fun getItemCount(): Int {
        return imageurl.size
    }

    fun setUrl(urls: List<Any>){
        imageurl.clear()
        imageurl.addAll(urls)
        notifyDataSetChanged()
    }

    fun deleteUri(position: Int) {
        imageurl.removeAt(position)
        notifyDataSetChanged()
    }

    fun addUri(urls: List<Any>) {
        imageurl.addAll(urls)
        notifyDataSetChanged()
    }

    inner class Holder(val binding: PhotoWriteRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSetting(item: Any){
            Glide.with(binding.root.context).load(item).into(binding.writeImageview)
        }
    }
}
