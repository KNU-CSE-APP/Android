package com.example.knucseapp.ui.board.freeboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.BoardRecyclerBinding
import com.example.knucseapp.ui.board.detail.BoardDetailActivity


class BoardAdapter: RecyclerView.Adapter<Holder>() {

    var boardDTOs = mutableListOf<BoardDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BoardRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return boardDTOs.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val board = boardDTOs.get(position).board
        val boardItem = board?.boardItem
        //val comment = boardDTOs.get(position).comments
        holder.setBoard(board!!)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, BoardDetailActivity::class.java)
            intent.putExtra("board",board)
            //intent.putExtra("comment",comment)
            it.context.startActivity(intent)
        }
    }
}

class Holder(val binding: BoardRecyclerBinding): RecyclerView.ViewHolder(binding.root){
    /*init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvTitle.text}",Toast.LENGTH_LONG).show()
        }
    }*/

    fun setBoard(item : Board){
        binding.tvTitle.text = item.boardItem?.title
        binding.tvAuthor.text = item.boardItem?.author
        binding.tvContent.text = item.boardItem?.content
        binding.tvDate.text = item.boardItem?.date
        binding.tvCommentCnt.text = item.comments?.size.toString()
        binding.tvTag.text = item.boardItem?.category
    }
}