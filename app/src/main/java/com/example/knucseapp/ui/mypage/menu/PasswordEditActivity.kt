package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityPasswordEditBinding

class PasswordEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPasswordEditBinding
    lateinit var menuName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_edit)
        binding.lifecycleOwner = this

        menuName = intent.getStringExtra("menu_name").toString()
        setToolbar()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.passwordEditToolbarTextview.text = menuName
    }
}