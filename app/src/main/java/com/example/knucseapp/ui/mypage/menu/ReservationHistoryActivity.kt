package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityMainBinding
import com.example.knucseapp.databinding.ActivityReservationHistoryBinding

class ReservationHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationHistoryBinding

    lateinit var menu_name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_history)
        binding.lifecycleOwner = this

        menu_name = intent.getStringExtra("menu_name").toString()
        setToolbar()
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.reservationHistoryToolbarTextview.text = menu_name
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
}