package com.example.knucseapp.ui.mypage.menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityReservationHistoryBinding
import com.example.knucseapp.databinding.ActivitySettingBinding
import com.example.knucseapp.ui.mypage.Menuname
import com.example.knucseapp.ui.mypage.MyPageAdapter
import com.example.knucseapp.ui.mypage.MyPageMenu

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    var setting_list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.lifecycleOwner = this

        setToolbar()
        loadMenu()
        var adapter = SettingAdapter()
        adapter.setting_list = setting_list

        val decoration = com.example.knucseapp.ui.DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.settingRecycler.addItemDecoration(decoration)
        binding.settingRecycler.adapter = adapter
        binding.settingRecycler.layoutManager = LinearLayoutManager(this)
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

    fun loadMenu(){
        Menuname.setting.forEach { name ->
            setting_list.add(name)
        }
    }
}