package com.example.knucseapp.ui.reservation.seat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityReservationConfirmBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReservationConfirmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationConfirmBinding
    private lateinit var seat: Seat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_confirm)
        binding.lifecycleOwner = this

        seat = intent.getSerializableExtra("seat") as Seat
        setData()
        setToolbar()
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

    private fun setData() {
        binding.reservationConfirmSeatInfo.text = "${seat.Room_number} ${seat.Seat_number}번 좌석"
        binding.reservationConfirmSeatStatus.text = "예약 가능"
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        binding.reservationConfirmEnterTime.text = "${df.format(cal.time)}"

        cal.add(Calendar.HOUR, 6)
        binding.reservationConfirmExitTime.text = "${df.format(cal.time)}"
    }
}