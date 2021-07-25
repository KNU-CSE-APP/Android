package com.example.knucseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityMainBinding
import com.example.knucseapp.ui.board.BoardHomeFragment
import com.example.knucseapp.ui.board.freeboard.BoardFragment
import com.example.knucseapp.ui.mypage.MypageFragment
import com.example.knucseapp.ui.notice.NoticeFragment
import com.example.knucseapp.ui.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var noticeFragment: NoticeFragment
    private lateinit var boardHomeFragment: BoardHomeFragment
    private lateinit var reservationFragment: ReservationFragment
    private lateinit var mypageFragment: MypageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO:변경~~~
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        //TODO:변경~~~

        setToolbar()
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
                        boardHomeFragment = BoardHomeFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,boardHomeFragment).commit()
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

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_item, menu)
        return true
    }
}