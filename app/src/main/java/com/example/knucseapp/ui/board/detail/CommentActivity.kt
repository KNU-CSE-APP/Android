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
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ReplyForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityCommentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.*
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.comment_recycler.*

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

        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.connectedLayout.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
                viewModel.getCommentReply(commentId)
            }
            else
            {
                binding.connectedLayout.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }
    }

    private fun initViewModel() {
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //?????? commentdataloading
        viewModel.commentDataLoading.observe(this){
            if(it){
                binding.commentProgressBar.show()
            }
            else{
                binding.commentProgressBar.hide()
            }
        }

        viewModel.commentData.observe(this) {
            binding.tvAuthor.text = it.author
            binding.tvComment.text = it.content
            binding.tvDate.text = it.time
            if(it.profileImage == null) Glide.with(this).load(R.drawable.img_user).into(binding.accountIvProfile)
            else{ Glide.with(binding.root.context).load(it.profileImage).into(binding.accountIvProfile) }
            adapter.setData(it.replyList)
        }

        viewModel.writeCommentResponse.observe(this){
            if(NetworkStatus.status){
                viewModel.getCommentReply(commentId)
                binding.replyTextview.text = null
                hideKeyboard(binding.replyTextview)
                toast("?????? ????????? ?????????????????????.")
            }
            else
                toast("???????????? ????????? ????????? ?????????.")

        }

        viewModel.deleteCommentResponse.observe(this) {
            if(it!=null && NetworkStatus.status) {
                toast("${it.response}")
                viewModel.getCommentReply(commentId)
            }
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
//        viewModel.getCommentReply(commentId)
//        viewModel.getAllComment(boardId)
        binding.commentRecycler.layoutManager = LinearLayoutManager(this)
//        binding.commentRecycler.isNestedScrollingEnabled()
//        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
//        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setButton() {
        binding.btnCtv.setOnClickListener {
            val content = binding.replyTextview.text.toString()
            if (content.isNullOrBlank()) {
                toast("?????? ????????? ??????????????????.")
            }
            else {
                if(NetworkStatus.status)
                    viewModel.writeReply(ReplyForm(boardId, commentId, binding.replyTextview.text.toString()))
                else
                    toast("???????????? ????????? ????????? ?????????.")
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
                        Log.d(TAG, "delete ${Id}")
                        if(NetworkStatus.status)
                            viewModel.deleteComment(Id)
                        else
                            toast("???????????? ????????? ????????? ?????????.")
                        true
                    }
                    else -> {
                        Toast.makeText(this@CommentActivity, "?????? ??????!", Toast.LENGTH_SHORT).show()
                        true
                    }
                }

            }
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.comment_menu_item, popup.menu)
            popup.show()
        }
    }

    //?????? focus ??? ?????? ??? ????????? ????????? ?????????
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