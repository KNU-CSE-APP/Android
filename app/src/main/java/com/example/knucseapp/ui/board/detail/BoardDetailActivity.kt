package com.example.knucseapp.ui.board.detail

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentForm
import com.example.knucseapp.data.model.ReplyForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityBoardDetailBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration
import kotlinx.android.synthetic.main.comment_recycler.*

class BoardDetailActivity : AppCompatActivity() {

    companion object {
        val hint_reply = "대댓글을 입력하세요"
        val hint_comment = "댓글을 입력하세요"
    }
    lateinit var viewModel : BoardViewModel
    lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var boardDetail : BoardDTO
    private lateinit var adapter : CommentAdapter
    private var boardid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardDetail = intent.getSerializableExtra("board") as BoardDTO
        boardid = boardDetail.boardId
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

        viewModel.boardDetailData.observe(this) {
            if(it.success) {
                boardDetail = it.response
                viewModel.getAllComment(boardid)
            }
        }

        viewModel.commentData.observe(this) {
            adapter.setData(it, boardDetail)
        }

        viewModel.writeCommentResponse.observe(this){
            viewModel.getBoardDetailData(boardid)
            binding.commentTextview.text = null
            hideKeyboard()
            Toast.makeText(this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRecyclerView() {
        val link = reply()
        adapter = CommentAdapter(link)
        binding.commentRecycler.adapter = adapter
        adapter.setData(null, boardDetail)
        viewModel.getBoardDetailData(boardid)
        binding.commentRecycler.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setButton() {
        binding.btnCtv.setOnClickListener {
            val content = binding.commentTextview.text.toString()
            val commentType = when(binding.commentTextview.hint){
                hint_comment -> {
                    0
                }
                else -> {
                    1
                }
            }
            if (content.isNullOrBlank()) {
                Toast.makeText(this, "댓글 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                when(commentType)
                {
                    0 -> viewModel.writeComment(CommentForm(boardDetail.boardId, content))
                    else -> {

                    }
                }

            }
        }

    }

    //현재 focus 가 아닌 곳 클릭시 키보드 내려감
    //TODO : 완료 버튼은 영역에서 제거해야함
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        val focusView = currentFocus
        val focusView = binding.edtComment
        if (focusView != null) {

            var rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev!!.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                hideKeyboard()
                focusView.clearFocus()
                binding.commentTextview.hint = hint_comment
            }
        }
        return super.dispatchTouchEvent(ev)
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

    inner class reply{
        fun makeReply(boardId: Int, commentId: Int) {
            binding.commentTextview.requestFocus()
            showKeyboard()
            binding.commentTextview.hint = hint_reply

            binding.btnCtv.setOnClickListener {
                Log.d(TAG, "${boardId}, ${commentId}, ${binding.commentTextview.text.toString()}")
                viewModel.writeReply(ReplyForm(boardId, commentId, binding.commentTextview.text.toString()))
            }
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.commentTextview.windowToken, 0)
    }

    fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

}