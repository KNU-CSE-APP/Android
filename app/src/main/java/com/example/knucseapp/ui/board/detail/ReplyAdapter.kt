package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.ReplyRecyclerBinding
import com.example.knucseapp.ui.board.freeboard.Reply

class ReplyAdapter : RecyclerView.Adapter<ReplyHolder>() {

    var replys = mutableListOf<Reply>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyHolder {
        val binding = ReplyRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReplyHolder(binding)
    }

    override fun getItemCount(): Int {
        return replys.size
    }

    override fun onBindViewHolder(holder: ReplyHolder, position: Int) {
        val reply = replys.get(position)
        holder.setReply(reply)
    }
}

class ReplyHolder(val binding : ReplyRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setReply(reply : Reply){
        binding.tvAuthor.text = reply.author
        binding.tvComment.text = reply.comment
        binding.tvDate.text = reply.date
    }
}