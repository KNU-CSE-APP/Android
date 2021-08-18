package com.example.knucseapp.ui.reservation.seat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ClassSeatDTO
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ActivityReservationConfirmBinding
import com.example.knucseapp.ui.MainActivity
import com.example.knucseapp.ui.reservation.ReservationViewModel
import com.example.knucseapp.ui.reservation.ReservationViewModelFactory
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ReservationConfirmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationConfirmBinding
    private lateinit var viewModelFactory: ReservationViewModelFactory
    private lateinit var viewModel: ReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_confirm)
        binding.lifecycleOwner = this

        initViewModel()
        setToolbar()
        setButton()
    }

    private fun setButton() {
        binding.btnCompleteReservation.setOnClickListener {
            val intent = Intent(this@ReservationConfirmActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
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

    private fun initViewModel(){
        viewModelFactory = ReservationViewModelFactory(ReservationRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReservationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }
}