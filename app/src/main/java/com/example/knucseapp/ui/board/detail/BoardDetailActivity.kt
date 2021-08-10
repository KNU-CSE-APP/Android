package com.example.knucseapp.ui.board.detail

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentForm
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityBoardDetailBinding
import com.example.knucseapp.ui.MainActivity
import com.example.knucseapp.ui.auth.AuthViewModel
import com.example.knucseapp.ui.auth.AuthViewModelFactory
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration

class BoardDetailActivity : AppCompatActivity() {

    lateinit var viewModel : BoardViewModel
    lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var boardDetail : BoardDTO
    private lateinit var adapter : CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardDetail = intent.getSerializableExtra("board") as BoardDTO

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)

        initViewModel()
        setToolbar()
        setRecyclerView()
        setButton()
    }

    private fun initViewModel(){
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.commentDataLoading.observe(this){
            if(it){
                binding.detailProgressBar.visibility = View.VISIBLE
            }
            else{
                binding.detailProgressBar.visibility = View.GONE
            }
        }

        viewModel.commentData.observe(this) {
            adapter.setData(it, boardDetail)
        }

        viewModel.writeCommentResponse.observe(this){
            viewModel.getAllComment(boardDetail.boardId)
            binding.commentTextview.text = null
            Toast.makeText(this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRecyclerView() {
        adapter = CommentAdapter()
        binding.commentRecycler.adapter = adapter
        adapter.setData(null, boardDetail)
        viewModel.getAllComment(boardDetail.boardId)

        binding.commentRecycler.layoutManager = LinearLayoutManager(this)

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setButton() {
        binding.btnCtv.setOnClickListener {
            val content = binding.commentTextview.text.toString()
            if (content.isNullOrBlank()) {
                Toast.makeText(this, "댓글 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.writeComment(CommentForm(boardDetail.boardId, content))
            }
        }
    }
    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.boardDetailToolbarTextview.text = intent.getStringExtra("title")
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