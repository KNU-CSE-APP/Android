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
                        binding.mainToolbarTextview.text = "공지사항"
                        noticeFragment = NoticeFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,noticeFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_board -> {
                        binding.mainToolbarTextview.text = "자유게시판"
                        boardFragment = BoardFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,boardFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_reservation -> {
                        binding.mainToolbarTextview.text = "강의실 예약"
                        reservationFragment = ReservationFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,reservationFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_mypage -> {
                        binding.mainToolbarTextview.text = "마이 페이지"
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