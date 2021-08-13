package com.example.knucseapp.ui.mypage.menu

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.ActivityUserInfoEditBinding
import com.example.knucseapp.ui.auth.MySharedPreferences
import com.example.knucseapp.ui.mypage.MypageViewModel
import com.example.knucseapp.ui.mypage.MypageViewModelFactory
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class UserInfoEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserInfoEditBinding
    private lateinit var viewModel : MypageViewModel
    private lateinit var viewModelFactory: MypageViewModelFactory
    lateinit var nickName : String
    lateinit var filePath : Uri
    private var imageChanged = false
    private var nickNameChanged = false
    private lateinit var file : File
    private lateinit var requestFile : RequestBody
    private lateinit var bodyFile : MultipartBody.Part
    private lateinit var bodyNickname : MultipartBody.Part

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            Glide.with(this).load(result.uriContent).into(binding.accountIvProfile)
            filePath = result.uriContent!!
            binding.btnOk.isEnabled=true
            imageChanged = true
        }
    }

    private fun startCrop() {
        // start picker to get image for cropping and then use the image in cropping activity
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setAllowRotation(true)
                setActivityTitle("crop")
                setCropMenuCropButtonIcon(0)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info_edit)
        initViewModel()
        setToolbar()

        nickName = binding.userNicknameText.text.toString()
        viewModel.getUserInfo()

        binding.apply {
            btnOk.setOnClickListener {
                if(nickNameChanged && imageChanged){
                    file = File(createCopyAndReturnRealPath(filePath))
                    requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    bodyFile = MultipartBody.Part.createFormData("image",file.name,requestFile)
                    bodyNickname  = MultipartBody.Part.createFormData("nickName",binding.userNicknameEdit.text.toString())
                    viewmodel?.editUserInfo(bodyFile,bodyNickname)
                }
                else if (nickNameChanged && !imageChanged){
                    if(binding.userNicknameEdit.text.toString().isNotEmpty()){
                        bodyNickname  = MultipartBody.Part.createFormData("nickName",binding.userNicknameEdit.text.toString())
                        viewmodel?.editUserInfo(null,bodyNickname)
                    }
                    else
                        toast("닉네임을 입력해주세요.")

                }
                else if(!nickNameChanged && imageChanged){
                    file = File(createCopyAndReturnRealPath(filePath))
                    requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    bodyFile = MultipartBody.Part.createFormData("image",file.name,requestFile)
                    viewmodel?.editUserInfo(bodyFile,null)
                }

            }
            accountIvProfile.setOnClickListener { showDialog() }
            btnEditNickname.setOnClickListener {
                binding.userNicknameText.visibility = View.GONE
                binding.userNicknameEdit.visibility = View.VISIBLE
                binding.userNicknameEdit.setText(binding.userNicknameText.text)
            }
            userNicknameEdit.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
                override fun afterTextChanged(s: Editable?) {
                    if (!binding.userNicknameEdit.text.toString().equals(nickName)){
                        binding.btnOk.isEnabled = true
                        nickNameChanged = true
                    }
                }
            })
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

    private fun initViewModel(){
        viewModelFactory = MypageViewModelFactory(AuthRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(MypageViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        // 유저 정보 loading
        viewModel.dataLoading.observe(this){
            if (it){
                binding.infoLinearLayout.visibility = View.GONE
                binding.imageConstraintLayout.visibility = View.GONE
                binding.progressbar2.show()
            }
            else{
                binding.infoLinearLayout.visibility = View.VISIBLE
                binding.imageConstraintLayout.visibility = View.VISIBLE
                binding.progressbar2.hide()
            }
        }

        // 유저 정보 setting
        viewModel.getUserInfoResponse.observe(this){
            if (it.success)
            {
                if (it.response.imagePath == null){ Glide.with(this).load(R.drawable.profile_default_image).into(binding.accountIvProfile) }
                else{ Glide.with(this).load(it.response.imagePath).into(binding.accountIvProfile) }
                binding.userEmailText.setText(it.response.email)
                binding.userNameText.setText(it.response.username)
                binding.userStudentidText.setText(it.response.studentId)
                binding.userNicknameText.setText(it.response.nickname)
                nickName = it.response.nickname
            }
            else
                toast(it.error.message)
        }

        viewModel.getPutProfileResponse.observe(this){
            if (it.success){
                toast(it.response)
                finish()
            }
            else{ toast(it.error.message) }
        }
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this).create()
        val dialogView = layoutInflater.inflate(R.layout.profile_edit_dialog,null)
        val tv_changeProfile = dialogView.findViewById<TextView>(R.id.change_profile)
        val tv_removeProfile = dialogView.findViewById<TextView>(R.id.remove_profile)

        tv_changeProfile.setOnClickListener {
            startCrop()
            builder.dismiss()
        }
        tv_removeProfile.setOnClickListener {
            Glide.with(this).load(R.drawable.profile_default_image).into(binding.accountIvProfile)
            builder.dismiss()
        }
        builder.setView(dialogView)
        builder.show()
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