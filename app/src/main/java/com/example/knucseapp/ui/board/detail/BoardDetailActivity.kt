package com.example.knucseapp.ui.board.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.databinding.ActivityBoardDetailBinding
import com.example.knucseapp.ui.util.DividerItemDecoration
import com.example.knucseapp.ui.board.freeboard.*

class BoardDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var getBoard : BoardDTO
    var boardDetailList = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBoard = intent.getSerializableExtra("board") as BoardDTO
        //getComment = intent.getSerializableExtra("comment") as Comment
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)
        binding.lifecycleOwner = this

        setToolbar()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = CommentAdapter()
        setComment()
        binding.commentRecycler.adapter = adapter
        adapter.boardDetailList = boardDetailList

        binding.commentRecycler.layoutManager = LinearLayoutManager(this)

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setComment() {
        var replys = mutableListOf<CommentDTO>(CommentDTO("지완",1,1,"굿", 0, "7/24"),
            CommentDTO("지완",1,2,"대댓글", 1, "7/24"),
            CommentDTO("지완",1,2,"굿", 0, "7/24")
        , CommentDTO("지완",2,3,"굿", 0, "7/24"))
        boardDetailList.add(getBoard!!)
        for (i in 1..7) {
            boardDetailList.addAll(replys)
        }
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.boardDetailToolbarTextview.text = "자유게시판"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}