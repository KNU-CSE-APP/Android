package com.example.knucseapp.ui.mypage

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.databinding.MypageFragmentBinding
import com.example.knucseapp.ui.SignInActivity
import com.example.knucseapp.ui.mypage.menu.PasswordEditActivity
import com.example.knucseapp.ui.mypage.menu.ReservationHistoryActivity
import com.example.knucseapp.ui.mypage.menu.SettingActivity
import com.example.knucseapp.ui.mypage.menu.UserInfoEditActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    lateinit var binding: MypageFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = MypageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        setButton()
    }
    
    fun setButton() {
        binding.apply {
            btnMypageMypage.setOnClickListener {
                val intent = Intent(it.context, UserInfoEditActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageChangePassword.setOnClickListener {
                val intent = Intent(it.context, PasswordEditActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageReservationHistory.setOnClickListener {
                val intent = Intent(it.context, ReservationHistoryActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageWriteHistory.setOnClickListener {
                Toast.makeText(context, "wrtie clicked", Toast.LENGTH_SHORT).show()
            }

            btnMypageSetting.setOnClickListener {
                val intent = Intent(it.context, SettingActivity::class.java)
                it.context.startActivity(intent)
            }

            btnMypageLogout.setOnClickListener {
                Toast.makeText(context, "logout clicked", Toast.LENGTH_SHORT).show()
                logout()
            }
        }
    }

    //TODO : viewmodel로 옮기기
    fun logout(){
        MaterialAlertDialogBuilder(binding.root.context)
                .setTitle("로그아웃")
                .setMessage("로그아웃하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    val intent = Intent(context, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context?.startActivity(intent)
                }
                .setNegativeButton("취소") { _, _ -> // 취소시 처리 로직
                }
                .show()
    }

}