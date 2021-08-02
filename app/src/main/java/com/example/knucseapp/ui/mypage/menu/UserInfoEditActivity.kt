package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityUserInfoEditBinding

class UserInfoEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserInfoEditBinding
    lateinit var nickName : String

    /*private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            startCrop()
        }
    */

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            Glide.with(this).load(result.uriContent).into(binding.accountIvProfile)
            binding.btnOk.isEnabled=true
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
        binding.lifecycleOwner = this

        setToolbar()
        nickName = binding.userNicknameText.text.toString()
        binding.btnOk.isEnabled = false

        binding.apply {
            btnOk.setOnClickListener {

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
                    }
                }
            })
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
            Glide.with(this).load(R.drawable.sample_image2).into(binding.accountIvProfile)
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