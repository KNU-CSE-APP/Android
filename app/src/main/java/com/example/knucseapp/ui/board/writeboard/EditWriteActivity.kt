package com.example.knucseapp.ui.board.writeboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
import gun0912.tedimagepicker.builder.TedImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class EditWriteActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : ActivityEditWriteBinding
    private lateinit var boardDTO: BoardDTO
    private lateinit var adapter : WritePhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_write)
        boardDTO = intent.getSerializableExtra("board") as BoardDTO
        initViewModel()
        setToolbar()
        setBoard()
        setButton()
        setRecycler()
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
            var fileList = mutableListOf<MultipartBody.Part>() //추가된 이미지
            adapter.imageurl.forEach { filePath ->
                if(filePath is Uri) {
                    var file = File(createCopyAndReturnRealPath(filePath))
                    var requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    var bodyFile = MultipartBody.Part.createFormData("file[]", file.name + ".jpg", requestFile)
                    fileList.add(bodyFile)
                }
                else {
                    filePath as String
                    if(filePath in boardDTO.images){
                        boardDTO.images.remove(filePath)
                    }
                }
            }

            var deleteList = mutableListOf<MultipartBody.Part>()
            boardDTO.images.forEach {
                var bodyFile = MultipartBody.Part.createFormData("deleteUrl[]", it)
                deleteList.add(bodyFile)
            }

            viewModel.changeBoardDetail(boardDTO, fileList, deleteList)
        }

        binding.btnCamera.setOnClickListener {
            TedImagePicker.with(this)
                    .max(10-adapter.itemCount, "최대 10장 선택 가능합니다.")
                    .startMultiImage { uriList ->
                        adapter.addUri(uriList)
                    }
        }
    }

    private fun setRecycler(){
        //TODO: image setting
        adapter = WritePhotoAdapter()
        adapter.setUrl(boardDTO.images)
        binding.writePhotoRecycler.adapter = adapter
        binding.writePhotoRecycler.layoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    // Uri -> absolutePath
    fun createCopyAndReturnRealPath(uri: Uri) :String? {
        val context = applicationContext
        val contentResolver = context.contentResolver ?: return null

        // Create file path inside app's data dir
        val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) { e.printStackTrace() }
        return file.getAbsolutePath()
    }
}