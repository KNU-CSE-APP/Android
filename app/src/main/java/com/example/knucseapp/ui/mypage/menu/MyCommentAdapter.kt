package com.example.knucseapp.ui.mypage.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.MyCommentRecyclerBinding
import com.example.knucseapp.ui.board.detail.BoardDetailActivity

class MyCommentAdapter(val title: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var myCommentList = mutableListOf<CommentDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MyCommentRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Holder){
            val comment = myCommentList.get(position)
            holder.setComment(comment)
            
            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, BoardDetailActivity::class.java)
                intent.putExtra("boardId", comment.boardId)
                intent.putExtra("title",title)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return myCommentList.size
    }

    fun myCommentItem(items: List<CommentDTO>){
        myCommentList.clear()
        myCommentList.addAll(items)
        notifyDataSetChanged()
    }

    inner class Holder(val binding : MyCommentRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun setComment(comment : CommentDTO){
            Glide.with(binding.root.context).load(comment.profileImage).into(binding.accountIvProfile)
            binding.tvAuthor.setText(comment.author)
            binding.tvComment.setText(comment.content)
            binding.tvDate.setText(comment.time)
        }
    }

}
