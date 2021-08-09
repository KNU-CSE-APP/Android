package com.example.knucseapp.ui.board.writeboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardForm
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityWriteBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WriteActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : ActivityWriteBinding
    private lateinit var arrayAdapter : ArrayAdapter<Any?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        setToolbar()
        setSpinner()
        initViewModel()
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

    private fun setSpinner() {
        var list_of_items = arrayOf("자유게시판", "QNA")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, list_of_items)
        (binding.categoryTextview as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initViewModel() {
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.toastMessage.observe(this) {
            Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
        }

        viewModel.writeResponse.observe(this) {
            if (it.success) {
                Toast.makeText(this, "게시글이 작성되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

}