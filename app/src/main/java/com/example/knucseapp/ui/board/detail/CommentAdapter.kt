package com.example.knucseapp.ui.board.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.BoardDetailRecyclerBinding
import com.example.knucseapp.databinding.CommentRecyclerBinding
import com.example.knucseapp.databinding.ReplyRecyclerBinding
import com.example.knucseapp.ui.reservation.seat.ReservationActivity
import com.example.knucseapp.ui.reservation.seat.ReservationConfirmActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CommentAdapter(var link: BoardDetailActivity.reply): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                addAll(sortData(comment))
            }
        }
        notifyDataSetChanged()
    }

    fun sortData(comment: List<CommentDTO>):List<CommentDTO> {
        var sortData = mutableListOf<CommentDTO>()
        var replyData = mutableListOf<CommentDTO>()

        comment.forEach {
            if(it.parentId == 0) {
                sortData.add(it)
            }
            else {
                replyData.add(it)
            }
        }

        replyData.sortBy{
            it.parentId
        }

        while(!replyData.isNullOrEmpty()) {
            var parentId = replyData.get(0).parentId
            var getReply = replyData.filter {
                it.parentId == parentId
            }

            replyData.removeAll { it ->
                it.parentId == parentId
            }

            for(i in 0 until sortData.size){
                if(sortData[i].commentId == parentId) { //parent에 해당하는 댓글을 찾으면
                    //parent 다음 위치에 대댓글을 모두 넣어줌
                    sortData.addAll(i+1, getReply)
                    break
                }
            }

        }
        return sortData
    }

    override fun getItemCount(): Int {
        var size = boardDetailList.size
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is BoardDetailHolder -> holder.setBoard(boardDetailList.get(position) as BoardDTO)
            is CommentHolder -> {
                val comment = boardDetailList.get(position) as CommentDTO
                holder.setComment(comment)
                holder.binding.btnReply.setOnClickListener {
                    MaterialAlertDialogBuilder(it.context)
                            .setMessage("대댓글을 작성하시겠습니까?")
                            .setPositiveButton("확인") { dialog, whichButton ->
                                link.makeReply(comment.boardId, comment.commentId)
                            }
                            .setNegativeButton("취소") { dialog, whichButton ->
                            }
                            .show()
                }

            }
            else -> {
                holder as ReplyHolder
                holder.setReply(boardDetailList.get(position) as CommentDTO)
            }
        }
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
        binding.tvDate.text = boardItem.time
        binding.tvTitle.text = boardItem.title
        binding.tvContent.text = boardItem.content
        binding.tvCommentCnt.text = "${boardItem.commentCnt}"
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

