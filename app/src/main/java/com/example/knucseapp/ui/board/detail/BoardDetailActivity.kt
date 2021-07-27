package com.example.knucseapp.ui.board.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityBoardDetailBinding
import com.example.knucseapp.ui.DividerItemDecoration
import com.example.knucseapp.ui.board.*
import com.example.knucseapp.ui.board.freeboard.*

class BoardDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var getBoard : Board
    var boardDetailList = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBoard = intent.getSerializableExtra("board") as Board
        //getComment = intent.getSerializableExtra("comment") as Comment
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)
        binding.lifecycleOwner = this

        setToolbar()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = CommentAdapter()
        setComment()
        binding.commentRecycler.scrollToPosition(0)
        binding.commentRecycler.adapter = adapter
        adapter.boardDetailList = boardDetailList

        binding.commentRecycler.layoutManager = LinearLayoutManager(this)

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setComment() {
        var replys = mutableListOf<Reply>(Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"),Reply(0,"지완","굿","07/24"))
        boardDetailList.add(getBoard.boardItem!!)
        val comment = Comment(1,"지완","지코바!","07/24 17:53",replys)
        for (i in 1..7) {
            boardDetailList.add(comment)
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