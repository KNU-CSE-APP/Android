package com.example.knucseapp.ui.reservation.seat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ActivityReservationConfirmBinding
import com.example.knucseapp.ui.MainActivity
import com.example.knucseapp.ui.reservation.ReservationViewModel
import com.example.knucseapp.ui.reservation.ReservationViewModelFactory
import com.example.knucseapp.ui.util.*
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
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.infoLinearLayout.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
                viewModel.requestFindReservation()
            }
            else
            {
                binding.infoLinearLayout.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }

        setToolbar()
        setButton()
    }

    override fun onStart(){
        super.onStart()

//        if(NetworkStatus.status)
//            viewModel.requestFindReservation()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    private fun initViewModel(){
        viewModelFactory = ReservationViewModelFactory(ReservationRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReservationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // viewmodel observe
        viewModel.dataLoading.observe(this){
            if (it){
                binding.infoLinearLayout.visibility = View.GONE
                binding.progressBar.show()
            }
            else{
                binding.infoLinearLayout.visibility = View.VISIBLE
                binding.progressBar.hide()
            }
        }

        viewModel.getFindReservationResponse.observe(this){
            if (it.success){
                binding.reservationConfirmSeatInfo.setText("${it.response.building}호관 ${it.response.roomNumber}호 ${it.response.seatNumber}번")
                binding.reservationConfirmSeatStatus.setText("이용중")
                binding.reservationConfirmEnterTime.setText("${it.response.startDate.substring(0,10)} ${it.response.startDate.substring(11,it.response.startDate.length)}")
                binding.reservationConfirmExitTime.setText("${it.response.dueDate.substring(0,10)} ${it.response.dueDate.substring(11,it.response.dueDate.length)}")
            }
        }

    }
}