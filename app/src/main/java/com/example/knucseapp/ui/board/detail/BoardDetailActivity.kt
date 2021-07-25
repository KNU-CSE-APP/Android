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

class BoardDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var getBoardItem : BoardItem
    private lateinit var getComment : Comment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBoardItem = intent.getSerializableExtra("boarditem") as BoardItem
        getComment = intent.getSerializableExtra("comment") as Comment
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)
        binding.lifecycleOwner = this

        setToolbar()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = CommentAdapter()
        adapter.boardDTOs = setData()
        binding.commentRecycler.adapter = adapter
        binding.commentRecycler.layoutManager = LinearLayoutManager(this)

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setData(): MutableList<BoardDTO> {
        var boardDTOs = mutableListOf<BoardDTO>()
        boardDTOs.add(BoardDTO(getBoardItem,getComment))

        val comment = Comment(1,"지완","지코바!","07/24 17:53")
        for (i in 1..7) {
            boardDTOs.add(BoardDTO(getBoardItem,comment))
        }
        return boardDTOs
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