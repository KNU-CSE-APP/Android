package com.example.knucseapp.ui.board.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.ReplyRecyclerBinding

class ReplyAdapter(var link: BoardDetailActivity.reply) : RecyclerView.Adapter<ReplyHolder>() {

    var replys = mutableListOf<CommentDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyHolder {
        val binding = ReplyRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReplyHolder(binding)
    }

    override fun getItemCount(): Int {
        return replys.size
    }

    override fun onBindViewHolder(holder: ReplyHolder, position: Int) {
        val comment = replys.get(position)
        holder.setReply(comment)
        holder.binding.imgSetting.setOnClickListener{
            link.callPopupMenu(1, comment.commentId, holder.binding.imgSetting)
        }
    }
}

class ReplyHolder(val binding : ReplyRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setReply(reply : CommentDTO){
        binding.tvAuthor.text = reply.author
        binding.tvComment.text = reply.content
        binding.tvDate.text = reply.time
    }
}