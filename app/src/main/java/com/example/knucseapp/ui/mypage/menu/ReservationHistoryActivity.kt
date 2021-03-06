package com.example.knucseapp.ui.mypage.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ActivityReservationHistoryBinding
import com.example.knucseapp.ui.reservation.ReservationViewModel
import com.example.knucseapp.ui.reservation.ReservationViewModelFactory
import com.example.knucseapp.ui.util.*
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
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.connectedLayout.visibility = VISIBLE
                binding.disconnectedLayout.visibility = GONE
                NetworkStatus.status = true
                viewModel.requestFindReservation()
            }
            else
            {
                binding.connectedLayout.visibility = GONE
                binding.disconnectedLayout.visibility = VISIBLE
                NetworkStatus.status = false
            }
        }
        initViewModel()
        if(NetworkStatus.status)
            viewModel.requestFindReservation()
        setBtnListener()
        setToolbar()
    }

    private fun setBtnListener(){
        binding.apply {
            reservationReturnButton.setOnClickListener {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle("?????? ??????")
                    .setMessage("????????? ?????????????????????????")
                    .setPositiveButton("??????") { dialog, whichButton ->
                        if(NetworkStatus.status)
                            viewModel.requestDeleteSeat()
                        else
                            toast("???????????? ????????? ????????? ?????????.")
                    }
                    .setNegativeButton("??????") { dialog, whichButton -> // ????????? ?????? ??????
                    }
                    .show()
            }
            reservationExtensionButton.setOnClickListener {
                MaterialAlertDialogBuilder(binding.root.context)
                    .setTitle("?????? ??????")
                    .setMessage("????????? ?????????????????????????")
                    .setPositiveButton("??????") { dialog, whichButton ->
                        if(NetworkStatus.status)
                            viewModel.requestExtension()
                        else
                            toast("???????????? ????????? ????????? ?????????.")
                    }
                    .setNegativeButton("??????") { dialog, whichButton -> // ????????? ?????? ??????
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
                binding.connectedLayout.visibility = GONE
                binding.progressBar.show()
            }
            else{
                binding.connectedLayout.visibility = VISIBLE
                binding.progressBar.hide()
            }
        }

        // reservation info
        viewModel.getFindReservationResponse.observe(this){
            if (it.success){
                binding.reservationHistroyMessageTextview.setText("?????? ????????? ?????? ???????????????.")
                binding.reservationTable.visibility = VISIBLE
                binding.reservationHistoryBtnLayout.visibility = VISIBLE
                binding.reservationHistorySeatInfo.setText("${it.response.building}?????? ${it.response.roomNumber}??? ${it.response.seatNumber}???")
                binding.reservationHistorySeatStatus.setText("?????????")
                binding.reservationHistoryExtensionNum.setText(it.response.extensionNum)
                binding.reservationHistoryEnterTime.setText("${it.response.startDate.substring(0,10)} ${it.response.startDate.substring(11,it.response.startDate.length)}")
                binding.reservationHistoryExitTime.setText("${it.response.dueDate.substring(0,10)} ${it.response.dueDate.substring(11,it.response.dueDate.length)}")
            }
        }

        // extension
        viewModel.getExtensionResponse.observe(this){
            if (it.success){
                binding.reservationHistroyMessageTextview.setText("?????? ????????? ?????? ???????????????.")
                binding.reservationTable.visibility = VISIBLE
                binding.reservationHistoryBtnLayout.visibility = VISIBLE
                binding.reservationHistorySeatInfo.setText("${it.response.building}?????? ${it.response.roomNumber}??? ${it.response.seatNumber}???")
                binding.reservationHistorySeatStatus.setText("?????????")
                binding.reservationHistoryExtensionNum.setText(it.response.extensionNum)
                binding.reservationHistoryEnterTime.setText("${it.response.startDate.substring(0,10)} ${it.response.startDate.substring(11,it.response.startDate.length)}")
                binding.reservationHistoryExitTime.setText("${it.response.dueDate.substring(0,10)} ${it.response.dueDate.substring(11,it.response.dueDate.length)}")
                toast("?????? ${3-it.response.extensionNum.toInt()}??? ???????????????.")
                if(it.response.extensionNum.toInt()==3){
                    binding.reservationExtensionButton.isEnabled = false
                }
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
        //?????? ????????? ?????? ??????
        binding.reservationHistroyMessageTextview.text = "?????? ????????? ?????? ???????????????."
        binding.reservationTable.visibility = VISIBLE
        binding.reservationHistoryBtnLayout.visibility = VISIBLE

        binding.reservationHistorySeatInfo.text = "~~~??? ??????"
        binding.reservationHistorySeatStatus.text = "?????????"
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        binding.reservationHistoryEnterTime.text = "${df.format(cal.time)}"

        cal.add(Calendar.HOUR, 6)
        binding.reservationHistoryExitTime.text = "${df.format(cal.time)}"

        binding.reservationHistoryExtensionNum.text = "0???"
    }
}