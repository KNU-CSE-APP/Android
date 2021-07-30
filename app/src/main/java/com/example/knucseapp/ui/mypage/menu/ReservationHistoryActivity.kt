package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityMainBinding
import com.example.knucseapp.databinding.ActivityReservationHistoryBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReservationHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_history)
        binding.lifecycleOwner = this
        setToolbar()
        setData()
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

    fun setData(){
        //예약 정보가 있을 경우
        binding.reservationHistroyMessageTextview.text = "현재 예약된 좌석 정보입니다."
        binding.reservationTable.visibility = VISIBLE
        binding.reservationHistoryBtnLayout.visibility = VISIBLE

        binding.reservationHistorySeatInfo.text = "~~~번 좌석"
        binding.reservationHistorySeatStatus.text = "사용중"
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        binding.reservationHistoryEnterTime.text = "${df.format(cal.time)}"

        cal.add(Calendar.HOUR, 6)
        binding.reservationHistoryExitTime.text = "${df.format(cal.time)}"

        binding.reservationHistoryExtensionNum.text = "0회"
    }
}