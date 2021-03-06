package com.example.knucseapp.ui.board.writeboard

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.ActivityEditWriteBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.NetworkConnection
import com.example.knucseapp.ui.util.NetworkStatus
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
        setBoardContent()

        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if(!isConnected) finish()
        }
    }

    fun setBoardContent(){
        binding.editWriteToolbarTextview.text = when(boardDTO.category){
            "FREE" -> "???????????????"
            else -> "QNA"
        }

        viewModel.writeTitle.set(boardDTO.title)
        viewModel.writeContent.set(boardDTO.content)
        adapter.setUrl(boardDTO.images)

    }

    fun setBoard(){
        viewModel.writeTitle.set(boardDTO.title)
        viewModel.writeContent.set(boardDTO.content)
    }

    private fun setToolbar(){
        setSupportActionBar(binding.editWriteToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.editWriteToolbarTextview.text = when(boardDTO.category){
            "FREE" -> "???????????????"
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
                toast("????????? ????????? ?????????????????????.")
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
            if(NetworkStatus.status){
                var fileList = mutableListOf<MultipartBody.Part>() //????????? ?????????
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
            else
                toast("???????????? ????????? ????????? ?????????.")
        }

        binding.btnCamera.setOnClickListener {
            TedImagePicker.with(this)
                    .max(10-adapter.itemCount, "?????? 10??? ?????? ???????????????.")
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