package com.example.knucseapp.ui.board.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.ReplyRecyclerBinding
import com.example.knucseapp.ui.util.MyApplication
import okhttp3.internal.notify

class ReplyAdapter(var link: CommentActivity.reply?) : RecyclerView.Adapter<ReplyHolder>() {

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
        if(link!=null && comment.author.equals(MyApplication.prefs.getUserNickname())) {
            holder.binding.imgSetting.run {
                visibility = VISIBLE
                setOnClickListener{
                    link?.callPopupMenu(comment.commentId, holder.binding.imgSetting)
                }
            }
        }
    }

    fun setData(data: List<CommentDTO>?)
    {
        replys.clear()
        if(data!=null) {
            replys.addAll(data)
        }
        notifyDataSetChanged()
    }
}

class ReplyHolder(val binding : ReplyRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setReply(reply : CommentDTO){
        binding.tvAuthor.text = reply.author
        binding.tvComment.text = reply.content
        binding.tvDate.text = reply.time
    }
}