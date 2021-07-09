package com.example.knucseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityMainBinding
import com.example.knucseapp.ui.board.BoardFragment
import com.example.knucseapp.ui.mypage.MypageFragment
import com.example.knucseapp.ui.notice.NoticeFragment
import com.example.knucseapp.ui.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private lateinit var noticeFragment: NoticeFragment
    private lateinit var boardFragment: BoardFragment
    private lateinit var reservationFragment: ReservationFragment
    private lateinit var mypageFragment: MypageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.apply {
            setOnItemSelectedListener { // 메뉴 고를 때
                when(it.itemId){
                    R.id.action_notice -> {
                        noticeFragment = NoticeFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,noticeFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_board -> {
                        boardFragment = BoardFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,boardFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_reservation -> {
                        reservationFragment = ReservationFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,reservationFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_mypage -> {
                        mypageFragment = MypageFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,mypageFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
            selectedItemId = R.id.action_reservation // 초기 화면
        }
    }
}