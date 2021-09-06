package com.example.knucseapp.ui.mypage.menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityCommentHistoryBinding
import com.example.knucseapp.ui.board.detail.CommentAdapter
import com.example.knucseapp.ui.mypage.MypageViewModel
import com.example.knucseapp.ui.mypage.MypageViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration
import com.example.knucseapp.ui.util.NetworkConnection
import com.example.knucseapp.ui.util.NetworkStatus
import com.example.knucseapp.ui.util.toast
import kotlinx.android.synthetic.main.activity_comment_history.*

class CommentHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommentHistoryBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory
    private lateinit var adapter : MyCommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment_history)
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.mycommentRecycler.visibility = VISIBLE
                binding.disconnectedLayout.visibility = GONE
                NetworkStatus.status = true
                initData()
            }
            else
            {
                binding.mycommentRecycler.visibility = GONE
                binding.disconnectedLayout.visibility = VISIBLE
                NetworkStatus.status = false
            }
        }
        setToolbar()
        initViewModel()
        setRecyclerView()
//        initData()
    }

    private fun setRecyclerView(){
        adapter = MyCommentAdapter()
        binding.mycommentRecycler.adapter = adapter
        binding.mycommentRecycler.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.mycommentRecycler.addItemDecoration(decoration)
    }

    private fun initViewModel(){
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(MypageViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.dataLoading.observe(this){
            if (it){
                binding.progressBar2.visibility = VISIBLE
                binding.mycommentRecycler.visibility = GONE
            }
            else{
                binding.progressBar2.visibility = GONE
                binding.mycommentRecycler.visibility = VISIBLE
            }
        }

        viewModel.getMyCommentResponse.observe(this){
            if (it.success){
                adapter.myCommentItem(it.response)
            }
            else {toast(it.error.message)}
        }
    }

    private fun initData(){
        viewModel.getMyComment()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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