package com.example.knucseapp.ui.board.detail

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.BoardDetailRecyclerBinding
import com.example.knucseapp.databinding.CommentRecyclerBinding
import com.example.knucseapp.ui.util.MyApplication
import com.example.knucseapp.ui.util.NetworkStatus
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CommentAdapter(var link: BoardDetailActivity.reply): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var boardDetailList = mutableListOf<Any>()

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

    fun setData(comment: List<CommentDTO>?, board: BoardDTO) {
        Log.d("CommentAdapter", "comment data : ${comment.toString()}, ${board}")
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
        return boardDetailList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is BoardDetailHolder -> {
                val boardDetail = boardDetailList.get(position) as BoardDTO
                holder.setBoard(boardDetail)
                if(boardDetail.author == MyApplication.prefs.getUserNickname()) {
                    holder.binding.imgSetting.run {
                        visibility = VISIBLE
                        setOnClickListener {
                            link.callPopupMenu(0, boardDetail.boardId, holder.binding.imgSetting)
                        }
                    }
                }
                else {
                    holder.binding.imgSetting.run {
                        visibility = GONE
                    }
                }
            }
            else -> {
                holder as CommentHolder
                val comment = boardDetailList.get(position) as CommentDTO
                holder.setComment(comment)
                holder.binding.btnReply.setOnClickListener {
                    MaterialAlertDialogBuilder(it.context)
                            .setMessage("???????????? ?????????????????????????")
                            .setPositiveButton("??????") { dialog, whichButton ->
//                                link.makeReply(comment.boardId, comment.commentId)
                                if(NetworkStatus.status){
                                    val intent = Intent(it.context, CommentActivity::class.java)
                                    intent.putExtra("commentId", comment.commentId)
                                    intent.putExtra("boardId", comment.boardId)
                                    it.context.startActivity(intent)
                                }
                            }
                            .setNegativeButton("??????") { dialog, whichButton ->
                            }
                            .show()
                }
                if(comment.author == MyApplication.prefs.getUserNickname()) {
                    holder.binding.imgSetting.run {
                        visibility = VISIBLE
                        setOnClickListener {
                            link.callPopupMenu(1, comment.commentId, holder.binding.imgSetting)
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
    }

    override fun getItemViewType(position: Int): Int {
        return when(boardDetailList.get(position)){
            is BoardDTO -> VIEW_TYPE_BOARD
            else -> VIEW_TYPE_COMMENT
        }
    }

    inner class BoardDetailHolder(val binding: BoardDetailRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun setBoard(boardItem: BoardDTO){
            binding.tvAuthor.text = boardItem.author
            binding.tvDate.text = boardItem.time
            binding.tvTitle.text = boardItem.title
            binding.tvContent.text = boardItem.content
            binding.tvCommentCnt.text = "${boardItem.commentCnt}"
            binding.tvPhotoCnt.text = "${boardItem.images.size}"
            if(boardItem.category.equals("ADMIN")){
                Glide.with(binding.root.context).load(R.drawable.img_csmark).into(binding.accountIvProfile)
            }
            else {
                if (boardItem.profileImg == null) Glide.with(binding.root.context).load(R.drawable.img_user).into(binding.accountIvProfile)
                else {
                    Glide.with(binding.root.context).load(boardItem.profileImg).into(binding.accountIvProfile)
                }
            }

            if(boardItem.images.size != null) {
                val photoAdapter = ContentPhotoAdapter()
                photoAdapter.setUrl(boardItem.images)
                binding.contentPhotoRecycler.adapter = photoAdapter
                binding.contentPhotoRecycler.layoutManager =
                    LinearLayoutManager(binding.root.context).also {
                        it.orientation = LinearLayoutManager.HORIZONTAL
                    }
            }
        }
    }

    inner class CommentHolder(val binding : CommentRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun setComment(comment : CommentDTO){
            binding.tvAuthor.text = comment.author
            binding.tvComment.text = comment.content
            binding.tvDate.text = comment.time

            if(comment.profileImage == null) Glide.with(binding.root.context).load(R.drawable.img_user).into(binding.accountIvProfile)
            else{ Glide.with(binding.root.context).load(comment.profileImage).into(binding.accountIvProfile) }

            if(comment.replyList!=null) {
                val replyAdapter = ReplyAdapter(null)
                replyAdapter.replys = comment.replyList!!
                replyAdapter.notifyDataSetChanged()
                binding.replyRecycler.adapter = replyAdapter
                binding.replyRecycler.layoutManager = LinearLayoutManager(binding.root.context)
            }
            else {
                binding.replyRecycler.adapter = null
            }
        }
    }

}


