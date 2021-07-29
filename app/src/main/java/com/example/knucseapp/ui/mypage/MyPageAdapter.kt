package com.example.knucseapp.ui.mypage

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.MypageRecyclerBinding
import com.example.knucseapp.ui.SignInActivity
import com.example.knucseapp.ui.mypage.menu.ReservationHistoryActivity
import com.example.knucseapp.ui.mypage.menu.SettingActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

            when(binding.menutext.text)
            {
                Menuname.grouplist[0] -> {
                    val intent = Intent(it.context, ReservationHistoryActivity::class.java)
                    intent.putExtra("menu_name", Menuname.grouplist[0])
                    it.context.startActivity(intent)
                }
                Menuname.grouplist[4] -> {
                    val intent = Intent(it.context, SettingActivity::class.java)
                    intent.putExtra("menu_name", Menuname.grouplist[4])
                    it.context.startActivity(intent)
                }
                Menuname.grouplist[5] -> {
                    MaterialAlertDialogBuilder(binding.root.context)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃하시겠습니까?")
                            .setPositiveButton("확인") { _, _ ->
                                val intent = Intent(it.context, SignInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                it.context.startActivity(intent)
                            }
                            .setNegativeButton("취소") { _, _ -> // 취소시 처리 로직
                            }
                            .show()

                }
                else -> {
                    Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.menutext.text}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun setMemo(menu: MyPageMenu){
        binding.imageView.setImageResource(menu.ResId)
        binding.menutext.text = menu.MenuName
    }
}