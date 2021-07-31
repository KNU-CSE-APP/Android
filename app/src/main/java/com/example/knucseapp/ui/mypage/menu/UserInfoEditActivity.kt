package com.example.knucseapp.ui.mypage.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityUserInfoEditBinding

class UserInfoEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserInfoEditBinding
    lateinit var nickName : String
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Glide.with(this).load(it.data?.data).into(binding.accountIvProfile)
            binding.btnOk.isEnabled=true
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
        val tv_removeProfile = dialogView.findViewById<TextView>(R.id.change_profile)

        tv_changeProfile.setOnClickListener {
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            getContent.launch(photoPickerIntent)
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