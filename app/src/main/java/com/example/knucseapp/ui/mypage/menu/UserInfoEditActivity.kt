package com.example.knucseapp.ui.mypage.menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.R.color.knu_red
import com.example.knucseapp.databinding.ActivityUserInfoEditBinding

class UserInfoEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserInfoEditBinding
    lateinit var menuName : String
    lateinit var nickName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info_edit)
        binding.lifecycleOwner = this

        menuName = intent.getStringExtra("menu_name").toString()
        setToolbar()
        nickName = binding.userNicknameText.text.toString()


        binding.userNicknameEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                if (binding.userNicknameEdit.text.toString().equals(nickName)){
                    binding.btnOk.isEnabled = false
                }
                else{
                    binding.btnOk.isEnabled = true
                }
            }
        })
        binding.apply {
            btnOk.setOnClickListener {

            }
            accountIvProfile.setOnClickListener { showDialog() }
            btnEditNickname.setOnClickListener {
                binding.userNicknameText.visibility = View.GONE
                binding.userNicknameEdit.visibility = View.VISIBLE
                binding.userNicknameEdit.setText(binding.userNicknameText.text)
            }
        }
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.profile_edit_dialog,null)
        builder.setView(dialogView).show()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.userinfoEditToolbarTextview.text = menuName
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