package com.example.knucseapp.ui.board.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.CommentForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityBoardDetailBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.board.writeboard.EditWriteActivity
import com.example.knucseapp.ui.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.comment_recycler.*

class BoardDetailActivity : AppCompatActivity() {

    companion object {
        val comment_type = 1
        val boardContent_type = 0
        val TAG = "BoardDetailActivity"
    }
    lateinit var viewModel : BoardViewModel
    lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var boardDetail : BoardDTO
    private lateinit var adapter : CommentAdapter
    private var boardid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardid = intent.getIntExtra("boardId", 0)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)

        initViewModel()
        setToolbar()
        setRecyclerView()
        setButton()
    }

    override fun onStart() {
        super.onStart()
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.connectedLayout.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
                viewModel.getBoardDetailData(boardid)
            }
            else
            {
                binding.connectedLayout.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }
    }

    private fun initViewModel(){
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.allCommentDataLoading.observe(this){
            if(it){
                binding.detailProgressBar.show()
            }
            else{
                binding.detailProgressBar.hide()
            }
        }

        viewModel.boardDetailData.observe(this) {
            if(it.success) {
                boardDetail = it.response
                if(NetworkStatus.status)
                    viewModel.getAllComment(boardid)
                else
                    toast("???????????? ????????? ????????? ?????????.")
            }
            else {
                AlertDialog.Builder(this)
                MaterialAlertDialogBuilder(binding.root.context)
                        .setTitle("??????")
                        .setMessage("???????????? ?????? ??????????????????.")
                        .setPositiveButton("??????") { dialog, whichButton ->
                            finish()
                        }
                        .show()
            }
        }

        viewModel.allCommentData.observe(this) {
            adapter.setData(it, boardDetail)
        }

        viewModel.writeCommentResponse.observe(this){
            viewModel.getBoardDetailData(boardid)
            binding.commentTextview.text = null
            hideKeyboard(binding.commentTextview)
            toast("?????? ????????? ?????????????????????.")
        }

        viewModel.deleteCommentResponse.observe(this) {
            if(it!=null) {
                toast("${it.response}")
                if(NetworkStatus.status)
                    viewModel.getBoardDetailData(boardid)
                else
                    toast("???????????? ????????? ????????? ?????????.")
            }
        }

        viewModel.deleteBoardDetailResponse.observe(this) {
            toast("????????? ????????? ?????????????????????.")
            finish()
        }
    }

    private fun setRecyclerView() {
        val link = reply()
        adapter = CommentAdapter(link)
        binding.commentRecycler.adapter = adapter
//        adapter.setData(null, boardDetail)
        binding.commentRecycler.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.commentRecycler.addItemDecoration(decoration)
    }

    private fun setButton() {
        binding.btnCtv.setOnClickListener {
            val content = binding.commentTextview.text.toString()
            if (content.isNullOrBlank()) {
                toast("?????? ????????? ??????????????????.")
            }
            else {
                if(NetworkStatus.status)
                    viewModel.writeComment(CommentForm(boardDetail.boardId, content))
                else
                    toast("???????????? ????????? ????????? ?????????.")
            }
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
        fun callPopupMenu(type: Int, Id: Int, view: View) {
            setPopupMenu(type, Id, view)
        }
    }

    fun setPopupMenu(popupType: Int, Id: Int, view: View) {
        var popup = PopupMenu(this@BoardDetailActivity, view)
        popup.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.menu_delete -> {
                    if(popupType == comment_type) {
                        Log.d(TAG,"comment Type : ${popupType}")
                        if(NetworkStatus.status)
                            viewModel.deleteComment(Id)
                        else
                            toast("???????????? ????????? ????????? ?????????.")
                    }
                    else {
                        if(NetworkStatus.status)
                            viewModel.deleteBoardDetail(Id)
                        else
                            toast("???????????? ????????? ????????? ?????????.")
                    }
                    true
                }
                else -> {
                    if(popupType == boardContent_type){
                        val intent = Intent(this@BoardDetailActivity, EditWriteActivity::class.java)
                        intent.putExtra("board", boardDetail)
                        startActivity(intent)
                    }
                    true
                }
            }

        }
        val inflater: MenuInflater = popup.menuInflater

        if(popupType == comment_type) {
            inflater.inflate(R.menu.comment_menu_item, popup.menu)
        }
        else {
            inflater.inflate(R.menu.board_menu_item, popup.menu)
        }
        popup.show()
    }
}