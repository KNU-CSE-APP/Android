package com.example.knucseapp.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.MypageRecyclerBinding

class MyPageAdapter : RecyclerView.Adapter<Holder>() {
    var menulist = mutableListOf<MyPageMenu>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MypageRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = menulist.get(position)
        holder.setMemo(menu)
    }

    override fun getItemCount(): Int {
        return menulist.size
    }
}

class Holder(val binding: MypageRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener{
            Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.menutext.text}", Toast.LENGTH_LONG).show()
        }
    }
    fun setMemo(menu: MyPageMenu){
        binding.imageView.setImageResource(menu.ResId)
        binding.menutext.text = menu.MenuName
    }
}