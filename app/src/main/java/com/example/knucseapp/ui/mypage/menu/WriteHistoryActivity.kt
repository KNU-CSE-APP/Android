package com.example.knucseapp.ui.mypage.menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityPasswordEditBinding
import com.example.knucseapp.databinding.ActivityWriteHistoryBinding
import com.example.knucseapp.ui.board.freeboard.BoardAdapter
import com.example.knucseapp.ui.board.freeboard.BoardFragment
import com.example.knucseapp.ui.mypage.MypageViewModel
import com.example.knucseapp.ui.mypage.MypageViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast

class WriteHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWriteHistoryBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory
    private lateinit var adapter: BoardAdapter
    private var boardname = "내가 쓴 글"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_history)
        initViewModel()
        setToolbar()
        setRecyclerView()
        initData()
    }

    private fun initViewModel(){
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(MypageViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getMyBoardResponse.observe(this){
            if (it.success){
                adapter.myboardItem(it.response)
            }
            else {toast(it.error.message)}
        }

        viewModel.dataLoading.observe(this) {
            if(it) {
                binding.writeHistroyProgressBar.show()
            }
            else {
                binding.writeHistroyProgressBar.hide()
            }
        }
    }
    private fun initData() {
        viewModel.getMyBoard()
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

    fun setRecyclerView() {
        adapter = BoardAdapter(boardname)

        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.boardRecycler.addItemDecoration(decoration)
        binding.boardRecycler.adapter = adapter
        binding.boardRecycler.layoutManager = LinearLayoutManager(this)

    }
}