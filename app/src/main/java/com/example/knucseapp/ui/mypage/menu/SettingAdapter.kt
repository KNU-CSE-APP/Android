package com.example.knucseapp.ui.mypage.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.MypageRecyclerBinding
import com.example.knucseapp.databinding.SettingRecyclerBinding
import com.example.knucseapp.ui.mypage.Menuname
import com.example.knucseapp.ui.mypage.MyPageMenu

class SettingAdapter : RecyclerView.Adapter<Holder>() {
    var setting_list = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SettingRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = setting_list.get(position)
        holder.setSetting(menu)
    }

    override fun getItemCount(): Int {
        return setting_list.size
    }
}

class Holder(val binding: SettingRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setSetting(item: String){
        binding.settingText.text = item
    }
}