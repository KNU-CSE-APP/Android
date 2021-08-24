package com.example.knucseapp.ui.board.writeboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityWriteBinding
import com.example.knucseapp.ui.board.BoardHomeFragment
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.toast
import okhttp3.internal.notify


class WriteActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : ActivityWriteBinding
    private lateinit var adapter : WritePhotoAdapter
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        adapter.imageurl.clear()
        result.run {
            if(data?.clipData!= null) {
                val count = data!!.clipData!!.itemCount

                for(i in 0 until count) {
                    val imageUri = data!!.clipData!!.getItemAt(i).uri
                    adapter.setUrl(imageUri)
                }

            }
            else {
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if(imageUri != null) {
                        adapter.setUrl(imageUri)
                    }
                }
            }
        }

        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
        setToolbar()
        setSpinner()
        initViewModel()
        setRecycler()
        setButton()
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
            toast("${it}")
        }

        viewModel.writeResponse.observe(this) {
            if (it.success) {
                toast("게시글이 작성되었습니다.")
                val mIntent = Intent(this, BoardHomeFragment::class.java).apply {
                    putExtra("category", it.response.category)
                }
                setResult(Activity.RESULT_OK, mIntent);
                Log.d("WriteActivity", "${viewModel.freeBoardPage.value}")
                finish()
            } else {
                toast(it.error.message)
                finish()
            }
        }

    }

    private fun setRecycler(){
        //TODO: image setting
        adapter = WritePhotoAdapter()
        binding.writePhotoRecycler.adapter = adapter
        binding.writePhotoRecycler.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    private fun setButton() {
        binding.btnCamera.setOnClickListener {
            Log.d("WriteActivity", "clicked!!")
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.action = Intent.ACTION_GET_CONTENT
            getContent.launch(intent)
        }
    }


}