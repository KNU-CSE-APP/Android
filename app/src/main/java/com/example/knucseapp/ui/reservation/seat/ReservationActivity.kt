package com.example.knucseapp.ui.reservation.seat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ClassSeatDTO
import com.example.knucseapp.data.model.FindClassRoomDTO
import com.example.knucseapp.data.model.ReservationDTO
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ActivityReservationBinding
import com.example.knucseapp.ui.reservation.ReservationViewModel
import com.example.knucseapp.ui.reservation.ReservationViewModelFactory
import com.example.knucseapp.ui.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ReservationActivity : AppCompatActivity() {

    private lateinit var viewModel: ReservationViewModel
    private lateinit var viewModelFactory: ReservationViewModelFactory
    private lateinit var binding: ActivityReservationBinding
    private lateinit var adapter: SeatAdapter
    lateinit var room: FindClassRoomDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        room = intent.getSerializableExtra("room") as FindClassRoomDTO

        initViewModel()
        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected)
            {
                binding.connectedLayout.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
                viewModel.getAllSeat(room.building, room.roomNumber)
            }
            else
            {
                binding.connectedLayout.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }
        setToolbar()
        setRecyclerView()
        setButton()
    }

    override fun onStart() {
        super.onStart()
//        if(NetworkStatus.status)
//            viewModel.getAllSeat(room.building, room.roomNumber)
    }


    private fun initViewModel(){
        viewModelFactory = ReservationViewModelFactory(ReservationRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReservationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.allSeatDataLoading.observe(this){
            if(it){
                binding.reservationProgressbar.show()
            }
            else {
                binding.reservationProgressbar.hide()
            }
        }

        viewModel.allSeatData.observe(this) {
            adapter.setData(it)
        }

        viewModel.makeReservationResult.observe(this) {
            if(it.success){
                toast(it.response)
                val intent = Intent(
                        this@ReservationActivity,
                        ReservationConfirmActivity::class.java
                )
                this@ReservationActivity.startActivity(intent)
            }
            else{
                toast(it.error.message)
            }
        }
    }


    private fun setButton() {
        binding.btnShowSetLayout.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.image_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            mBuilder.show()
        }
    }
    private fun setRecyclerView() {
        var link = changeActivity()
        adapter = SeatAdapter(link)
        binding.seatRecycler.adapter = adapter
        binding.seatRecycler.layoutManager = GridLayoutManager(this, 4)
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.reservationToolbarTextview.text = "${room.building} - ${room.roomNumber}???"
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

    inner class changeActivity{
        fun callReservationConfirmActivity(seat: ClassSeatDTO) {
            seat.apply {
                if(status == "RESERVED"){
                    Toast.makeText(this@ReservationActivity, "?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
                else{
                    MaterialAlertDialogBuilder(binding.root.context)
                        .setTitle("?????? ??????")
                        .setMessage("?????? ????????? ????????????????????????? \n${room.roomNumber} / ${seat.number}??? ??????\n\n???????????? ????????? ????????? ??????????????????.")
                        .setPositiveButton("??????") { dialog, whichButton ->
                            if(NetworkStatus.status)
                                viewModel.makeReservation(ReservationDTO(room.building, room.roomNumber, seat.number))
                            else
                                toast("???????????? ????????? ????????? ?????????.")
                        }
                        .setNegativeButton("??????") { dialog, whichButton -> // ????????? ?????? ??????
                        }
                        .show()

                }
            }

        }

    }
}