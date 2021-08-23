package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.ReplyRecyclerBinding
import com.example.knucseapp.ui.util.MyApplication

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
        if(link!=null) {
            if(comment.author.equals(MyApplication.prefs.getUserNickname())) {
                holder.binding.imgSetting.run {
                    visibility = VISIBLE
                    setOnClickListener {
                        link?.callPopupMenu(comment.commentId, holder.binding.imgSetting)
                    }
                }
            }
            else {
                holder.binding.imgSetting.run {
                    visibility = GONE
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
        if(reply.profileImage == null) Glide.with(binding.root.context).load(R.drawable.user).into(binding.accountIvProfile)
        else{ Glide.with(binding.root.context).load(reply.profileImage).into(binding.accountIvProfile) }
    }
}