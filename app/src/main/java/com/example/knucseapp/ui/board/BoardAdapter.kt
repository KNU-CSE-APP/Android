package com.example.knucseapp.ui.board.freeboard

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.databinding.BoardRecyclerBinding
import com.example.knucseapp.databinding.BoardRecyclerLoadingBinding
import com.example.knucseapp.ui.board.detail.BoardDetailActivity


class BoardAdapter(val title: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_POST = 0 //게시글
        private const val TYPE_LOADING = 1 //NULL
    }

    var boardDTOs = mutableListOf<BoardDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_POST -> {
                val binding = BoardRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                Holder(binding)
            }
            else -> {
                val binding = BoardRecyclerLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                LoadingHolder(binding)

            }
        }

    }

    override fun getItemCount(): Int {
        return boardDTOs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is Holder) {
            val boardDTO = boardDTOs.get(position)
            holder.setBoard(boardDTO)

            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, BoardDetailActivity::class.java)
                intent.putExtra("boardId", boardDTO.boardId)
                intent.putExtra("title", title)
                it.context.startActivity(intent)
            }
        }
        else{

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(boardDTOs[position].boardId){
            -1 -> TYPE_LOADING
            else -> TYPE_POST
        }
    }


    fun addItem(items: List<BoardDTO>, page: Int) {
        if(page == 1) {
            boardDTOs.clear()
        }
        boardDTOs.addAll(items)
        boardDTOs.add(BoardDTO(" ", -1, " ", " ", " ", -1, " ", " ", listOf("")))
        notifyDataSetChanged()
    }

    fun myboardItem(items: List<BoardDTO>){
        boardDTOs.clear()
        boardDTOs.addAll(items)
        notifyDataSetChanged()
    }

    fun deleteLoading(): Boolean {
        if(boardDTOs.get(boardDTOs.lastIndex).boardId == -1) {
            boardDTOs.removeAt(boardDTOs.lastIndex)
            return true
        }
        return false
    }


    inner class Holder(val binding: BoardRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        /*init {
            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvTitle.text}",Toast.LENGTH_LONG).show()
            }
        }*/

        fun setBoard(item : BoardDTO){
            binding.tvTitle.text = item.title
            binding.tvAuthor.text = item.author
            binding.tvContent.text = item.content
            binding.tvDate.text = item.time
            binding.tvCommentCnt.text = item.commentCnt.toString()
            if(item.profileImg == null) Glide.with(binding.root.context).load(R.drawable.user).into(binding.accountIvProfile)
            else{ Glide.with(binding.root.context).load(item.profileImg).into(binding.accountIvProfile) }

            if(item.category.equals("FREE")){
                binding.tvTag.text = "#자유게시판"
            }
            else {
                binding.tvTag.text = "#QNA"
            }
        }
    }

    inner class LoadingHolder(val binding:BoardRecyclerLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    }


}

