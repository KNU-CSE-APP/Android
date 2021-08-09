package com.example.knucseapp.ui.board.freeboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.databinding.BoardRecyclerBinding
import com.example.knucseapp.ui.board.detail.BoardDetailActivity


class BoardAdapter(val title: String): RecyclerView.Adapter<Holder>() {

    var boardDTOs = mutableListOf<BoardDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BoardRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return boardDTOs.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val boardDTO = boardDTOs.get(position)
        holder.setBoard(boardDTO)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, BoardDetailActivity::class.java)
            intent.putExtra("board",boardDTO)
            intent.putExtra("title", title)
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

    fun setBoard(item : BoardDTO){
        binding.tvTitle.text = item.title
        binding.tvAuthor.text = item.author
        binding.tvContent.text = item.content
        binding.tvDate.text = item.dateTime
        binding.tvCommentCnt.text = "0"
        binding.tvTag.text = item.category
    }
}