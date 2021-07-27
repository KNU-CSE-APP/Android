package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.BoardDetailRecyclerBinding
import com.example.knucseapp.databinding.CommentRecyclerBinding
import com.example.knucseapp.ui.board.freeboard.BoardItem
import com.example.knucseapp.ui.board.freeboard.Comment

class CommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var boardDetailList : MutableList<Any>

    private val VIEW_TYPE_BOARD = 0
    private val VIEW_TYPE_COMMENT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_BOARD -> {
                val binding = BoardDetailRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return BoardDetailHolder(binding)
            }
            else -> {
                val binding = CommentRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return CommentHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        var size = boardDetailList.size
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BoardDetailHolder) holder.setBoard(boardDetailList.get(position) as BoardItem)
        else if(holder is CommentHolder) holder.setComment(boardDetailList.get(position) as Comment)
    }

    override fun getItemViewType(position: Int): Int {
        return when(boardDetailList.get(position)){
            is BoardItem -> VIEW_TYPE_BOARD
            else -> VIEW_TYPE_COMMENT
        }
    }
}

class BoardDetailHolder(val binding: BoardDetailRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setBoard(boardItem: BoardItem){
        binding.tvAuthor.text = boardItem.author
        binding.tvDate.text = boardItem.date
        binding.tvTitle.text = boardItem.title
        binding.tvContent.text = boardItem.content
    }
}

class CommentHolder(val binding : CommentRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setComment(comment : Comment){
        binding.tvAuthor.text = comment.author
        binding.tvComment.text = comment.comment
        binding.tvDate.text = comment.date

        val replyAdapter = ReplyAdapter()
        replyAdapter.replys = comment.replys!!
        binding.replyRecycler.adapter = replyAdapter
        binding.replyRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        //binding.replyRecycler.setHasFixedSize(true)
    }
}

