package com.example.knucseapp.ui.board.writeboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast
import gun0912.tedimagepicker.builder.TedImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class WriteActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : ActivityWriteBinding
    private lateinit var adapter : WritePhotoAdapter

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

        viewModel.writeLoading.observe(this) {
            if(it){
                binding.writeprogressbar.show()
            }
            else {
                binding.writeprogressbar.hide()
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
//            Log.d("WriteActivity", "clicked!!")
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            intent.action = Intent.ACTION_GET_CONTENT
//            getContent.launch(intent)
            TedImagePicker.with(this)
                    .startMultiImage { uriList ->
                        adapter.setUrl(uriList)
                    }
        }

        binding.addWrite.setOnClickListener {
            var fileList = mutableListOf<MultipartBody.Part>()
            adapter.imageurl.forEach { filePath ->
                var file = File(createCopyAndReturnRealPath(filePath))
                var requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                var bodyFile = MultipartBody.Part.createFormData("file[]",file.name+".jpg",requestFile)
                fileList.add(bodyFile)
            }
            viewModel.write(binding.categoryTextview.text.toString(), fileList)
        }
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