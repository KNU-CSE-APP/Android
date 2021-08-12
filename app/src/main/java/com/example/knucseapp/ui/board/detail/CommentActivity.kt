package com.example.knucseapp.ui.board.detail

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ReplyForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityCommentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.hideKeyboard
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    companion object {
        val TAG = "CommentActivity"
    }

    private lateinit var viewModel: BoardViewModel
    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var binding: ActivityCommentBinding
    private lateinit var adapter : ReplyAdapter
    private var commentId: Int = 0
    private var boardId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        commentId = intent.getIntExtra("commentId", 0)
        boardId = intent.getIntExtra("boardId", 0)

        initViewModel()
        setToolbar()
        setRecyclerView()
        setButton()
    }

    private fun initViewModel() {
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.commentDataLoading.observe(this){
            if(it){
                binding.commentProgressBar.show()
            }
            else{
                binding.commentProgressBar.hide()
            }
        }

        viewModel.commentData.observe(this) {
            adapter.setData(it.replyList)
        }

        viewModel.writeCommentResponse.observe(this){
            viewModel.getCommentReply(commentId)
            binding.replyTextview.text = null
            hideKeyboard(binding.replyTextview)
            toast("댓글 작성이 완료되었습니다.")
        }

    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecyclerView() {
        val link = reply()
        adapter = ReplyAdapter(link)
        binding.commentRecycler.adapter = adapter
        viewModel.getCommentReply(commentId)
        binding.commentRecycler.layoutManager = LinearLayoutManager(this)
//        binding.commentRecycler.isNestedScrollingEnabled()
//        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
//        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setButton() {
        binding.btnCtv.setOnClickListener {
            val content = binding.replyTextview.text.toString()
            if (content.isNullOrBlank()) {
                toast("댓글 내용을 입력해주세요.")
            }
            else {
                viewModel.writeReply(ReplyForm(boardId, commentId, binding.replyTextview.text.toString()))
            }
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

    inner class reply{
        fun callPopupMenu(Id: Int, view: View) {
            var popup = PopupMenu(this@CommentActivity, view)
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.menu_delete -> {
                        viewModel.deleteComment(Id)
                        true
                    }
                    else -> {
                        Toast.makeText(this@CommentActivity, "댓글 수정!", Toast.LENGTH_SHORT).show()
                        true
                    }
                }

            }
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.comment_menu_item, popup.menu)
            popup.show()
        }
    }

    //현재 focus 가 아닌 곳 클릭시 키보드 내려감
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = binding.edtComment
        if (focusView != null) {

            var rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev!!.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                hideKeyboard(binding.edtComment)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}