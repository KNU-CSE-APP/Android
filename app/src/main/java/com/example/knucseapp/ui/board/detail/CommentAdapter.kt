package com.example.knucseapp.ui.board.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.BoardDetailRecyclerBinding
import com.example.knucseapp.databinding.CommentRecyclerBinding
import com.example.knucseapp.databinding.ReplyRecyclerBinding

class CommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var boardDetailList = mutableListOf<Any>()

    private val VIEW_TYPE_BOARD = 0
    private val VIEW_TYPE_COMMENT = 1
    private val VIEW_TYPE_REPLY = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_BOARD -> {
                val binding = BoardDetailRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return BoardDetailHolder(binding)
            }
            VIEW_TYPE_COMMENT -> {
                val binding = CommentRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return CommentHolder(binding)
            }
            else -> {
                val binding = ReplyRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return ReplyHolder(binding)
            }
        }
    }

    fun setData(comment: List<CommentDTO>?, board: BoardDTO) {
        boardDetailList.run {
            clear()
            add(board)
            if (comment != null) {
                addAll(comment)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        var size = boardDetailList.size
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BoardDetailHolder) holder.setBoard(boardDetailList.get(position) as BoardDTO)
        else if(holder is CommentHolder) holder.setComment(boardDetailList.get(position) as CommentDTO)
        else if(holder is ReplyHolder) holder.setReply(boardDetailList.get(position) as CommentDTO)
    }

    override fun getItemViewType(position: Int): Int {
        var item = boardDetailList.get(position)
        if(item is BoardDTO){
            return VIEW_TYPE_BOARD
        }
        else {
            item as CommentDTO
            return when(item.parentId){
                0-> VIEW_TYPE_COMMENT
                else -> VIEW_TYPE_REPLY
            }

        }
    }
}

class BoardDetailHolder(val binding: BoardDetailRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setBoard(boardItem: BoardDTO){
        binding.tvAuthor.text = boardItem.author
        binding.tvDate.text = boardItem.dateTime
        binding.tvTitle.text = boardItem.title
        binding.tvContent.text = boardItem.content
    }
}

class CommentHolder(val binding : CommentRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setComment(comment : CommentDTO){
        binding.tvAuthor.text = comment.author
        binding.tvComment.text = comment.content
        binding.tvDate.text = comment.time
    }
}

class ReplyHolder(val binding : ReplyRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    fun setReply(reply : CommentDTO){
        binding.tvAuthor.text = reply.author
        binding.tvComment.text = reply.content
        binding.tvDate.text = reply.time
    }
}

