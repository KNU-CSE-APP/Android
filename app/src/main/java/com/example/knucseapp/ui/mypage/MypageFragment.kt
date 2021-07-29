package com.example.knucseapp.ui.mypage

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.MypageFragmentBinding
import com.example.knucseapp.ui.SignInActivity
import com.example.knucseapp.ui.mypage.menu.ReservationHistoryActivity
import com.example.knucseapp.ui.mypage.menu.SettingActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    private lateinit var viewModel: MypageViewModel
    lateinit var binding: MypageFragmentBinding
    var menulist = mutableListOf<MyPageMenu>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = MypageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        viewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
        setButton()
    }


    fun setButton() {
        binding.btnMypageMypage.setOnClickListener {
            Toast.makeText(context, "mypage clicked", Toast.LENGTH_SHORT).show()
        }
        binding.btnMypageChangePassword.setOnClickListener {
            Toast.makeText(context, "change password clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnMypageReservationHistory.setOnClickListener {
            val intent = Intent(it.context, ReservationHistoryActivity::class.java)
            it.context.startActivity(intent)
        }

        binding.btnMypageWriteHistory.setOnClickListener {
            Toast.makeText(context, "wrtie clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnMypageSetting.setOnClickListener {
            val intent = Intent(it.context, SettingActivity::class.java)
            it.context.startActivity(intent)
        }

        binding.btnMypageLogout.setOnClickListener {
            Toast.makeText(context, "logout clicked", Toast.LENGTH_SHORT).show()
            logout()
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