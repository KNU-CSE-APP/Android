package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ReservationDTO
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ActivityMainBinding
import com.example.knucseapp.databinding.ActivityReservationHistoryBinding
import com.example.knucseapp.ui.reservation.ReservationViewModel
import com.example.knucseapp.ui.reservation.ReservationViewModelFactory
import com.example.knucseapp.ui.util.hide
import com.example.knucseapp.ui.util.show
import com.example.knucseapp.ui.util.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReservationHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationHistoryBinding
    lateinit var viewModel : ReservationViewModel
    lateinit var viewModelFactory : ReservationViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_history)
        initViewModel()
        viewModel.requestFindReservation()
        setBtnListener()
        setToolbar()
    }

    private fun setBtnListener(){
        binding.apply {
            reservationReturnButton.setOnClickListener {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle("반납 확인")
                    .setMessage("좌석을 반납하시겠습니까?")
                    .setPositiveButton("반납") { dialog, whichButton ->
                        viewModel.requestDeleteSeat()
                    }
                    .setNegativeButton("취소") { dialog, whichButton -> // 취소시 처리 로직
                    }
                    .show()
            }
            reservationExtensionButton.setOnClickListener {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle("연장 확인")
                    .setMessage("좌석을 연장하시겠습니까?")
                    .setPositiveButton("연장") { dialog, whichButton ->
                        viewModel.requestExtension()
                    }
                    .setNegativeButton("취소") { dialog, whichButton -> // 취소시 처리 로직
                    }
                    .show()
            }
        }
    }

    private fun initViewModel(){
        viewModelFactory = ReservationViewModelFactory(ReservationRepository())
        viewModel = ViewModelProvider(this,viewModelFactory).get(ReservationViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.dataLoading.observe(this){
            if (it){
                binding.linearLayout2.visibility = GONE
                binding.progressBar.show()
            }
            else{
                binding.linearLayout2.visibility = VISIBLE
                binding.progressBar.hide()
            }
        }

        // reservation info
        viewModel.getFindReservationResponse.observe(this){
            if (it.success){
                binding.reservationHistroyMessageTextview.setText("현재 예약된 좌석 정보입니다.")
                binding.reservationTable.visibility = VISIBLE
                binding.reservationHistoryBtnLayout.visibility = VISIBLE
                binding.reservationHistorySeatInfo.setText("${it.response.building}호관 ${it.response.roomNumber}호 ${it.response.seatNumber}번")
                binding.reservationHistorySeatStatus.setText("이용중")
                binding.reservationHistoryExtensionNum.setText(it.response.extensionNum)
                binding.reservationHistoryEnterTime.setText("${it.response.startDate.substring(0,10)} ${it.response.startDate.substring(11,it.response.startDate.length)}")
                binding.reservationHistoryExitTime.setText("${it.response.dueDate.substring(0,10)} ${it.response.dueDate.substring(11,it.response.dueDate.length)}")
            }
        }

        // extension
        viewModel.getExtensionResponse.observe(this){
            if (it.success){
                binding.reservationHistroyMessageTextview.setText("현재 예약된 좌석 정보입니다.")
                binding.reservationTable.visibility = VISIBLE
                binding.reservationHistoryBtnLayout.visibility = VISIBLE
                binding.reservationHistorySeatInfo.setText("${it.response.building}호관 ${it.response.roomNumber}호 ${it.response.seatNumber}번")
                binding.reservationHistorySeatStatus.setText("이용중")
                binding.reservationHistoryExtensionNum.setText(it.response.extensionNum)
                binding.reservationHistoryEnterTime.setText("${it.response.startDate.substring(0,10)} ${it.response.startDate.substring(11,it.response.startDate.length)}")
                binding.reservationHistoryExitTime.setText("${it.response.dueDate.substring(0,10)} ${it.response.dueDate.substring(11,it.response.dueDate.length)}")
                toast("연장 ${3-it.response.extensionNum.toInt()}번 남았습니다.")
            }
            else{
                toast(it.error.message)
            }
        }

        // delete reservation
        viewModel.getDeleteSeatResponse.observe(this){
            if(it.success){
                toast(it.response)
                finish()
            }
            else toast(it.error.message)
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