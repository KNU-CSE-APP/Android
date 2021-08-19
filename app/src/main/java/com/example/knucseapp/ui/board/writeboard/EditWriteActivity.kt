package com.example.knucseapp.ui.board.writeboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.BoardForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityEditWriteBinding
import com.example.knucseapp.databinding.ActivityWriteBinding
import com.example.knucseapp.ui.board.BoardHomeFragment
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.toast

class EditWriteActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : ActivityEditWriteBinding
    private lateinit var boardDTO: BoardDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_write)
        boardDTO = intent.getSerializableExtra("board") as BoardDTO
        initViewModel()
        setToolbar()
        setBoard()
        setButton()
    }

    fun setBoard(){
        viewModel.writeTitle.set(boardDTO.title)
        viewModel.writeContent.set(boardDTO.content)
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.editWriteToolbarTextview.text = when(boardDTO.category){
            "FREE" -> "자유게시판"
            else -> "QNA"
        }
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

    private fun initViewModel() {
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.toastMessage.observe(this) {
            toast("${it}")
        }

        viewModel.changeBoardDetailResponse.observe(this) {
            if(it.success){
                toast("게시물 수정이 완료되었습니다.")
                finish()
            }
            else{
                toast("${it.error}")
                finish()
            }
        }

    }

    private fun setButton(){
        binding.editWrite.setOnClickListener {
            viewModel.changeBoardDetail(BoardForm(boardDTO.category, boardDTO.content, boardDTO.title), boardDTO.boardId)
        }
    }
}